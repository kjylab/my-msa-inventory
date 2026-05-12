package dev.ktcloud.black.inventory.adapter.presentation.web.inbound.request

data class IncreaseInventoryRequest(
    val inventoryId: Long,
    val amount: Int
)
