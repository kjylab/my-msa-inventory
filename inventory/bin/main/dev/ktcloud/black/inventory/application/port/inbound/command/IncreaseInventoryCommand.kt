package dev.ktcloud.black.inventory.application.port.inbound.command

interface IncreaseInventoryCommand {
    fun increase(command: In)

    data class In(
        val inventoryId: Long,
        val amount: Int
    )
}