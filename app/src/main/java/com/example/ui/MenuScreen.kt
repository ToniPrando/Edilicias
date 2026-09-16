package com.example.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Cake
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingBag
import androidx.compose.material.icons.rounded.Tune
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.data.SweetRepository
import com.example.model.CartItem
import com.example.model.Category
import com.example.model.CustomizationSelection
import com.example.model.SweetItem
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun MenuScreen(
  onBackToSplash: () -> Unit,
  onOpenAdmin: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  var selectedCategory by remember { mutableStateOf(Category.ALL) }
  var searchQuery by remember { mutableStateOf("") }
  var itemToCustomize by remember { mutableStateOf<SweetItem?>(null) }
  var showCart by remember { mutableStateOf(false) }

  // Cart state
  val cartItems = remember { androidx.compose.runtime.mutableStateListOf<CartItem>() }

  // Filter items based on Category & Search (reactive to SweetRepository changes)
  val filteredItems = remember(selectedCategory, searchQuery, SweetRepository.sweetItems.size, SweetRepository.sweetItems.toList()) {
    SweetRepository.sweetItems.filter { item ->
      val matchesCategory = selectedCategory == Category.ALL || item.category == selectedCategory
      val matchesSearch = searchQuery.isBlank() ||
        item.name.contains(searchQuery, ignoreCase = true) ||
        item.description.contains(searchQuery, ignoreCase = true) ||
        item.tags.any { it.contains(searchQuery, ignoreCase = true) }
      matchesCategory && matchesSearch
    }
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("menu_screen_root"),
    containerColor = EdiliciasCreamBg,
    topBar = {
      Surface(
        color = Color.White,
        shadowElevation = 3.dp,
      ) {
        TopAppBar(
          title = {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              // Mini Logo
              Image(
                painter = painterResource(id = R.drawable.img_edilicias_logo),
                contentDescription = "Logo Edilicias",
                modifier = Modifier
                  .size(42.dp)
                  .clip(CircleShape)
                  .clickable(onClick = onBackToSplash),
              )
              Column {
                Text(
                  text = "Edilicias",
                  style = MaterialTheme.typography.titleLarge,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasChocolate,
                )
                Text(
                  text = "Doceria Artesanal • Delícias com Afeto",
                  style = MaterialTheme.typography.labelSmall,
                  color = EdiliciasPink,
                  fontWeight = FontWeight.Medium,
                )
              }
            }
          },
          navigationIcon = {
            IconButton(
              onClick = onBackToSplash,
              modifier = Modifier.testTag("back_to_splash_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Voltar para tela inicial",
                tint = EdiliciasChocolate,
              )
            }
          },
          actions = {
            // Admin Panel Shortcut Icon
            IconButton(
              onClick = onOpenAdmin,
              modifier = Modifier.testTag("menu_open_admin_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.AdminPanelSettings,
                contentDescription = "Acessar Painel do Administrador",
                tint = EdiliciasChocolate,
                modifier = Modifier.size(24.dp),
              )
            }

            // Cart Icon with Badge
            IconButton(
              onClick = { showCart = true },
              modifier = Modifier.testTag("cart_open_btn"),
            ) {
              BadgedBox(
                badge = {
                  if (cartItems.isNotEmpty()) {
                    Badge(
                      containerColor = EdiliciasPink,
                      contentColor = Color.White,
                    ) {
                      Text(
                        text = cartItems.sumOf { it.customization.quantity }.toString(),
                        fontWeight = FontWeight.Bold,
                      )
                    }
                  }
                },
              ) {
                Icon(
                  imageVector = Icons.Rounded.ShoppingBag,
                  contentDescription = "Abrir sacola de pedidos",
                  tint = EdiliciasChocolate,
                  modifier = Modifier.size(26.dp),
                )
              }
            }
          },
          colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.White,
            titleContentColor = EdiliciasChocolate,
          ),
        )
      }
    },
  ) { innerPadding ->
    LazyColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding),
      contentPadding = PaddingValues(bottom = 24.dp),
    ) {
      // 1. Search Bar
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
              Text(
                text = "Buscar bolos, brigadeiros, presentes...",
                style = MaterialTheme.typography.bodyMedium,
                color = EdiliciasTextMuted,
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = EdiliciasPink,
              )
            },
            trailingIcon = {
              if (searchQuery.isNotEmpty()) {
                IconButton(onClick = { searchQuery = "" }) {
                  Icon(
                    imageVector = Icons.Rounded.Clear,
                    contentDescription = "Limpar busca",
                    tint = EdiliciasTextMuted,
                  )
                }
              }
            },
            singleLine = true,
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = Color.White,
              unfocusedContainerColor = Color.White,
              focusedBorderColor = EdiliciasPink,
              unfocusedBorderColor = EdiliciasOutline.copy(alpha = 0.7f),
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_sweets_input"),
          )
        }
      }

      // 2. Banner: Monte Seu Doce Personalizado
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.Transparent),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .background(
                brush = Brush.horizontalGradient(
                  colors = listOf(
                    EdiliciasPinkContainer,
                    EdiliciasTealContainer,
                  ),
                ),
              )
              .padding(16.dp),
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(14.dp),
            ) {
              Box(
                modifier = Modifier
                  .size(46.dp)
                  .clip(CircleShape)
                  .background(Color.White),
                contentAlignment = Alignment.Center,
              ) {
                Icon(
                  imageVector = Icons.Rounded.AutoAwesome,
                  contentDescription = null,
                  tint = EdiliciasPink,
                  modifier = Modifier.size(24.dp),
                )
              }

              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "Monte seu Doce com Afeto ✨",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasChocolate,
                )
                Text(
                  text = "Escolha massa, recheios de panela, cobertura e frase personalizada em cada doce!",
                  style = MaterialTheme.typography.bodySmall,
                  color = EdiliciasTextSecondary,
                )
              }
            }
          }
        }
      }

      // 3. Category Horizontal Pills Filter
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Category.values().forEach { category ->
            val isSelected = selectedCategory == category
            val backgroundColor = if (isSelected) EdiliciasPink else Color.White
            val contentColor = if (isSelected) Color.White else EdiliciasChocolate
            val borderColor = if (isSelected) EdiliciasPink else EdiliciasOutline.copy(alpha = 0.6f)

            Surface(
              onClick = { selectedCategory = category },
              shape = RoundedCornerShape(20.dp),
              color = backgroundColor,
              border = BorderStroke(1.2.dp, borderColor),
              shadowElevation = if (isSelected) 3.dp else 1.dp,
              modifier = Modifier.testTag("category_pill_${category.id}"),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
              ) {
                Text(text = category.iconEmoji)
                Text(
                  text = category.displayName,
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = contentColor,
                )
              }
            }
          }
        }
      }

      // 4. Section Title & Results Counter
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 6.dp),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text(
            text = selectedCategory.displayName,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = EdiliciasChocolate,
          )
          Text(
            text = "${filteredItems.size} ${if (filteredItems.size == 1) "opção" else "opções"}",
            style = MaterialTheme.typography.bodySmall,
            color = EdiliciasTextSecondary,
          )
        }
      }

      // 5. Sweets List
      if (filteredItems.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center,
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = EdiliciasTextMuted,
                modifier = Modifier.size(48.dp),
              )
              Text(
                text = "Nenhum doce encontrado",
                style = MaterialTheme.typography.titleSmall,
                color = EdiliciasChocolate,
                fontWeight = FontWeight.Bold,
              )
              Text(
                text = "Tente buscar por outro termo ou selecione 'Todos os Doces'.",
                style = MaterialTheme.typography.bodySmall,
                color = EdiliciasTextSecondary,
              )
            }
          }
        }
      } else {
        items(filteredItems, key = { it.id }) { sweetItem ->
          SweetCard(
            sweetItem = sweetItem,
            onCustomize = { itemToCustomize = sweetItem },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 7.dp),
          )
        }
      }
    }
  }

  // Customization Dialog Modal
  itemToCustomize?.let { item ->
    CustomizeDialog(
      sweetItem = item,
      onDismiss = { itemToCustomize = null },
      onAddToCart = { selection ->
        cartItems.add(CartItem(sweetItem = item, customization = selection))
      },
    )
  }

  // Cart / Order Sheet
  if (showCart) {
    CartSheet(
      cartItems = cartItems.toList(),
      onRemoveItem = { itemToRemove -> cartItems.remove(itemToRemove) },
      onClearCart = { cartItems.clear() },
      onDismiss = { showCart = false },
    )
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SweetCard(
  sweetItem: SweetItem,
  onCustomize: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .clickable(onClick = onCustomize)
      .testTag("sweet_card_${sweetItem.id}"),
  ) {
    Column(modifier = Modifier.fillMaxWidth()) {
      // Photo with overlay badges
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(180.dp),
      ) {
        Image(
          painter = painterResource(id = sweetItem.imageRes),
          contentDescription = sweetItem.name,
          contentScale = ContentScale.Crop,
          modifier = Modifier.fillMaxSize(),
        )

        // Gradient overlay for contrast
        Box(
          modifier = Modifier
            .fillMaxSize()
            .background(
              brush = Brush.verticalGradient(
                colors = listOf(
                  Color.Black.copy(alpha = 0.15f),
                  Color.Transparent,
                  Color.Black.copy(alpha = 0.35f),
                ),
              ),
            ),
        )

        // Top tags
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top,
        ) {
          // Category Pill
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White.copy(alpha = 0.92f),
          ) {
            Text(
              text = "${sweetItem.category.iconEmoji} ${sweetItem.category.displayName}",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            )
          }

          if (sweetItem.isCustomizable) {
            Surface(
              shape = RoundedCornerShape(12.dp),
              color = EdiliciasTeal,
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Tune,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(12.dp),
                )
                Text(
                  text = "Personalizável",
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = FontWeight.Bold,
                  color = Color.White,
                )
              }
            }
          }
        }
      }

      // Card Information & Actions
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
      ) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.Top,
        ) {
          Text(
            text = sweetItem.name,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = EdiliciasChocolate,
            modifier = Modifier.weight(1f),
          )
        }

        Text(
          text = sweetItem.description,
          style = MaterialTheme.typography.bodySmall,
          color = EdiliciasTextSecondary,
          maxLines = 3,
        )

        // Tags pills (e.g. Mais Pedido, Artesanal)
        if (sweetItem.tags.isNotEmpty()) {
          FlowRow(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            sweetItem.tags.forEach { tag ->
              Surface(
                shape = RoundedCornerShape(8.dp),
                color = EdiliciasPinkLight,
              ) {
                Text(
                  text = tag,
                  style = MaterialTheme.typography.labelSmall,
                  color = EdiliciasPink,
                  fontWeight = FontWeight.Medium,
                  modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                )
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(2.dp))

        // Price and Action Button
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Column {
            Text(
              text = "A partir de",
              style = MaterialTheme.typography.labelSmall,
              color = EdiliciasTextMuted,
            )
            Text(
              text = "R$ ${String.format(Locale.GERMANY, "%.2f", sweetItem.basePrice)}",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasPink,
            )
          }

          Button(
            onClick = onCustomize,
            colors = ButtonDefaults.buttonColors(
              containerColor = EdiliciasPink,
              contentColor = Color.White,
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier.testTag("customize_sweet_btn_${sweetItem.id}"),
          ) {
            Icon(
              imageVector = Icons.Rounded.Tune,
              contentDescription = null,
              modifier = Modifier.size(16.dp),
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Personalizar",
              style = MaterialTheme.typography.labelMedium,
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    }
  }
}
