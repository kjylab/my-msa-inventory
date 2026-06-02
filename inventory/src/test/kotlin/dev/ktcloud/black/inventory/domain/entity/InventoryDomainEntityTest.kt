package dev.ktcloud.black.inventory.domain.entity

import dev.ktcloud.black.inventory.domain.exception.InventoryException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.Test

class InventoryDomainEntityTest {

    private fun inventory(quantity: Int = 100) = InventoryDomainEntity(
        id = 1L,
        productId = "product-1",
        skuCode = "SKU-001",
        _quantity = quantity,
    )

    @Test
    fun `재고가 충분하면 decreaseQuantity 호출 시 정상적으로 감소한다`() {
        val inv = inventory(100)

        inv.decreaseQuantity(30)

        assertThat(inv.quantity).isEqualTo(70)
    }

    @Test
    fun `재고가 부족하면 decreaseQuantity 호출 시 InventoryNotEnough 예외가 발생한다`() {
        val inv = inventory(10)

        assertThatThrownBy { inv.decreaseQuantity(50) }
            .isInstanceOf(InventoryException.InventoryNotEnough::class.java)
    }

    @Test
    fun `재고와 감소량이 같으면 0이 된다`() {
        val inv = inventory(50)

        inv.decreaseQuantity(50)

        assertThat(inv.quantity).isEqualTo(0)
    }

    @Test
    fun `increaseQuantity 호출 시 재고가 증가한다`() {
        val inv = inventory(100)

        inv.increaseQuantity(50)

        assertThat(inv.quantity).isEqualTo(150)
    }

    @Test
    fun `setQuantity 호출 시 재고가 지정한 값으로 설정된다`() {
        val inv = inventory(100)

        inv.setQuantity(200)

        assertThat(inv.quantity).isEqualTo(200)
    }
}
