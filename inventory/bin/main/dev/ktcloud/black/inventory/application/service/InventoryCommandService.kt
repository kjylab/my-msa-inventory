package dev.ktcloud.black.inventory.application.service

import dev.ktcloud.black.client.redis.api.DistributedLock
import dev.ktcloud.black.client.redis.api.IdempotentEventProcessor
import dev.ktcloud.black.inventory.application.dto.event.outbound.InventoryReservedResultEvent
import dev.ktcloud.black.inventory.application.port.cache.outbound.InventoryCacheCommandOutboundPort
import dev.ktcloud.black.inventory.application.port.event.InventoryOrderEventPublishPort
import dev.ktcloud.black.inventory.application.port.inbound.command.CreateInventoryCommand
import dev.ktcloud.black.inventory.application.port.inbound.command.DecreaseInventoryCommand
import dev.ktcloud.black.inventory.application.port.inbound.command.IncreaseInventoryCommand
import dev.ktcloud.black.inventory.application.port.outbound.LoadCacheSyncedInventoryOutboundPort
import dev.ktcloud.black.inventory.application.port.outbound.UpdateInventoryCommandOutboundPort
import dev.ktcloud.black.inventory.application.port.state.outbound.InventoryStateCommandOutboundPort
import dev.ktcloud.black.inventory.domain.entity.InventoryDomainEntity
import dev.ktcloud.black.inventory.domain.exception.InventoryException
import dev.ktcloud.black.inventory.domain.vo.InventoryDecreaseIdempotencyKey
import dev.ktcloud.black.inventory.domain.vo.InventoryLockKey
import dev.ktcloud.black.inventory.domain.vo.InventoryReserveResultState
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class InventoryCommandService(
    private val inventoryStateCommandOutboundPort: InventoryStateCommandOutboundPort,
    private val loadCacheSyncedInventoryOutboundPort: LoadCacheSyncedInventoryOutboundPort,
    private val updateInventoryCommandOutboundPort: UpdateInventoryCommandOutboundPort,
    private val distributedLock: DistributedLock,
    private val inventoryCacheCommandOutboundPort: InventoryCacheCommandOutboundPort,
    private val idempotentEventProcessor: IdempotentEventProcessor,
    private val inventoryOrderEventPublishPort: InventoryOrderEventPublishPort
): CreateInventoryCommand, DecreaseInventoryCommand, IncreaseInventoryCommand {
    @Transactional
    override fun create(command: CreateInventoryCommand.In) {
        val inventoryDomainEntity = InventoryDomainEntity(
            productId = command.productId,
            skuCode = command.skuCode,
        )

        inventoryStateCommandOutboundPort.save(inventoryDomainEntity)
    }

    @Transactional
    override fun decrease(command: DecreaseInventoryCommand.In) {
        try {
            loadCacheSyncedInventoryOutboundPort.loadInventory(command.inventoryId)
        } catch (_: InventoryException.NoCachedInventoryFound) {
            distributedLock.execute(
                key = InventoryLockKey(command.inventoryId).toLockKey(),
                func = {
                    loadCacheSyncedInventoryOutboundPort.loadCacheSyncedInventory(command.inventoryId)
                }
            )
        }

        val result = try {
            idempotentEventProcessor.withIdempotencyProcess(
                InventoryDecreaseIdempotencyKey(command.inventoryId, command.orderId).toIdempotencyKey(),
                func = {
                    val eventId = updateInventoryCommandOutboundPort.decrease(command.inventoryId, command.amount)
                    inventoryCacheCommandOutboundPort.decrease(command.inventoryId, command.amount, eventId)
                }
            )
        } catch (_: InventoryException.InventoryNotEnough) {
            inventoryOrderEventPublishPort.publish(
                InventoryReservedResultEvent(
                    orderId = command.inventoryId,
                    inventoryId = command.inventoryId,
                    amount = command.amount,
                    resultState = InventoryReserveResultState.FAILED
                )
            )
        }

        if (result != null) {
            inventoryOrderEventPublishPort.publish(
                InventoryReservedResultEvent(
                    orderId = command.inventoryId,
                    inventoryId = command.inventoryId,
                    amount = command.amount,
                    resultState = InventoryReserveResultState.SUCCESS
                )
            )
        }
    }

    @Transactional
    override fun increase(command: IncreaseInventoryCommand.In) {
        try {
            loadCacheSyncedInventoryOutboundPort.loadInventory(command.inventoryId)
        } catch (_: InventoryException.NoCachedInventoryFound) {
            distributedLock.execute(
                key = InventoryLockKey(command.inventoryId).toLockKey(),
                func = {
                    loadCacheSyncedInventoryOutboundPort.loadCacheSyncedInventory(command.inventoryId)
                }
            )
        }

        val eventId = updateInventoryCommandOutboundPort.increase(command.inventoryId, command.amount)
        inventoryCacheCommandOutboundPort.increase(command.inventoryId, command.amount, eventId)
    }
}