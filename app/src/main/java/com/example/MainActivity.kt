package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.data.SweetRepository
import com.example.ui.AdminPasswordDialog
import com.example.ui.AdminScreen
import com.example.ui.MenuScreen
import com.example.ui.SplashScreen
import com.example.ui.theme.MyApplicationTheme

enum class AppScreen {
  SPLASH,
  MENU,
  ADMIN,
}

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    SweetRepository.initialize(applicationContext)
    setContent {
      MyApplicationTheme {
        EdiliciasApp()
      }
    }
  }
}

@Composable
fun EdiliciasApp(
  initialScreen: AppScreen = AppScreen.SPLASH,
  modifier: Modifier = Modifier,
) {
  var currentScreen by rememberSaveable { mutableStateOf(initialScreen) }
  var showAdminPasswordDialog by rememberSaveable { mutableStateOf(false) }

  BackHandler(enabled = currentScreen != AppScreen.SPLASH) {
    currentScreen = if (currentScreen == AppScreen.ADMIN) AppScreen.MENU else AppScreen.SPLASH
  }

  if (showAdminPasswordDialog) {
    AdminPasswordDialog(
      onDismiss = { showAdminPasswordDialog = false },
      onPasswordSuccess = {
        showAdminPasswordDialog = false
        currentScreen = AppScreen.ADMIN
      },
    )
  }

  AnimatedContent(
    targetState = currentScreen,
    transitionSpec = { fadeIn() togetherWith fadeOut() },
    label = "ScreenTransition",
    modifier = modifier,
  ) { screen ->
    when (screen) {
      AppScreen.SPLASH -> {
        SplashScreen(
          onEnterApp = { currentScreen = AppScreen.MENU },
          modifier = Modifier.fillMaxSize(),
        )
      }
      AppScreen.MENU -> {
        MenuScreen(
          onBackToSplash = { currentScreen = AppScreen.SPLASH },
          onOpenAdmin = { showAdminPasswordDialog = true },
          modifier = Modifier.fillMaxSize(),
        )
      }
      AppScreen.ADMIN -> {
        AdminScreen(
          onBack = { currentScreen = AppScreen.MENU },
          onPreviewClientMenu = { currentScreen = AppScreen.MENU },
          modifier = Modifier.fillMaxSize(),
        )
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  SplashScreen(onEnterApp = {}, modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun SplashPreview() {
  MyApplicationTheme {
    SplashScreen(onEnterApp = {})
  }
}

@Preview(showBackground = true)
@Composable
fun MenuPreview() {
  MyApplicationTheme {
    MenuScreen(onBackToSplash = {})
  }
}

