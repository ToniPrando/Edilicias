package com.example.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.R
import com.example.data.SweetRepository
import com.example.model.Category
import com.example.model.SweetItem

@Entity(tableName = "sweets")
data class SweetEntity(
  @PrimaryKey val id: String,
  val name: String,
  val categoryName: String,
  val description: String,
  val basePrice: Double,
  val imageRes: Int,
  val customImageUri: String? = null,
  val isCustomizable: Boolean = true,
  val tagsCsv: String = "",
  val defaultSize: String = "Porção Padrão",
  val allowsCustomMessage: Boolean = true,
  val maxFillingsAllowed: Int = 2,
)

fun SweetEntity.toSweetItem(): SweetItem {
  val cat = try {
    Category.valueOf(categoryName)
  } catch (e: Exception) {
    Category.CAKES
  }
  val initial = SweetRepository.initialSweetItems.find { it.id == id }
  if (initial != null) {
    return initial.copy(
      name = name,
      category = cat,
      description = description,
      basePrice = basePrice,
      imageRes = if (imageRes != 0) imageRes else initial.imageRes,
      customImageUri = customImageUri,
      isCustomizable = isCustomizable,
      tags = if (tagsCsv.isBlank()) initial.tags else tagsCsv.split(","),
      defaultSize = defaultSize,
      allowsCustomMessage = allowsCustomMessage,
      maxFillingsAllowed = maxFillingsAllowed,
    )
  }
  return SweetItem(
    id = id,
    name = name,
    category = cat,
    description = description,
    basePrice = basePrice,
    imageRes = if (imageRes != 0) imageRes else R.drawable.img_bolo,
    customImageUri = customImageUri,
    isCustomizable = isCustomizable,
    tags = if (tagsCsv.isBlank()) listOf("Artesanal") else tagsCsv.split(","),
    defaultSize = defaultSize,
    availableSizes = when (cat) {
      Category.CAKES -> SweetRepository.cakeSizes
      Category.BRIGADEIROS -> SweetRepository.brigadeiroSizes
      Category.CUPCAKES -> SweetRepository.cupcakeSizes
      else -> SweetRepository.cakeSizes.take(2)
    },
    availableDoughs = if (cat == Category.BRIGADEIROS) emptyList() else SweetRepository.defaultDoughOptions,
    availableFillings = SweetRepository.defaultFillingOptions,
    availableFrostings = if (cat == Category.BRIGADEIROS) emptyList() else SweetRepository.defaultFrostingOptions,
    availableToppings = SweetRepository.defaultToppings,
    maxFillingsAllowed = maxFillingsAllowed,
    allowsCustomMessage = allowsCustomMessage,
  )
}

fun SweetItem.toEntity(): SweetEntity {
  return SweetEntity(
    id = id,
    name = name,
    categoryName = category.name,
    description = description,
    basePrice = basePrice,
    imageRes = imageRes,
    customImageUri = customImageUri,
    isCustomizable = isCustomizable,
    tagsCsv = tags.joinToString(","),
    defaultSize = defaultSize,
    allowsCustomMessage = allowsCustomMessage,
    maxFillingsAllowed = maxFillingsAllowed,
  )
}
