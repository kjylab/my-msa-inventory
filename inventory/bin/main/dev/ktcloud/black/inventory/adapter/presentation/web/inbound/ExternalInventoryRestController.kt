package dev.ktcloud.black.inventory.adapter.presentation.web.inbound

import dev.ktcloud.black.inventory.adapter.presentation.web.inbound.request.IncreaseInventoryRequest
import dev.ktcloud.black.inventory.adapter.presentation.web.inbound.response.FetchInventoriesResponse

interface ExternalInventoryRestController {
    fun fetchInventories(): FetchInventoriesResponse
    fun increaseInventory(request: IncreaseInventoryRequest)
}