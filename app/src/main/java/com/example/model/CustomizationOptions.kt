package com.example.model

data class SizeOption(
  val id: String,
  val name: String,
  val subtitle: String,
  val priceMultiplier: Double = 1.0,
  val additionalPrice: Double = 0.0,
)

data class DoughOption(
  val id: String,
  val name: String,
  val description: String,
)

data class FillingOption(
  val id: String,
  val name: String,
  val description: String,
)

data class FrostingOption(
  val id: String,
  val name: String,
  val description: String,
)

data class ToppingOption(
  val id: String,
  val name: String,
  val price: Double,
  val emoji: String = "✨",
)

data class CustomizationSelection(
  val selectedSize: SizeOption? = null,
  val selectedDough: DoughOption? = null,
  val selectedFillings: List<FillingOption> = emptyList(),
  val selectedFrosting: FrostingOption? = null,
  val selectedToppings: List<ToppingOption> = emptyList(),
  val customMessage: String = "",
  val specialNotes: String = "",
  val quantity: Int = 1,
) {
  fun calculateItemTotal(basePrice: Double): Double {
    val sizeBase = if (selectedSize != null) {
      (basePrice * selectedSize.priceMultiplier) + selectedSize.additionalPrice
    } else {
      basePrice
    }
    val toppingsExtra = selectedToppings.sumOf { it.price }
    return (sizeBase + toppingsExtra) * quantity
  }
}
