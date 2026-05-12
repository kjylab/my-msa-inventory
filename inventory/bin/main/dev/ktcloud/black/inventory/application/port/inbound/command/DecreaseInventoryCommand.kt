package dev.ktcloud.black.inventory.application.port.inbound.command

interface DecreaseInventoryCommand {
    fun decrease(command: In)

    data class In(
        val orderId: Long,
        val inventoryId: Long,
        val amount: Int
    )
}