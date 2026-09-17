package com.example.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AdminPanelSettings
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.EdiliciasChocolate
import com.example.ui.theme.EdiliciasPink
import com.example.ui.theme.EdiliciasPinkContainer
import com.example.ui.theme.EdiliciasPinkLight
import com.example.ui.theme.EdiliciasTeal
import com.example.ui.theme.EdiliciasTextSecondary

@Composable
fun SplashScreen(
  onEnterApp: () -> Unit,
  onOpenAdmin: () -> Unit = {},
  modifier: Modifier = Modifier,
) {
  val interactionSource = remember { MutableInteractionSource() }
  val isPressed by interactionSource.collectIsPressedAsState()

  // Gentle breathing animation to invite touch
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val breathScale by infiniteTransition.animateFloat(
    initialValue = 1.0f,
    targetValue = 1.04f,
    animationSpec = infiniteRepeatable(
      animation = tween(durationMillis = 1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Reverse,
    ),
    label = "breathScale",
  )

  val pressScale by animateFloatAsState(
    targetValue = if (isPressed) 0.94f else breathScale,
    animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
    label = "pressScale",
  )

  // Pure white background requested by the user
  Box(
    modifier = modifier
      .fillMaxSize()
      .background(Color.White)
      .testTag("splash_screen_root"),
    contentAlignment = Alignment.Center,
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      verticalArrangement = Arrangement.Center,
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 24.dp),
    ) {
      // Small decorative brand banner top
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        modifier = Modifier
          .clip(RoundedCornerShape(50.dp))
          .background(EdiliciasPinkLight)
          .padding(horizontal = 14.dp, vertical = 6.dp),
      ) {
        Icon(
          imageVector = Icons.Rounded.Favorite,
          contentDescription = null,
          tint = EdiliciasPink,
          modifier = Modifier.size(14.dp),
        )
        Text(
          text = "Doceria Artesanal",
          color = EdiliciasPink,
          style = MaterialTheme.typography.labelMedium,
          fontWeight = FontWeight.Bold,
          letterSpacing = 1.sp,
        )
      }

      Spacer(modifier = Modifier.height(28.dp))

      // The circular logo - clickable to enter app
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(280.dp)
          .scale(pressScale)
          .shadow(
            elevation = if (isPressed) 4.dp else 12.dp,
            shape = CircleShape,
            ambientColor = EdiliciasPink.copy(alpha = 0.35f),
            spotColor = EdiliciasTeal.copy(alpha = 0.25f),
          )
          .clip(CircleShape)
          .background(Color.White)
          .clickable(
            interactionSource = interactionSource,
            indication = null,
            onClick = onEnterApp,
          )
          .testTag("logo_enter_button"),
      ) {
        Image(
          painter = painterResource(id = R.drawable.img_edilicias_logo),
          contentDescription = "Logotipo da Doceria Edilicias - Toque para entrar no cardápio",
          contentScale = ContentScale.Fit,
          modifier = Modifier
            .fillMaxSize()
            .padding(4.dp),
        )
      }

      Spacer(modifier = Modifier.height(32.dp))

      // Interactive cue button / indicator
      Surface(
        onClick = onEnterApp,
        shape = RoundedCornerShape(32.dp),
        color = EdiliciasPink,
        shadowElevation = 4.dp,
        modifier = Modifier.testTag("tap_to_enter_badge"),
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
        ) {
          Icon(
            imageVector = Icons.Rounded.AutoAwesome,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp),
          )
          Text(
            text = stringResource(id = R.string.splash_tap_to_enter),
            color = Color.White,
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
          )
          Icon(
            imageVector = Icons.Rounded.ArrowForward,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(18.dp),
          )
        }
      }

      Spacer(modifier = Modifier.height(16.dp))

      Text(
        text = "Delícias feitas à mão com ingredientes nobres e muito afeto",
        style = MaterialTheme.typography.bodyMedium,
        color = EdiliciasTextSecondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(horizontal = 24.dp),
      )
    }
  }
}
