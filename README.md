# my-msa-inventory

Troica Market MSA의 **재고(Inventory) 서비스**. 상품별 재고를 관리하며, Redis 캐시 + Lua 스크립트로 원자적 재고 차감을 구현하고 Kafka로 주문 서비스와 이벤트를 교환한다.

## 아키텍처

### 모듈 구성

```
inventory/            ← 순수 도메인 + 유스케이스 + Redis/Kafka 어댑터
  domain/             ← InventoryDomainEntity, InventoryException
  application/        ← UseCase 인터페이스 + 구현 서비스
  adapter/
    infrastructure/
      jpa/            ← PostgreSQL (영구 저장소)
      redis/          ← Redis 캐시 (원자적 수량 조작)
      kafka/          ← 주문 이벤트 발행/수신

inventory-event/      ← 이벤트 아웃박스 패턴용 도메인 + JPA 어댑터
  domain/             ← InventoryEventDomainEntity, InventoryEventType

inventory-service/    ← 실행 진입점 (Spring Boot + gRPC 서버)
  InventoryGrpcController
  GrpcExceptionHandler
```

### 재고 차감 흐름 (Redis Lua 스크립트)

재고 차감은 **Redis에서 먼저** 원자적으로 수행되고 나중에 PostgreSQL과 동기화된다.

```
주문 생성 요청
  → Kafka: inventory-reserve-request-topic
  → InventoryOrderEventKafkaListener
  → Redis Lua script (decrease_inventory.lua)
      - 재고 부족 → INVENTORY_NOT_ENOUGH 에러 반환
      - 성공 → 남은 수량 반환
  → PostgreSQL 동기화
  → Kafka: inventory-reserved-result-topic (성공/실패 결과 발행)
```

#### Lua 스크립트를 쓰는 이유

Redis `WATCH/MULTI/EXEC` 대신 Lua 스크립트를 사용하면 **Read-Check-Write가 하나의 원자적 연산**이 된다. 분산 환경에서도 재고가 음수가 되지 않도록 보장한다.

## gRPC API

```protobuf
service InventoryService {
  rpc CreateInventory   (CreateInventoryRequest)   returns (InventoryResponseDto);
  rpc DecreaseInventory (DecreaseInventoryRequest) returns (InventoryResponseDto);
  rpc IncreaseInventory (IncreaseInventoryRequest) returns (InventoryResponseDto);
  rpc FetchInventory    (FetchInventoryRequest)    returns (InventoryResponseDto);
  rpc FetchInventories  (Empty)                   returns (FetchInventoriesResponse);
}
```

> gRPC 포트: **9003** (HTTP: 8080)

## Kafka 토픽

| 토픽 | 방향 | 설명 |
|------|------|------|
| `inventory-reserve-request-topic` | 수신 (consumer) | order-service → 재고 예약 요청 |
| `inventory-reserved-result-topic` | 발행 (producer) | 재고 예약 성공/실패 결과 → order-service |

## 실행 포트

| 포트 | 용도 |
|------|------|
| 8080 | HTTP (actuator: /healthz, /actuator/prometheus) |
| 9003 | gRPC (내부 서비스 통신) |

## 의존 인프라

| 인프라 | 용도 |
|--------|------|
| PostgreSQL (`inventory_db`) | 재고 영구 저장 |
| Redis | 재고 수량 캐시 + 원자적 차감/증가 |
| Kafka | 주문 이벤트 비동기 처리 |

## CI/CD 흐름

```
GitHub push
  → JAR 빌드
  → Docker 이미지 빌드 + Docker Hub push (jyupk/my-msa-inventory-service)
  → my-msa-manifest-values/inventory-service/values-release.yaml 의 tag를 커밋 SHA로 업데이트
  → ArgoCD 감지 → 클러스터 롤링 업데이트
```

## 로컬 Docker 빌드

```bash
docker build --no-cache -t ktcloud-msa-inventory-service:latest -f Containerfile .
```

## 관련 레포

| 레포 | 역할 |
|------|------|
| [my-msa-common](https://github.com/kjylab/my-msa-common) | 공통 라이브러리 |
| [my-msa-manifest-values](https://github.com/kjylab/my-msa-manifest-values) | Helm values (이미지 태그 관리) |
