package com.example.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.DeliveryDining
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CartItem
import com.example.ui.components.SweetImage
import com.example.ui.theme.EdiliciasChocolate
import com.example.ui.theme.EdiliciasCreamBg
import com.example.ui.theme.EdiliciasOutline
import com.example.ui.theme.EdiliciasPink
import com.example.ui.theme.EdiliciasPinkContainer
import com.example.ui.theme.EdiliciasPinkLight
import com.example.ui.theme.EdiliciasTeal
import com.example.ui.theme.EdiliciasTealContainer
import com.example.ui.theme.EdiliciasTextPrimary
import com.example.ui.theme.EdiliciasTextSecondary
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import java.util.Locale

@Composable
fun CartSheet(
  cartItems: List<CartItem>,
  onRemoveItem: (CartItem) -> Unit,
  onClearCart: () -> Unit,
  onDismiss: () -> Unit,
) {
  val context = LocalContext.current
  var customerName by remember { mutableStateOf("") }
  var deliveryAddress by remember { mutableStateOf("") }
  var isDelivery by remember { mutableStateOf(false) }

  val subtotal = cartItems.sumOf { it.totalPrice }
  val deliveryFee = if (isDelivery && cartItems.isNotEmpty()) 8.00 else 0.00
  val total = subtotal + deliveryFee

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.96f)
        .fillMaxHeight(0.92f)
        .clip(RoundedCornerShape(28.dp))
        .testTag("cart_sheet"),
      color = EdiliciasCreamBg,
      tonalElevation = 6.dp,
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Cart Header
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
                imageVector = Icons.Rounded.ShoppingBag,
                contentDescription = null,
                tint = EdiliciasPink,
                modifier = Modifier.size(20.dp),
              )
            }
            Column {
              Text(
                text = "Minha Sacola de Doces",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = EdiliciasTextPrimary,
              )
              Text(
                text = "${cartItems.size} ${if (cartItems.size == 1) "item personalizado" else "itens personalizados"}",
                style = MaterialTheme.typography.bodySmall,
                color = EdiliciasTextSecondary,
              )
            }
          }
          IconButton(
            onClick = onDismiss,
            modifier = Modifier.testTag("close_cart_btn"),
          ) {
            Icon(
              imageVector = Icons.Rounded.Close,
              contentDescription = "Fechar sacola",
              tint = EdiliciasTextSecondary,
            )
          }
        }

        HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

        if (cartItems.isEmpty()) {
          // Empty State
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
              .padding(32.dp),
            contentAlignment = Alignment.Center,
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
              Box(
                modifier = Modifier
                  .size(90.dp)
                  .clip(CircleShape)
                  .background(EdiliciasPinkLight),
                contentAlignment = Alignment.Center,
              ) {
                Icon(
                  imageVector = Icons.Rounded.ShoppingBag,
                  contentDescription = null,
                  tint = EdiliciasPink,
                  modifier = Modifier.size(44.dp),
                )
              }
              Text(
                text = "Sua sacola está vazia",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = EdiliciasChocolate,
              )
              Text(
                text = "Escolha um bolo, brigadeiros ou cupcakes no menu e personalize com seus sabores favoritos!",
                style = MaterialTheme.typography.bodyMedium,
                color = EdiliciasTextSecondary,
                textAlign = TextAlign.Center,
              )
              Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = EdiliciasPink),
                shape = RoundedCornerShape(20.dp),
              ) {
                Text("Ver Cardápio")
              }
            }
          }
        } else {
          // Cart Items List
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp),
          ) {
            item { Spacer(modifier = Modifier.height(10.dp)) }

            items(cartItems, key = { it.id }) { item ->
              CartItemCard(
                cartItem = item,
                onRemove = { onRemoveItem(item) },
              )
            }

            item {
              Spacer(modifier = Modifier.height(6.dp))
              // Delivery / Retirada selection
              Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                modifier = Modifier.fillMaxWidth(),
              ) {
                Column(
                  modifier = Modifier.padding(14.dp),
                  verticalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                  Text(
                    text = "Como deseja receber?",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = EdiliciasChocolate,
                  )
                  Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                  ) {
                    DeliveryTypeButton(
                      title = "Retirar no Balcão",
                      subtitle = "Grátis na doceria",
                      icon = Icons.Rounded.Storefront,
                      isSelected = !isDelivery,
                      onClick = { isDelivery = false },
                      modifier = Modifier.weight(1f),
                      tag = "pickup_option_btn",
                    )
                    DeliveryTypeButton(
                      title = "Entrega Delivery",
                      subtitle = "+ R$ 8,00 taxa",
                      icon = Icons.Rounded.DeliveryDining,
                      isSelected = isDelivery,
                      onClick = { isDelivery = true },
                      modifier = Modifier.weight(1f),
                      tag = "delivery_option_btn",
                    )
                  }

                  // Customer info
                  OutlinedTextField(
                    value = customerName,
                    onValueChange = { customerName = it },
                    placeholder = { Text("Seu Nome Completo") },
                    modifier = Modifier
                      .fillMaxWidth()
                      .testTag("cart_customer_name_input"),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = EdiliciasPink,
                      unfocusedBorderColor = EdiliciasOutline,
                    ),
                    singleLine = true,
                    leadingIcon = {
                      Icon(
                        imageVector = Icons.Rounded.Person,
                        contentDescription = null,
                        tint = EdiliciasPink,
                      )
                    },
                  )

                  if (isDelivery) {
                    OutlinedTextField(
                      value = deliveryAddress,
                      onValueChange = { deliveryAddress = it },
                      placeholder = { Text("Endereço de Entrega (Rua, Número, Bairro)") },
                      modifier = Modifier
                        .fillMaxWidth()
                        .testTag("cart_address_input"),
                      shape = RoundedCornerShape(12.dp),
                      colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EdiliciasTeal,
                        unfocusedBorderColor = EdiliciasOutline,
                      ),
                      maxLines = 2,
                      leadingIcon = {
                        Icon(
                          imageVector = Icons.Rounded.LocationOn,
                          contentDescription = null,
                          tint = EdiliciasTeal,
                        )
                      },
                    )
                  }
                }
              }
            }

            item { Spacer(modifier = Modifier.height(10.dp)) }
          }

          // Sticky Bottom Checkout
          Surface(
            color = Color.White,
            shadowElevation = 8.dp,
            modifier = Modifier.fillMaxWidth(),
          ) {
            Column(
              modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 14.dp),
              verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              // Breakdown
              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
              ) {
                Text(
                  text = "Subtotal doces",
                  style = MaterialTheme.typography.bodyMedium,
                  color = EdiliciasTextSecondary,
                )
                Text(
                  text = "R$ ${String.format(Locale.GERMANY, "%.2f", subtotal)}",
                  style = MaterialTheme.typography.bodyMedium,
                  fontWeight = FontWeight.Medium,
                  color = EdiliciasTextPrimary,
                )
              }

              if (isDelivery) {
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                  Text(
                    text = "Taxa de entrega",
                    style = MaterialTheme.typography.bodyMedium,
                    color = EdiliciasTextSecondary,
                  )
                  Text(
                    text = "R$ ${String.format(Locale.GERMANY, "%.2f", deliveryFee)}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = EdiliciasTeal,
                    fontWeight = FontWeight.Bold,
                  )
                }
              }

              Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
              ) {
                Text(
                  text = "Total do Pedido",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasChocolate,
                )
                Text(
                  text = "R$ ${String.format(Locale.GERMANY, "%.2f", total)}",
                  style = MaterialTheme.typography.headlineSmall,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasPink,
                )
              }

              // Finalize via WhatsApp Button
              Button(
                onClick = {
                  shareOrderWhatsApp(
                    context = context,
                    customerName = customerName,
                    isDelivery = isDelivery,
                    deliveryAddress = deliveryAddress,
                    cartItems = cartItems,
                    total = total,
                  )
                },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = EdiliciasTeal,
                  contentColor = Color.White,
                ),
                modifier = Modifier
                  .fillMaxWidth()
                  .height(50.dp)
                  .testTag("send_order_whatsapp_btn"),
              ) {
                Icon(
                  imageVector = Icons.Rounded.CheckCircle,
                  contentDescription = null,
                  modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = "Finalizar Pedido via WhatsApp",
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
}

@Composable
private fun DeliveryTypeButton(
  title: String,
  subtitle: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  tag: String,
) {
  val border = if (isSelected) BorderStroke(1.8.dp, EdiliciasPink) else BorderStroke(1.dp, EdiliciasOutline)
  val background = if (isSelected) EdiliciasPinkLight else Color.White

  Surface(
    onClick = onClick,
    shape = RoundedCornerShape(14.dp),
    color = background,
    border = border,
    modifier = modifier.testTag(tag),
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
      Icon(
        imageVector = icon,
        contentDescription = null,
        tint = if (isSelected) EdiliciasPink else EdiliciasTextSecondary,
        modifier = Modifier.size(22.dp),
      )
      Column {
        Text(
          text = title,
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          color = if (isSelected) EdiliciasChocolate else EdiliciasTextPrimary,
        )
        Text(
          text = subtitle,
          style = MaterialTheme.typography.bodySmall,
          color = EdiliciasTextSecondary,
        )
      }
    }
  }
}

@Composable
private fun CartItemCard(
  cartItem: CartItem,
  onRemove: () -> Unit,
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = Modifier.fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(14.dp),
      verticalAlignment = Alignment.Top,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      SweetImage(
        sweetItem = cartItem.sweetItem,
        contentDescription = cartItem.sweetItem.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(68.dp)
          .clip(RoundedCornerShape(14.dp)),
      )

      Column(modifier = Modifier.weight(1f)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top,
        ) {
          Column(modifier = Modifier.weight(1f)) {
            Text(
              text = cartItem.sweetItem.name,
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
            Text(
              text = "Qtd: ${cartItem.customization.quantity}x",
              style = MaterialTheme.typography.labelMedium,
              color = EdiliciasPink,
              fontWeight = FontWeight.Bold,
            )
          }

          IconButton(
            onClick = onRemove,
            modifier = Modifier.size(28.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.DeleteOutline,
              contentDescription = "Remover doce",
              tint = Color(0xFFC0392B),
              modifier = Modifier.size(20.dp),
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Customization breakdown
        cartItem.customization.selectedSize?.let {
          CustomOptionBadge("Tamanho: ${it.name}")
        }
        cartItem.customization.selectedDough?.let {
          CustomOptionBadge("Massa: ${it.name}")
        }
        if (cartItem.customization.selectedFillings.isNotEmpty()) {
          CustomOptionBadge("Recheio: ${cartItem.customization.selectedFillings.joinToString { it.name }}")
        }
        cartItem.customization.selectedFrosting?.let {
          CustomOptionBadge("Cobertura: ${it.name}")
        }
        if (cartItem.customization.selectedToppings.isNotEmpty()) {
          CustomOptionBadge("Toppings: ${cartItem.customization.selectedToppings.joinToString { it.name }}")
        }
        if (cartItem.customization.customMessage.isNotBlank()) {
          CustomOptionBadge("Frase: \"${cartItem.customization.customMessage}\"", isMessage = true)
        }
        if (cartItem.customization.specialNotes.isNotBlank()) {
          CustomOptionBadge("Obs: ${cartItem.customization.specialNotes}")
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "R$ ${String.format(Locale.GERMANY, "%.2f", cartItem.totalPrice)}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = EdiliciasPink,
        )
      }
    }
  }
}

@Composable
private fun CustomOptionBadge(text: String, isMessage: Boolean = false) {
  Text(
    text = if (isMessage) "💌 $text" else "• $text",
    style = MaterialTheme.typography.bodySmall,
    color = if (isMessage) EdiliciasPink else EdiliciasTextSecondary,
    fontWeight = if (isMessage) FontWeight.SemiBold else FontWeight.Normal,
    modifier = Modifier.padding(vertical = 1.dp),
  )
}

private fun shareOrderWhatsApp(
  context: Context,
  customerName: String,
  isDelivery: Boolean,
  deliveryAddress: String,
  cartItems: List<CartItem>,
  total: Double,
) {
  val sb = StringBuilder()
  sb.append("✨ *NOVO PEDIDO - EDILICIAS DOCERIA ARTESANAL* ✨\n\n")
  if (customerName.isNotBlank()) {
    sb.append("👤 *Cliente:* $customerName\n")
  }
  sb.append("📍 *Tipo:* ${if (isDelivery) "Entrega Delivery" else "Retirada no Balcão"}\n")
  if (isDelivery && deliveryAddress.isNotBlank()) {
    sb.append("🏡 *Endereço:* $deliveryAddress\n")
  }
  sb.append("\n🍰 *ITENS PERSONALIZADOS:*\n")

  cartItems.forEachIndexed { index, item ->
    sb.append("\n${index + 1}. *${item.sweetItem.name}* (x${item.customization.quantity})\n")
    item.customization.selectedSize?.let { sb.append("   - Tamanho: ${it.name}\n") }
    item.customization.selectedDough?.let { sb.append("   - Massa: ${it.name}\n") }
    if (item.customization.selectedFillings.isNotEmpty()) {
      sb.append("   - Recheios: ${item.customization.selectedFillings.joinToString { it.name }}\n")
    }
    item.customization.selectedFrosting?.let { sb.append("   - Cobertura: ${it.name}\n") }
    if (item.customization.selectedToppings.isNotEmpty()) {
      sb.append("   - Toppings: ${item.customization.selectedToppings.joinToString { it.name }}\n")
    }
    if (item.customization.customMessage.isNotBlank()) {
      sb.append("   - Frase no bolo/doce: \"${item.customization.customMessage}\"\n")
    }
    if (item.customization.specialNotes.isNotBlank()) {
      sb.append("   - Observações: ${item.customization.specialNotes}\n")
    }
    sb.append("   - Subtotal: R$ ${String.format(Locale.GERMANY, "%.2f", item.totalPrice)}\n")
  }

  sb.append("\n💰 *VALOR TOTAL:* R$ ${String.format(Locale.GERMANY, "%.2f", total)}\n")
  sb.append("\n_Feito com afeto pela Edilicias Doceria Artesanal_ 💕")

  val message = sb.toString()

  try {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
      type = "text/plain"
      putExtra(Intent.EXTRA_TEXT, message)
    }
    val chooser = Intent.createChooser(sendIntent, "Enviar Pedido Edilicias")
    context.startActivity(chooser)
  } catch (e: Exception) {
    Toast.makeText(context, "Não foi possível abrir o compartilhamento", Toast.LENGTH_SHORT).show()
  }
}
