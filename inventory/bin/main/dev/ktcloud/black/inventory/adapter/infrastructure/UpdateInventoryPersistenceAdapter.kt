package dev.ktcloud.black.inventory.adapter.infrastructure

import dev.ktcloud.black.inventory.application.port.cache.outbound.InventoryCacheCommandOutboundPort
import dev.ktcloud.black.inventory.application.port.outbound.UpdateInventoryCommandOutboundPort
import dev.ktcloud.black.inventory.event.application.port.outbound.InventoryEventCommandOutboundPort
import dev.ktcloud.black.inventory.event.domain.entity.InventoryEventDomainEntity
import dev.ktcloud.black.inventory.event.domain.vo.InventoryEventType
import org.springframework.stereotype.Component

@Component
class UpdateInventoryPersistenceAdapter(
    private val inventoryCacheCommandOutboundPort: InventoryCacheCommandOutboundPort,
    private val inventoryEventCommandOutboundPort: InventoryEventCommandOutboundPort
): UpdateInventoryCommandOutboundPort {
    override fun increase(
        inventoryId: Long,
        amount: Int
    ): Long {
        val inventoryEvent = InventoryEventDomainEntity(
            inventoryId = inventoryId,
            amount = amount,
            eventType = InventoryEventType.INCREMENT
        )

        val savedInventoryEvent = inventoryEventCommandOutboundPort.save(inventoryEvent)

        inventoryCacheCommandOutboundPort.increase(
            inventoryId = savedInventoryEvent.inventoryId,
            amount = savedInventoryEvent.amount,
            eventId = savedInventoryEvent.id
        )

        return savedInventoryEvent.id
    }

    override fun decrease(
        inventoryId: Long,
        amount: Int
    ): Long {
        val inventoryEvent = InventoryEventDomainEntity(
            inventoryId = inventoryId,
            amount = amount,
            eventType = InventoryEventType.DECREMENT
        )

        val savedInventoryEvent = inventoryEventCommandOutboundPort.save(inventoryEvent)

        inventoryCacheCommandOutboundPort.decrease(
            inventoryId = savedInventoryEvent.inventoryId,
            amount = savedInventoryEvent.amount,
            eventId = savedInventoryEvent.id
        )

        return savedInventoryEvent.id
    }
}