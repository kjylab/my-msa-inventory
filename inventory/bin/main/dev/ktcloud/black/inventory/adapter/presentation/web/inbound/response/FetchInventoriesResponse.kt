package dev.ktcloud.black.inventory.adapter.presentation.web.inbound.response

import dev.ktcloud.black.inventory.application.dto.web.InventoryDto

data class FetchInventoriesResponse(
    val inventories: List<InventoryDto>
)
