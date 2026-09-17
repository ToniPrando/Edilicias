package com.example.model

import androidx.annotation.DrawableRes

data class SweetItem(
  val id: String,
  val name: String,
  val category: Category,
  val description: String,
  val basePrice: Double,
  @DrawableRes val imageRes: Int,
  val customImageUri: String? = null,
  val isCustomizable: Boolean = true,
  val tags: List<String> = emptyList(),
  val defaultSize: String = "Porção Padrão",
  val availableSizes: List<SizeOption> = emptyList(),
  val availableDoughs: List<DoughOption> = emptyList(),
  val availableFillings: List<FillingOption> = emptyList(),
  val availableFrostings: List<FrostingOption> = emptyList(),
  val availableToppings: List<ToppingOption> = emptyList(),
  val maxFillingsAllowed: Int = 2,
  val allowsCustomMessage: Boolean = true,
)
