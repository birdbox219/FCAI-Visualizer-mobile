package com.example.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.ui.screens.CompareScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.PracticeScreen
import com.example.ui.screens.VisualizerScreen

sealed class Screen(val route: String, val title: String) {
  object Home : Screen("home", "Explore")
  object Visualizer : Screen("visualizer", "Visualizer")
  object Compare : Screen("compare", "Compare")
  object Practice : Screen("practice", "Quiz")
}

@Composable
fun MainAppNavigation() {
  var currentScreen by remember { mutableStateOf<Screen>(Screen.Home) }
  var selectedAlgorithmId by remember { mutableStateOf("bubble_sort") }

  Scaffold(
    modifier = Modifier.fillMaxSize(),
    bottomBar = {
      NavigationBar(
        modifier = Modifier.testTag("main_bottom_nav")
      ) {
        NavigationBarItem(
          selected = currentScreen is Screen.Home,
          onClick = { currentScreen = Screen.Home },
          icon = { Icon(Icons.Default.Home, contentDescription = "Explore") },
          label = { Text("Explore") },
          modifier = Modifier.testTag("nav_explore")
        )

        NavigationBarItem(
          selected = currentScreen is Screen.Visualizer,
          onClick = { currentScreen = Screen.Visualizer },
          icon = { Icon(Icons.Default.PlayCircle, contentDescription = "Visualizer") },
          label = { Text("Visualizer") },
          modifier = Modifier.testTag("nav_visualizer")
        )

        NavigationBarItem(
          selected = currentScreen is Screen.Compare,
          onClick = { currentScreen = Screen.Compare },
          icon = { Icon(Icons.Default.CompareArrows, contentDescription = "Compare") },
          label = { Text("Compare") },
          modifier = Modifier.testTag("nav_compare")
        )

        NavigationBarItem(
          selected = currentScreen is Screen.Practice,
          onClick = { currentScreen = Screen.Practice },
          icon = { Icon(Icons.Default.Quiz, contentDescription = "Quiz") },
          label = { Text("Quiz") },
          modifier = Modifier.testTag("nav_quiz")
        )
      }
    }
  ) { innerPadding ->
    val modifier = Modifier.padding(innerPadding)

    when (currentScreen) {
      is Screen.Home -> {
        HomeScreen(
          onAlgorithmSelected = { algoId ->
            selectedAlgorithmId = algoId
            currentScreen = Screen.Visualizer
          },
          onNavigateToCompare = {
            currentScreen = Screen.Compare
          },
          onNavigateToQuiz = {
            currentScreen = Screen.Practice
          },
          modifier = modifier
        )
      }

      is Screen.Visualizer -> {
        VisualizerScreen(
          algorithmId = selectedAlgorithmId,
          onNavigateBack = {
            currentScreen = Screen.Home
          },
          modifier = modifier
        )
      }

      is Screen.Compare -> {
        CompareScreen(
          onNavigateBack = {
            currentScreen = Screen.Home
          },
          modifier = modifier
        )
      }

      is Screen.Practice -> {
        PracticeScreen(
          onNavigateBack = {
            currentScreen = Screen.Home
          },
          modifier = modifier
        )
      }
    }
  }
}
