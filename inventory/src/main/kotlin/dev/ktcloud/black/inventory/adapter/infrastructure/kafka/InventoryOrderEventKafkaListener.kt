package dev.ktcloud.black.inventory.adapter.infrastructure.kafka

import dev.ktcloud.black.inventory.application.dto.event.inbound.InventoryReserveRequestEvent
import dev.ktcloud.black.inventory.application.port.event.InventoryOrderEventListenerPort
import dev.ktcloud.black.inventory.application.port.inbound.command.DecreaseInventoryCommand
import dev.ktcloud.black.inventory.application.service.InventoryCommandService
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class InventoryOrderEventKafkaListener(
    private val inventoryCommandService: InventoryCommandService,
): InventoryOrderEventListenerPort {
    @KafkaListener(
        topics = ["\${spring.kafka.topic.inventory-reserve-request}"],
        groupId = "inventory-service-group",
        containerFactory = "inventoryReserveRequestContainerFactory"
    )
    override fun onReserveRequest(event: InventoryReserveRequestEvent) {
        inventoryCommandService.decrease(
            DecreaseInventoryCommand.In(
                orderId = event.orderId,
                inventoryId = event.inventoryId,
                amount = event.amount
            )
        )
    }
}