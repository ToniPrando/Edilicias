package com.example.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material.icons.rounded.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.ui.theme.EdiliciasChocolate
import com.example.ui.theme.EdiliciasPink
import com.example.ui.theme.EdiliciasPinkLight
import com.example.ui.theme.EdiliciasTeal
import com.example.ui.theme.EdiliciasTextPrimary
import com.example.ui.theme.EdiliciasTextSecondary

const val ADMIN_DEFAULT_PASSWORD = "123456789"

@Composable
fun AdminPasswordDialog(
  onDismiss: () -> Unit,
  onPasswordSuccess: () -> Unit,
  modifier: Modifier = Modifier,
) {
  var passwordInput by remember { mutableStateOf("") }
  var isPasswordVisible by remember { mutableStateOf(false) }
  var isError by remember { mutableStateOf(false) }
  var errorMessage by remember { mutableStateOf("") }
  val focusRequester = remember { FocusRequester() }

  LaunchedEffect(Unit) {
    focusRequester.requestFocus()
  }

  fun validateAndSubmit() {
    if (passwordInput == ADMIN_DEFAULT_PASSWORD) {
      isError = false
      onPasswordSuccess()
    } else {
      isError = true
      errorMessage = "Senha incorreta! Digite a senha de administrador."
    }
  }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false),
  ) {
    Surface(
      shape = RoundedCornerShape(24.dp),
      color = Color.White,
      tonalElevation = 6.dp,
      shadowElevation = 8.dp,
      modifier = modifier
        .padding(horizontal = 24.dp)
        .fillMaxWidth()
        .testTag("admin_password_dialog"),
    ) {
      Column(
        modifier = Modifier.padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        // Icon header
        Box(
          modifier = Modifier
            .size(60.dp)
            .background(EdiliciasPinkLight, CircleShape),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            imageVector = Icons.Rounded.Lock,
            contentDescription = "Área Protegida",
            tint = EdiliciasPink,
            modifier = Modifier.size(32.dp),
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = "Área do Administrador",
          style = MaterialTheme.typography.titleLarge,
          fontWeight = FontWeight.Bold,
          color = EdiliciasChocolate,
          textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
          text = "Digite a senha padrão de acesso para gerenciar o cardápio e preços da Edilicias.",
          style = MaterialTheme.typography.bodyMedium,
          color = EdiliciasTextSecondary,
          textAlign = TextAlign.Center,
          lineHeight = 20.sp,
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Password input field
        OutlinedTextField(
          value = passwordInput,
          onValueChange = {
            passwordInput = it
            if (isError) isError = false
          },
          label = { Text("Senha do Administrador") },
          placeholder = { Text("Digite a senha") },
          singleLine = true,
          isError = isError,
          visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
          keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
          ),
          keyboardActions = KeyboardActions(
            onDone = { validateAndSubmit() },
          ),
          leadingIcon = {
            Icon(
              imageVector = Icons.Rounded.Key,
              contentDescription = null,
              tint = if (isError) MaterialTheme.colorScheme.error else EdiliciasChocolate,
            )
          },
          trailingIcon = {
            IconButton(
              onClick = { isPasswordVisible = !isPasswordVisible },
              modifier = Modifier.testTag("toggle_password_visibility_btn"),
            ) {
              Icon(
                imageVector = if (isPasswordVisible) Icons.Rounded.VisibilityOff else Icons.Rounded.Visibility,
                contentDescription = if (isPasswordVisible) "Ocultar senha" else "Mostrar senha",
                tint = EdiliciasChocolate.copy(alpha = 0.7f),
              )
            }
          },
          shape = RoundedCornerShape(16.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = EdiliciasPink,
            unfocusedBorderColor = Color(0xFFD6C7B2),
            focusedLabelColor = EdiliciasPink,
            cursorColor = EdiliciasPink,
            focusedTextColor = EdiliciasTextPrimary,
            unfocusedTextColor = EdiliciasTextPrimary,
          ),
          modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .testTag("admin_password_input"),
        )

        if (isError) {
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = errorMessage,
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
              .fillMaxWidth()
              .testTag("admin_password_error_text"),
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Action Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(12.dp),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          TextButton(
            onClick = onDismiss,
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("admin_password_cancel_btn"),
            shape = RoundedCornerShape(16.dp),
          ) {
            Text(
              text = "Cancelar",
              color = EdiliciasChocolate,
              fontWeight = FontWeight.SemiBold,
            )
          }

          Button(
            onClick = { validateAndSubmit() },
            modifier = Modifier
              .weight(1f)
              .height(48.dp)
              .testTag("admin_password_submit_btn"),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = EdiliciasPink,
              contentColor = Color.White,
            ),
          ) {
            Text(
              text = "Entrar",
              fontWeight = FontWeight.Bold,
            )
          }
        }
      }
    }
  }
}
