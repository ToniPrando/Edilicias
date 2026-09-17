package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.EditNote
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CustomizationSelection
import com.example.model.DoughOption
import com.example.model.FillingOption
import com.example.model.FrostingOption
import com.example.model.SizeOption
import com.example.model.SweetItem
import com.example.model.ToppingOption
import com.example.ui.components.SweetImage
import com.example.ui.theme.EdiliciasChocolate
import com.example.ui.theme.EdiliciasCreamBg
import com.example.ui.theme.EdiliciasGold
import com.example.ui.theme.EdiliciasOutline
import com.example.ui.theme.EdiliciasPink
import com.example.ui.theme.EdiliciasPinkContainer
import com.example.ui.theme.EdiliciasPinkLight
import com.example.ui.theme.EdiliciasTeal
import com.example.ui.theme.EdiliciasTealContainer
import com.example.ui.theme.EdiliciasTextMuted
import com.example.ui.theme.EdiliciasTextPrimary
import com.example.ui.theme.EdiliciasTextSecondary
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CustomizeDialog(
  sweetItem: SweetItem,
  onDismiss: () -> Unit,
  onAddToCart: (CustomizationSelection) -> Unit,
) {
  var selectedSize by remember { mutableStateOf<SizeOption?>(sweetItem.availableSizes.firstOrNull()) }
  var selectedDough by remember { mutableStateOf<DoughOption?>(sweetItem.availableDoughs.firstOrNull()) }
  val selectedFillings = remember {
    mutableStateListOf<FillingOption>().apply {
      sweetItem.availableFillings.firstOrNull()?.let { add(it) }
    }
  }
  var selectedFrosting by remember { mutableStateOf<FrostingOption?>(sweetItem.availableFrostings.firstOrNull()) }
  val selectedToppings = remember { mutableStateListOf<ToppingOption>() }
  var customMessage by remember { mutableStateOf("") }
  var specialNotes by remember { mutableStateOf("") }
  var quantity by remember { mutableIntStateOf(1) }

  // Current dynamic pricing
  val currentSelection = CustomizationSelection(
    selectedSize = selectedSize,
    selectedDough = selectedDough,
    selectedFillings = selectedFillings.toList(),
    selectedFrosting = selectedFrosting,
    selectedToppings = selectedToppings.toList(),
    customMessage = customMessage,
    specialNotes = specialNotes,
    quantity = quantity,
  )
  val totalPrice = currentSelection.calculateItemTotal(sweetItem.basePrice)

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.96f)
        .fillMaxHeight(0.92f)
        .clip(RoundedCornerShape(28.dp))
        .testTag("customize_dialog"),
      color = EdiliciasCreamBg,
      tonalElevation = 6.dp,
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Dialog Top Bar
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 20.dp, vertical = 14.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp),
          ) {
            Box(
              modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(EdiliciasPinkContainer),
              contentAlignment = Alignment.Center,
            ) {
              Icon(
                imageVector = Icons.Rounded.Cake,
                contentDescription = null,
                tint = EdiliciasPink,
                modifier = Modifier.size(20.dp),
              )
            }
            Column {
              Text(
                text = "Personalize seu Doce",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = EdiliciasTextPrimary,
              )
              Text(
                text = sweetItem.name,
                style = MaterialTheme.typography.bodyMedium,
                color = EdiliciasTextSecondary,
              )
            }
          }
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("close_customize_dialog"),
          ) {
            Icon(
              imageVector = Icons.Rounded.Close,
              contentDescription = "Fechar personalização",
              tint = EdiliciasTextSecondary,
            )
          }
        }

        HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

        // Scrollable Options Content
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 18.dp, vertical = 16.dp),
          verticalArrangement = Arrangement.spacedBy(20.dp),
        ) {
          // Hero item summary card
          Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
            modifier = Modifier.fillMaxWidth(),
          ) {
            Row(
              modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
              SweetImage(
                sweetItem = sweetItem,
                contentDescription = sweetItem.name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                  .size(80.dp)
                  .clip(RoundedCornerShape(16.dp)),
              )
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = sweetItem.name,
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasTextPrimary,
                )
                Text(
                  text = sweetItem.description,
                  style = MaterialTheme.typography.bodySmall,
                  color = EdiliciasTextSecondary,
                  maxLines = 2,
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = "A partir de R$ ${String.format(Locale.GERMANY, "%.2f", sweetItem.basePrice)}",
                  style = MaterialTheme.typography.labelLarge,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasPink,
                )
              }
            }
          }

          // Section 1: Tamanho / Quantidade (if sizes exist)
          if (sweetItem.availableSizes.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              SectionHeader(
                title = "1. Escolha o Tamanho",
                subtitle = "Selecione a porção ideal para você ou seu evento",
              )
              sweetItem.availableSizes.forEach { sizeOption ->
                val isSelected = selectedSize?.id == sizeOption.id
                OptionSelectCard(
                  title = sizeOption.name,
                  description = sizeOption.subtitle,
                  priceTag = if (sizeOption.additionalPrice > 0) {
                    "+ R$ ${String.format(Locale.GERMANY, "%.2f", sizeOption.additionalPrice)}"
                  } else "Incluso",
                  isSelected = isSelected,
                  onClick = { selectedSize = sizeOption },
                  tag = "size_option_${sizeOption.id}",
                )
              }
            }
          }

          // Section 2: Tipo de Massa (if doughs exist)
          if (sweetItem.availableDoughs.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              SectionHeader(
                title = "2. Tipo de Massa",
                subtitle = "Feita com ovos frescos e farinha especial artesanal",
              )
              sweetItem.availableDoughs.forEach { dough ->
                val isSelected = selectedDough?.id == dough.id
                OptionSelectCard(
                  title = dough.name,
                  description = dough.description,
                  priceTag = "Incluso",
                  isSelected = isSelected,
                  onClick = { selectedDough = dough },
                  tag = "dough_option_${dough.id}",
                )
              }
            }
          }

          // Section 3: Recheios (Fillings)
          if (sweetItem.availableFillings.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              SectionHeader(
                title = "3. Recheios Artesanais",
                subtitle = "Escolha até ${sweetItem.maxFillingsAllowed} recheios cremosos de panela",
              )
              sweetItem.availableFillings.forEach { filling ->
                val isSelected = selectedFillings.any { it.id == filling.id }
                OptionSelectCard(
                  title = filling.name,
                  description = filling.description,
                  priceTag = "Incluso",
                  isSelected = isSelected,
                  isMultiSelect = true,
                  onClick = {
                    if (isSelected) {
                      if (selectedFillings.size > 1) {
                        selectedFillings.removeAll { it.id == filling.id }
                      }
                    } else {
                      if (selectedFillings.size < sweetItem.maxFillingsAllowed) {
                        selectedFillings.add(filling)
                      } else {
                        // replace oldest
                        selectedFillings.removeAt(0)
                        selectedFillings.add(filling)
                      }
                    }
                  },
                  tag = "filling_option_${filling.id}",
                )
              }
            }
          }

          // Section 4: Coberturas (Frosting)
          if (sweetItem.availableFrostings.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              SectionHeader(
                title = "4. Cobertura Especial",
                subtitle = "Acabamento sedoso que realça o visual e sabor",
              )
              sweetItem.availableFrostings.forEach { frosting ->
                val isSelected = selectedFrosting?.id == frosting.id
                OptionSelectCard(
                  title = frosting.name,
                  description = frosting.description,
                  priceTag = "Incluso",
                  isSelected = isSelected,
                  onClick = { selectedFrosting = frosting },
                  tag = "frosting_option_${frosting.id}",
                )
              }
            }
          }

          // Section 5: Toppings & Confeitos Adicionais
          if (sweetItem.availableToppings.isNotEmpty()) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
              SectionHeader(
                title = "5. Toppings & Confeitos Especiais (Opcional)",
                subtitle = "Personalize o topo com delícias crocantes e nobres",
              )
              sweetItem.availableToppings.forEach { topping ->
                val isSelected = selectedToppings.any { it.id == topping.id }
                OptionSelectCard(
                  title = "${topping.emoji} ${topping.name}",
                  description = "Topping extra especial",
                  priceTag = "+ R$ ${String.format(Locale.GERMANY, "%.2f", topping.price)}",
                  isSelected = isSelected,
                  isMultiSelect = true,
                  onClick = {
                    if (isSelected) {
                      selectedToppings.removeAll { it.id == topping.id }
                    } else {
                      selectedToppings.add(topping)
                    }
                  },
                  tag = "topping_option_${topping.id}",
                )
              }
            }
          }

          // Section 6: Plaquinha / Mensagem Personalizada
          if (sweetItem.allowsCustomMessage) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
              SectionHeader(
                title = "6. Frase de Afeto / Topo Personalizado",
                subtitle = "Escreva o texto para o topo do bolo ou da caixinha (ex: 'Com amor, Edilicias')",
              )
              OutlinedTextField(
                value = customMessage,
                onValueChange = { if (it.length <= 40) customMessage = it },
                placeholder = { Text("Ex: 'Parabéns Maria!', 'Com todo amor', etc.") },
                modifier = Modifier
                  .fillMaxWidth()
                  .testTag("custom_message_input"),
                shape = RoundedCornerShape(14.dp),
                colors = OutlinedTextFieldDefaults.colors(
                  focusedBorderColor = EdiliciasPink,
                  unfocusedBorderColor = EdiliciasOutline,
                  focusedContainerColor = Color.White,
                  unfocusedContainerColor = Color.White,
                  cursorColor = EdiliciasPink,
                ),
                maxLines = 2,
                supportingText = {
                  Text(
                    text = "${customMessage.length}/40 caracteres",
                    color = EdiliciasTextMuted,
                  )
                },
                leadingIcon = {
                  Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = null,
                    tint = EdiliciasPink,
                  )
                },
              )
            }
          }

          // Section 7: Observações especiais
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            SectionHeader(
              title = "7. Alguma observação especial?",
              subtitle = "Alergias, ponto da fruta, embalagem para presente ou instruções",
            )
            OutlinedTextField(
              value = specialNotes,
              onValueChange = { specialNotes = it },
              placeholder = { Text("Ex: 'Laço rosa bem caprichado', 'Frutas vermelhas separadas'...") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("special_notes_input"),
              shape = RoundedCornerShape(14.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EdiliciasTeal,
                unfocusedBorderColor = EdiliciasOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = EdiliciasTeal,
              ),
              maxLines = 2,
              leadingIcon = {
                Icon(
                  imageVector = Icons.Rounded.EditNote,
                  contentDescription = null,
                  tint = EdiliciasTeal,
                )
              },
            )
          }

          Spacer(modifier = Modifier.height(10.dp))
        }

        // Sticky Bottom Bar with Quantity and Add Button
        Surface(
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
          ) {
            // Quantity Stepper
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp),
              modifier = Modifier
                .clip(RoundedCornerShape(24.dp))
                .background(EdiliciasPinkLight)
                .padding(horizontal = 8.dp, vertical = 4.dp),
            ) {
              IconButton(
                onClick = { if (quantity > 1) quantity-- },
                modifier = Modifier
                  .size(32.dp)
                  .testTag("qty_decrease_btn"),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Remove,
                  contentDescription = "Diminuir quantidade",
                  tint = EdiliciasPink,
                  modifier = Modifier.size(18.dp),
                )
              }

              Text(
                text = quantity.toString(),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = EdiliciasChocolate,
                modifier = Modifier.testTag("qty_text"),
              )

              IconButton(
                onClick = { if (quantity < 50) quantity++ },
                modifier = Modifier
                  .size(32.dp)
                  .testTag("qty_increase_btn"),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Add,
                  contentDescription = "Aumentar quantidade",
                  tint = EdiliciasPink,
                  modifier = Modifier.size(18.dp),
                )
              }
            }

            // Add to Bag Button with Live Total
            Button(
              onClick = {
                onAddToCart(currentSelection)
                onDismiss()
              },
              shape = RoundedCornerShape(24.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = EdiliciasPink,
                contentColor = Color.White,
              ),
              modifier = Modifier
                .height(48.dp)
                .testTag("confirm_add_to_cart_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.ShoppingBag,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(8.dp))
              Text(
                text = "Adicionar • R$ ${String.format(Locale.GERMANY, "%.2f", totalPrice)}",
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun SectionHeader(
  title: String,
  subtitle: String,
) {
  Column {
    Text(
      text = title,
      style = MaterialTheme.typography.titleSmall,
      fontWeight = FontWeight.Bold,
      color = EdiliciasChocolate,
    )
    Text(
      text = subtitle,
      style = MaterialTheme.typography.bodySmall,
      color = EdiliciasTextSecondary,
    )
  }
}

@Composable
private fun OptionSelectCard(
  title: String,
  description: String,
  priceTag: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  tag: String,
  isMultiSelect: Boolean = false,
) {
  val borderColor = if (isSelected) EdiliciasPink else EdiliciasOutline.copy(alpha = 0.6f)
  val backgroundColor = if (isSelected) EdiliciasPinkLight else Color.White

  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(14.dp),
    color = backgroundColor,
    border = BorderStroke(if (isSelected) 1.8.dp else 1.dp, borderColor),
    modifier = Modifier
      .fillMaxWidth()
      .testTag(tag),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier.weight(1f),
      ) {
        // Selection indicator circle or checkbox
        Box(
          modifier = Modifier
            .size(22.dp)
            .clip(if (isMultiSelect) RoundedCornerShape(6.dp) else CircleShape)
            .background(if (isSelected) EdiliciasPink else Color.Transparent)
            .then(
              if (!isSelected) {
                Modifier.background(Color.White, if (isMultiSelect) RoundedCornerShape(6.dp) else CircleShape)
              } else Modifier
            ),
          contentAlignment = Alignment.Center,
        ) {
          if (isSelected) {
            Icon(
              imageVector = Icons.Rounded.Check,
              contentDescription = null,
              tint = Color.White,
              modifier = Modifier.size(14.dp),
            )
          }
        }

        Column {
          Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) EdiliciasChocolate else EdiliciasTextPrimary,
          )
          if (description.isNotBlank()) {
            Text(
              text = description,
              style = MaterialTheme.typography.bodySmall,
              color = EdiliciasTextSecondary,
            )
          }
        }
      }

      Text(
        text = priceTag,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = if (isSelected) EdiliciasPink else EdiliciasTextSecondary,
      )
    }
  }
}
