package com.example.ui

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.DeleteOutline
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Link
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Storefront
import androidx.compose.material.icons.rounded.WarningAmber
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.R
import com.example.data.SweetRepository
import com.example.model.Category
import com.example.model.SweetItem
import com.example.ui.components.SweetImage
import com.example.ui.theme.EdiliciasChocolate
import com.example.ui.theme.EdiliciasCreamBg
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
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminScreen(
  onBack: () -> Unit,
  onPreviewClientMenu: () -> Unit,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current
  var searchQuery by remember { mutableStateOf("") }
  var selectedCategory by remember { mutableStateOf(Category.ALL) }

  // Dialog states
  var editingItem by remember { mutableStateOf<SweetItem?>(null) }
  var isCreatingNew by remember { mutableStateOf(false) }
  var itemToDelete by remember { mutableStateOf<SweetItem?>(null) }
  var showResetConfirm by remember { mutableStateOf(false) }

  // Filtered sweets (reactive to SweetRepository changes)
  val filteredItems = remember(selectedCategory, searchQuery, SweetRepository.sweetItems.size, SweetRepository.sweetItems.toList()) {
    SweetRepository.sweetItems.filter { item ->
      val matchesCat = selectedCategory == Category.ALL || item.category == selectedCategory
      val matchesQuery = searchQuery.isBlank() ||
        item.name.contains(searchQuery, ignoreCase = true) ||
        item.description.contains(searchQuery, ignoreCase = true)
      matchesCat && matchesQuery
    }
  }

  Scaffold(
    modifier = modifier
      .fillMaxSize()
      .testTag("admin_screen_root"),
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
              Box(
                modifier = Modifier
                  .size(38.dp)
                  .clip(CircleShape)
                  .background(EdiliciasChocolate),
                contentAlignment = Alignment.Center,
              ) {
                Icon(
                  imageVector = Icons.Rounded.AdminPanelSettings,
                  contentDescription = null,
                  tint = Color.White,
                  modifier = Modifier.size(22.dp),
                )
              }
              Column {
                Text(
                  text = "Área Administrativa (ADM)",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasChocolate,
                )
                Text(
                  text = "Gerenciamento do Cardápio Edilicias",
                  style = MaterialTheme.typography.labelSmall,
                  color = EdiliciasPink,
                  fontWeight = FontWeight.Medium,
                )
              }
            }
          },
          navigationIcon = {
            IconButton(
              onClick = onBack,
              modifier = Modifier.testTag("admin_back_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.ArrowBack,
                contentDescription = "Voltar para o cardápio",
                tint = EdiliciasChocolate,
              )
            }
          },
          actions = {
            // Button to preview how the customer sees the menu
            OutlinedButton(
              onClick = onPreviewClientMenu,
              shape = RoundedCornerShape(16.dp),
              border = BorderStroke(1.dp, EdiliciasTeal),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = EdiliciasTeal,
              ),
              contentPadding = PaddingValues(horizontal = 10.dp, vertical = 6.dp),
              modifier = Modifier
                .padding(end = 8.dp)
                .testTag("admin_preview_client_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.Storefront,
                contentDescription = null,
                modifier = Modifier.size(16.dp),
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "Ver Cardápio",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
              )
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
      contentPadding = PaddingValues(bottom = 32.dp),
    ) {
      // 1. Admin Actions Banner (Add sweet + stats)
      item {
        Card(
          shape = RoundedCornerShape(20.dp),
          colors = CardDefaults.cardColors(containerColor = Color.White),
          elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        ) {
          Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
            ) {
              Column {
                Text(
                  text = "Controle de Produtos",
                  style = MaterialTheme.typography.titleSmall,
                  fontWeight = FontWeight.Bold,
                  color = EdiliciasChocolate,
                )
                Text(
                  text = "${SweetRepository.sweetItems.size} doces cadastrados no sistema",
                  style = MaterialTheme.typography.bodySmall,
                  color = EdiliciasTextSecondary,
                )
              }

              // Add New Sweet Button
              Button(
                onClick = { isCreatingNew = true },
                shape = RoundedCornerShape(20.dp),
                colors = ButtonDefaults.buttonColors(
                  containerColor = EdiliciasPink,
                  contentColor = Color.White,
                ),
                modifier = Modifier.testTag("admin_add_sweet_btn"),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Add,
                  contentDescription = null,
                  modifier = Modifier.size(18.dp),
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                  text = "Acrescentar Doce",
                  style = MaterialTheme.typography.labelMedium,
                  fontWeight = FontWeight.Bold,
                )
              }
            }

            HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Text(
                text = "💡 Clique em 'Editar' para alterar valores ou 'Excluir' para remover do cardápio.",
                style = MaterialTheme.typography.bodySmall,
                color = EdiliciasTextSecondary,
                modifier = Modifier.weight(1f),
              )
              IconButton(
                onClick = { showResetConfirm = true },
                modifier = Modifier.size(32.dp),
              ) {
                Icon(
                  imageVector = Icons.Rounded.Refresh,
                  contentDescription = "Restaurar cardápio inicial",
                  tint = EdiliciasTextMuted,
                  modifier = Modifier.size(18.dp),
                )
              }
            }
          }
        }
      }

      // 2. Search Field
      item {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        ) {
          OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
              Text(
                text = "Buscar doce por nome...",
                style = MaterialTheme.typography.bodyMedium,
                color = EdiliciasTextMuted,
              )
            },
            leadingIcon = {
              Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = EdiliciasChocolate,
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
            shape = RoundedCornerShape(16.dp),
            colors = OutlinedTextFieldDefaults.colors(
              focusedContainerColor = Color.White,
              unfocusedContainerColor = Color.White,
              focusedBorderColor = EdiliciasChocolate,
              unfocusedBorderColor = EdiliciasOutline.copy(alpha = 0.7f),
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_search_input"),
          )
        }
      }

      // 3. Category Filter Chips
      item {
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 10.dp),
          horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
          Category.values().forEach { category ->
            val isSelected = selectedCategory == category
            val backgroundColor = if (isSelected) EdiliciasChocolate else Color.White
            val contentColor = if (isSelected) Color.White else EdiliciasChocolate

            Surface(
              onClick = { selectedCategory = category },
              shape = RoundedCornerShape(16.dp),
              color = backgroundColor,
              border = BorderStroke(1.dp, if (isSelected) EdiliciasChocolate else EdiliciasOutline),
              shadowElevation = if (isSelected) 2.dp else 0.dp,
              modifier = Modifier.testTag("admin_cat_chip_${category.id}"),
            ) {
              Row(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp),
              ) {
                Text(text = category.iconEmoji)
                Text(
                  text = category.displayName,
                  style = MaterialTheme.typography.labelSmall,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                  color = contentColor,
                )
              }
            }
          }
        }
      }

      // 4. Items List in Admin Mode
      if (filteredItems.isEmpty()) {
        item {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(40.dp),
            contentAlignment = Alignment.Center,
          ) {
            Text(
              text = "Nenhum doce encontrado nesta categoria.",
              style = MaterialTheme.typography.bodyMedium,
              color = EdiliciasTextSecondary,
            )
          }
        }
      } else {
        items(filteredItems, key = { it.id }) { item ->
          AdminSweetCard(
            sweetItem = item,
            onEdit = { editingItem = item },
            onDelete = { itemToDelete = item },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp),
          )
        }
      }
    }
  }

  // Edit / Create Dialog
  if (isCreatingNew || editingItem != null) {
    SweetEditorDialog(
      itemToEdit = editingItem,
      onDismiss = {
        editingItem = null
        isCreatingNew = false
      },
      onSave = { savedItem ->
        if (editingItem != null) {
          SweetRepository.updateItem(savedItem)
          Toast.makeText(context, "Doce '${savedItem.name}' atualizado!", Toast.LENGTH_SHORT).show()
        } else {
          SweetRepository.addItem(savedItem)
          Toast.makeText(context, "Novo doce '${savedItem.name}' adicionado ao cardápio!", Toast.LENGTH_SHORT).show()
        }
        editingItem = null
        isCreatingNew = false
      },
    )
  }

  // Delete confirmation dialog
  itemToDelete?.let { item ->
    AlertDialog(
      onDismissRequest = { itemToDelete = null },
      icon = {
        Icon(
          imageVector = Icons.Rounded.WarningAmber,
          contentDescription = null,
          tint = Color(0xFFC0392B),
          modifier = Modifier.size(36.dp),
        )
      },
      title = {
        Text(
          text = "Excluir Doce?",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = EdiliciasChocolate,
        )
      },
      text = {
        Text(
          text = "Tem certeza que deseja excluir '${item.name}' do cardápio vendido na doceria?",
          style = MaterialTheme.typography.bodyMedium,
          color = EdiliciasTextSecondary,
        )
      },
      confirmButton = {
        Button(
          onClick = {
            SweetRepository.deleteItem(item.id)
            Toast.makeText(context, "Doce '${item.name}' removido do cardápio", Toast.LENGTH_SHORT).show()
            itemToDelete = null
          },
          colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC0392B)),
          modifier = Modifier.testTag("confirm_delete_btn"),
        ) {
          Text("Excluir", color = Color.White)
        }
      },
      dismissButton = {
        TextButton(onClick = { itemToDelete = null }) {
          Text("Cancelar", color = EdiliciasTextSecondary)
        }
      },
    )
  }

  // Reset to default confirmation dialog
  if (showResetConfirm) {
    AlertDialog(
      onDismissRequest = { showResetConfirm = false },
      title = { Text("Restaurar Cardápio Padrão?") },
      text = { Text("Isso voltará a lista de doces para os itens padrão iniciais da Edilicias.") },
      confirmButton = {
        Button(
          onClick = {
            SweetRepository.resetToDefaults()
            Toast.makeText(context, "Cardápio restaurado para o padrão!", Toast.LENGTH_SHORT).show()
            showResetConfirm = false
          },
          colors = ButtonDefaults.buttonColors(containerColor = EdiliciasTeal),
        ) {
          Text("Restaurar")
        }
      },
      dismissButton = {
        TextButton(onClick = { showResetConfirm = false }) {
          Text("Cancelar")
        }
      },
    )
  }
}

@Composable
private fun AdminSweetCard(
  sweetItem: SweetItem,
  onEdit: () -> Unit,
  onDelete: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Card(
    shape = RoundedCornerShape(18.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    modifier = modifier
      .fillMaxWidth()
      .testTag("admin_item_card_${sweetItem.id}"),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
      // Thumbnail
      SweetImage(
        sweetItem = sweetItem,
        contentDescription = sweetItem.name,
        contentScale = ContentScale.Crop,
        modifier = Modifier
          .size(76.dp)
          .clip(RoundedCornerShape(14.dp)),
      )

      // Content info
      Column(modifier = Modifier.weight(1f)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = EdiliciasPinkLight,
          ) {
            Text(
              text = "${sweetItem.category.iconEmoji} ${sweetItem.category.displayName}",
              style = MaterialTheme.typography.labelSmall,
              color = EdiliciasPink,
              fontWeight = FontWeight.Bold,
              modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
            )
          }

          if (sweetItem.isCustomizable) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = EdiliciasTealContainer,
            ) {
              Text(
                text = "Personalizável",
                style = MaterialTheme.typography.labelSmall,
                color = EdiliciasTeal,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(3.dp))

        Text(
          text = sweetItem.name,
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = EdiliciasChocolate,
          maxLines = 1,
        )

        Text(
          text = sweetItem.description,
          style = MaterialTheme.typography.bodySmall,
          color = EdiliciasTextSecondary,
          maxLines = 1,
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
          text = "R$ ${String.format(Locale.GERMANY, "%.2f", sweetItem.basePrice)}",
          style = MaterialTheme.typography.titleMedium,
          fontWeight = FontWeight.Bold,
          color = EdiliciasPink,
        )
      }

      // Actions: Edit & Delete
      Column(
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.End,
      ) {
        // Edit Button
        Surface(
          onClick = onEdit,
          shape = RoundedCornerShape(12.dp),
          color = EdiliciasTealContainer,
          modifier = Modifier.testTag("edit_sweet_btn_${sweetItem.id}"),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.Edit,
              contentDescription = "Editar doce",
              tint = EdiliciasTeal,
              modifier = Modifier.size(16.dp),
            )
            Text(
              text = "Editar",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = EdiliciasTeal,
            )
          }
        }

        // Delete Button
        Surface(
          onClick = onDelete,
          shape = RoundedCornerShape(12.dp),
          color = Color(0xFFFDEAE8),
          modifier = Modifier.testTag("delete_sweet_btn_${sweetItem.id}"),
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
          ) {
            Icon(
              imageVector = Icons.Rounded.DeleteOutline,
              contentDescription = "Excluir doce",
              tint = Color(0xFFC0392B),
              modifier = Modifier.size(16.dp),
            )
            Text(
              text = "Excluir",
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = Color(0xFFC0392B),
            )
          }
        }
      }
    }
  }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SweetEditorDialog(
  itemToEdit: SweetItem?,
  onDismiss: () -> Unit,
  onSave: (SweetItem) -> Unit,
) {
  val context = LocalContext.current

  var name by remember { mutableStateOf(itemToEdit?.name ?: "") }
  var category by remember {
    mutableStateOf(itemToEdit?.category ?: Category.CAKES)
  }
  var priceText by remember {
    mutableStateOf(
      if (itemToEdit != null) String.format(Locale.US, "%.2f", itemToEdit.basePrice) else ""
    )
  }
  var description by remember { mutableStateOf(itemToEdit?.description ?: "") }
  var isCustomizable by remember { mutableStateOf(itemToEdit?.isCustomizable ?: true) }
  var allowsMessage by remember { mutableStateOf(itemToEdit?.allowsCustomMessage ?: true) }

  // Photo selection: Presets, Device Photo Picker, or URL
  var selectedImageRes by remember {
    mutableStateOf(itemToEdit?.imageRes ?: R.drawable.img_bolo)
  }
  var customImageUri by remember {
    mutableStateOf(itemToEdit?.customImageUri ?: "")
  }
  var urlInputText by remember {
    mutableStateOf(
      if (itemToEdit?.customImageUri?.startsWith("http") == true) itemToEdit.customImageUri ?: "" else ""
    )
  }
  var isUrlInputExpanded by remember {
    mutableStateOf(itemToEdit?.customImageUri?.startsWith("http") == true)
  }

  val photoPickerLauncher = rememberLauncherForActivityResult(
    contract = ActivityResultContracts.PickVisualMedia()
  ) { uri: Uri? ->
    if (uri != null) {
      val savedUri = try {
        val dir = java.io.File(context.filesDir, "sweet_images").apply { if (!exists()) mkdirs() }
        val targetFile = java.io.File(dir, "sweet_${System.currentTimeMillis()}.jpg")
        context.contentResolver.openInputStream(uri)?.use { input ->
          targetFile.outputStream().use { output ->
            input.copyTo(output)
          }
        }
        Uri.fromFile(targetFile).toString()
      } catch (e: Exception) {
        uri.toString()
      }
      customImageUri = savedUri
      isUrlInputExpanded = false
      Toast.makeText(context, "Foto do dispositivo selecionada e salva!", Toast.LENGTH_SHORT).show()
    }
  }

  var nameError by remember { mutableStateOf(false) }
  var priceError by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth(0.95f)
        .fillMaxSize(0.90f)
        .clip(RoundedCornerShape(24.dp))
        .testTag("sweet_editor_dialog"),
      color = EdiliciasCreamBg,
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Header
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
            horizontalArrangement = Arrangement.spacedBy(8.dp),
          ) {
            Icon(
              imageVector = if (itemToEdit != null) Icons.Rounded.Edit else Icons.Rounded.Add,
              contentDescription = null,
              tint = EdiliciasPink,
            )
            Text(
              text = if (itemToEdit != null) "Editar Doce" else "Acrescentar Novo Doce",
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(
              imageVector = Icons.Rounded.Close,
              contentDescription = "Fechar",
              tint = EdiliciasTextSecondary,
            )
          }
        }

        HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

        // Form Fields
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
          verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
          // 1. Nome do Doce
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "Nome do Doce *",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
            OutlinedTextField(
              value = name,
              onValueChange = {
                name = it
                nameError = false
              },
              placeholder = { Text("Ex: Bolo Red Velvet com Morangos") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_input_name"),
              shape = RoundedCornerShape(12.dp),
              isError = nameError,
              supportingText = {
                if (nameError) Text("O nome é obrigatório", color = Color.Red)
              },
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EdiliciasPink,
                unfocusedBorderColor = EdiliciasOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
              ),
            )
          }

          // 2. Categoria
          Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(
              text = "Categoria *",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
            FlowRow(
              horizontalArrangement = Arrangement.spacedBy(8.dp),
              verticalArrangement = Arrangement.spacedBy(6.dp),
            ) {
              Category.values().filter { it != Category.ALL }.forEach { cat ->
                val isSelected = category == cat
                Surface(
                  onClick = { category = cat },
                  shape = RoundedCornerShape(12.dp),
                  color = if (isSelected) EdiliciasPink else Color.White,
                  border = BorderStroke(1.dp, if (isSelected) EdiliciasPink else EdiliciasOutline),
                ) {
                  Text(
                    text = "${cat.iconEmoji} ${cat.displayName}",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color.White else EdiliciasChocolate,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                  )
                }
              }
            }
          }

          // 3. Preço Base (R$)
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "Preço Base (R$) *",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
            OutlinedTextField(
              value = priceText,
              onValueChange = {
                priceText = it
                priceError = false
              },
              placeholder = { Text("Ex: 48.00 ou 48,00") },
              keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
              modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_input_price"),
              shape = RoundedCornerShape(12.dp),
              isError = priceError,
              supportingText = {
                if (priceError) Text("Digite um valor numérico válido (ex: 45.00)", color = Color.Red)
              },
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EdiliciasPink,
                unfocusedBorderColor = EdiliciasOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
              ),
            )
          }

          // 4. Descrição
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
              text = "Descrição do Doce",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )
            OutlinedTextField(
              value = description,
              onValueChange = { description = it },
              placeholder = { Text("Descreva os ingredientes, maciez e encantos deste doce...") },
              modifier = Modifier
                .fillMaxWidth()
                .testTag("admin_input_description"),
              shape = RoundedCornerShape(12.dp),
              maxLines = 3,
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = EdiliciasTeal,
                unfocusedBorderColor = EdiliciasOutline,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
              ),
            )
          }

          // 5. Foto do Doce (Dispositivo, URL ou Padrão)
          Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Text(
              text = "Foto do Doce *",
              style = MaterialTheme.typography.labelLarge,
              fontWeight = FontWeight.Bold,
              color = EdiliciasChocolate,
            )

            // Current Image Preview Card
            Card(
              shape = RoundedCornerShape(18.dp),
              colors = CardDefaults.cardColors(containerColor = Color.White),
              elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
              modifier = Modifier.fillMaxWidth(),
            ) {
              Column(
                modifier = Modifier.padding(14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.spacedBy(14.dp),
                ) {
                  Box(
                    modifier = Modifier
                      .size(90.dp)
                      .clip(RoundedCornerShape(14.dp))
                      .border(1.dp, EdiliciasOutline, RoundedCornerShape(14.dp)),
                  ) {
                    SweetImage(
                      imageRes = selectedImageRes,
                      customImageUri = customImageUri.ifBlank { null },
                      contentDescription = "Prévia da imagem do doce",
                      contentScale = ContentScale.Crop,
                      modifier = Modifier.fillMaxSize(),
                    )
                  }

                  Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                  ) {
                    val (sourceBadge, sourceColor) = when {
                      customImageUri.startsWith("content:") || customImageUri.startsWith("file:") ->
                        Pair("Foto do Dispositivo", EdiliciasTeal)
                      customImageUri.startsWith("http://") || customImageUri.startsWith("https://") ->
                        Pair("Imagem via URL", EdiliciasPink)
                      else -> Pair("Ilustração Padrão", EdiliciasChocolate)
                    }

                    Surface(
                      shape = RoundedCornerShape(8.dp),
                      color = sourceColor.copy(alpha = 0.12f),
                    ) {
                      Text(
                        text = sourceBadge,
                        style = MaterialTheme.typography.labelSmall,
                        color = sourceColor,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                      )
                    }

                    Text(
                      text = if (customImageUri.isNotBlank()) "Imagem personalizada ativa" else "Imagem ilustrativa padrão",
                      style = MaterialTheme.typography.bodySmall,
                      color = EdiliciasTextSecondary,
                    )

                    if (customImageUri.isNotBlank()) {
                      TextButton(
                        onClick = {
                          customImageUri = ""
                          urlInputText = ""
                          isUrlInputExpanded = false
                        },
                        contentPadding = PaddingValues(0.dp),
                      ) {
                        Icon(
                          imageVector = Icons.Rounded.Clear,
                          contentDescription = null,
                          tint = Color(0xFFC0392B),
                          modifier = Modifier.size(16.dp),
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                          text = "Remover foto personalizada",
                          style = MaterialTheme.typography.labelSmall,
                          color = Color(0xFFC0392B),
                        )
                      }
                    }
                  }
                }

                HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

                // Action buttons: Pick from device & Enter URL
                Row(
                  modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                  // Button 1: Escolher do Dispositivo
                  Button(
                    onClick = {
                      photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                      )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                      containerColor = EdiliciasTeal,
                      contentColor = Color.White,
                    ),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("admin_pick_image_device_btn"),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                  ) {
                    Icon(
                      imageVector = Icons.Rounded.PhotoLibrary,
                      contentDescription = null,
                      modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "Dispositivo",
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = FontWeight.Bold,
                    )
                  }

                  // Button 2: Inserir Link / URL
                  OutlinedButton(
                    onClick = {
                      isUrlInputExpanded = !isUrlInputExpanded
                    },
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.2.dp, if (isUrlInputExpanded) EdiliciasPink else EdiliciasOutline),
                    colors = ButtonDefaults.outlinedButtonColors(
                      contentColor = if (isUrlInputExpanded) EdiliciasPink else EdiliciasChocolate
                    ),
                    modifier = Modifier
                      .weight(1f)
                      .testTag("admin_toggle_url_btn"),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 8.dp),
                  ) {
                    Icon(
                      imageVector = Icons.Rounded.Link,
                      contentDescription = null,
                      modifier = Modifier.size(18.dp),
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                      text = "URL da Web",
                      style = MaterialTheme.typography.labelSmall,
                      fontWeight = FontWeight.Bold,
                    )
                  }
                }

                // Expandable URL text field
                AnimatedVisibility(visible = isUrlInputExpanded) {
                  Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                  ) {
                    OutlinedTextField(
                      value = urlInputText,
                      onValueChange = {
                        urlInputText = it
                        customImageUri = it.trim()
                      },
                      placeholder = { Text("https://exemplo.com/foto-do-doce.jpg") },
                      leadingIcon = {
                        Icon(
                          imageVector = Icons.Rounded.Link,
                          contentDescription = null,
                          tint = EdiliciasPink,
                        )
                      },
                      trailingIcon = {
                        if (urlInputText.isNotEmpty()) {
                          IconButton(onClick = {
                            urlInputText = ""
                            customImageUri = ""
                          }) {
                            Icon(
                              imageVector = Icons.Rounded.Clear,
                              contentDescription = "Limpar URL",
                              tint = EdiliciasTextSecondary,
                            )
                          }
                        }
                      },
                      modifier = Modifier
                        .fillMaxWidth()
                        .testTag("admin_input_image_url"),
                      shape = RoundedCornerShape(12.dp),
                      singleLine = true,
                      supportingText = {
                        Text(
                          text = "Insira o link direto da imagem na internet (JPG, PNG ou WebP)",
                          style = MaterialTheme.typography.bodySmall,
                          color = EdiliciasTextSecondary,
                        )
                      },
                      colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = EdiliciasPink,
                        unfocusedBorderColor = EdiliciasOutline,
                        focusedContainerColor = Color(0xFFFCFAF9),
                        unfocusedContainerColor = Color(0xFFFCFAF9),
                      ),
                    )
                  }
                }

                // Preset options fallback
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                  Text(
                    text = "Ou escolha uma imagem padrão:",
                    style = MaterialTheme.typography.labelSmall,
                    color = EdiliciasTextSecondary,
                  )
                  Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    val presets = listOf(
                      Pair(R.drawable.img_bolo, "Bolo"),
                      Pair(R.drawable.img_brigadeiros, "Brigadeiros"),
                      Pair(R.drawable.img_cupcakes, "Cupcakes"),
                    )
                    presets.forEach { (res, label) ->
                      val isSelected = selectedImageRes == res && customImageUri.isBlank()
                      Surface(
                        onClick = {
                          selectedImageRes = res
                          customImageUri = ""
                          urlInputText = ""
                          isUrlInputExpanded = false
                        },
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSelected) EdiliciasPinkLight else Color(0xFFF9F7F5),
                        border = BorderStroke(
                          if (isSelected) 1.5.dp else 1.dp,
                          if (isSelected) EdiliciasPink else EdiliciasOutline
                        ),
                        modifier = Modifier.weight(1f),
                      ) {
                        Row(
                          modifier = Modifier.padding(horizontal = 6.dp, vertical = 6.dp),
                          verticalAlignment = Alignment.CenterVertically,
                          horizontalArrangement = Arrangement.Center,
                        ) {
                          Image(
                            painter = painterResource(id = res),
                            contentDescription = label,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                              .size(24.dp)
                              .clip(RoundedCornerShape(6.dp)),
                          )
                          Spacer(modifier = Modifier.width(4.dp))
                          Text(
                            text = label,
                            style = MaterialTheme.typography.labelSmall,
                            color = if (isSelected) EdiliciasChocolate else EdiliciasTextSecondary,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                          )
                        }
                      }
                    }
                  }
                }
              }
            }
          }

          // 6. Opções de Personalização Switches
          Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            modifier = Modifier.fillMaxWidth(),
          ) {
            Column(
              modifier = Modifier.padding(14.dp),
              verticalArrangement = Arrangement.spacedBy(10.dp),
            ) {
              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Permitir Personalização Completa",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = EdiliciasChocolate,
                  )
                  Text(
                    text = "Cliente poderá escolher tamanhos, massas, recheios e toppings",
                    style = MaterialTheme.typography.bodySmall,
                    color = EdiliciasTextSecondary,
                  )
                }
                Switch(
                  checked = isCustomizable,
                  onCheckedChange = { isCustomizable = it },
                  colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = EdiliciasPink,
                  ),
                )
              }

              HorizontalDivider(color = EdiliciasOutline.copy(alpha = 0.5f))

              Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
              ) {
                Column(modifier = Modifier.weight(1f)) {
                  Text(
                    text = "Permitir Frase de Afeto / Plaquinha",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = EdiliciasChocolate,
                  )
                  Text(
                    text = "Exibe campo para cliente escrever dedicatória personalizada",
                    style = MaterialTheme.typography.bodySmall,
                    color = EdiliciasTextSecondary,
                  )
                }
                Switch(
                  checked = allowsMessage,
                  onCheckedChange = { allowsMessage = it },
                  colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    checkedTrackColor = EdiliciasTeal,
                  ),
                )
              }
            }
          }
        }

        // Bottom Actions
        Surface(
          color = Color.White,
          shadowElevation = 8.dp,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 20.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
          ) {
            OutlinedButton(
              onClick = onDismiss,
              shape = RoundedCornerShape(20.dp),
              modifier = Modifier.weight(1f),
            ) {
              Text("Cancelar", color = EdiliciasTextSecondary)
            }

            Button(
              onClick = {
                val cleanPrice = priceText.replace(",", ".").trim()
                val parsedPrice = cleanPrice.toDoubleOrNull()

                var valid = true
                if (name.isBlank()) {
                  nameError = true
                  valid = false
                }
                if (parsedPrice == null || parsedPrice <= 0) {
                  priceError = true
                  valid = false
                }

                if (valid && parsedPrice != null) {
                  val finalCustomUri = customImageUri.trim().ifEmpty { null }
                  val resultItem = itemToEdit?.copy(
                    name = name.trim(),
                    category = category,
                    basePrice = parsedPrice,
                    description = description.ifBlank { "Doce artesanal feito com carinho pela Edilicias." },
                    imageRes = selectedImageRes,
                    customImageUri = finalCustomUri,
                    isCustomizable = isCustomizable,
                    allowsCustomMessage = allowsMessage,
                  ) ?: SweetItem(
                    id = "sweet_${UUID.randomUUID().toString().take(8)}",
                    name = name.trim(),
                    category = category,
                    basePrice = parsedPrice,
                    description = description.ifBlank { "Doce artesanal feito com carinho pela Edilicias." },
                    imageRes = selectedImageRes,
                    customImageUri = finalCustomUri,
                    isCustomizable = isCustomizable,
                    tags = listOf("Novidade", "Artesanal"),
                    availableSizes = when (category) {
                      Category.CAKES -> SweetRepository.cakeSizes
                      Category.BRIGADEIROS -> SweetRepository.brigadeiroSizes
                      Category.CUPCAKES -> SweetRepository.cupcakeSizes
                      else -> SweetRepository.cakeSizes.take(2)
                    },
                    availableDoughs = if (category == Category.BRIGADEIROS) emptyList() else SweetRepository.defaultDoughOptions,
                    availableFillings = SweetRepository.defaultFillingOptions,
                    availableFrostings = if (category == Category.BRIGADEIROS) emptyList() else SweetRepository.defaultFrostingOptions,
                    availableToppings = SweetRepository.defaultToppings,
                    allowsCustomMessage = allowsMessage,
                  )
                  onSave(resultItem)
                }
              },
              shape = RoundedCornerShape(20.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = EdiliciasPink,
                contentColor = Color.White,
              ),
              modifier = Modifier
                .weight(1.5f)
                .testTag("admin_save_sweet_btn"),
            ) {
              Icon(
                imageVector = Icons.Rounded.Check,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (itemToEdit != null) "Salvar Alterações" else "Adicionar ao Cardápio",
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
