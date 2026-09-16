package com.example.model

import java.util.UUID

data class CartItem(
  val id: String = UUID.randomUUID().toString(),
  val sweetItem: SweetItem,
  val customization: CustomizationSelection,
) {
  val totalPrice: Double
    get() = customization.calculateItemTotal(sweetItem.basePrice)
}
