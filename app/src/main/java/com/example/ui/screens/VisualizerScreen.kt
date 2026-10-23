package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithms.AlgorithmEngines
import com.example.data.AlgorithmRepository
import com.example.model.AlgorithmCategory
import com.example.ui.components.ComplexityCard
import com.example.ui.components.ControlPanel
import com.example.ui.components.VisualizerCanvas
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisualizerScreen(
  algorithmId: String,
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler(onBack = onNavigateBack)

  val algorithm = remember(algorithmId) {
    AlgorithmRepository.getAlgorithmById(algorithmId) ?: AlgorithmRepository.algorithms.first()
  }

  // Visualizer State
  var currentArray by remember {
    mutableStateOf(List(10) { Random.nextInt(10, 95) })
  }
  var searchTarget by remember { mutableIntStateOf(42) }
  var targetInputText by remember { mutableStateOf("42") }

  var steps by remember(algorithm.id, currentArray, searchTarget) {
    mutableStateOf(AlgorithmEngines.generateStepsForAlgorithm(algorithm.id, currentArray, searchTarget))
  }

  var currentStepIdx by remember(steps) { mutableIntStateOf(0) }
  var isPlaying by remember { mutableStateOf(false) }
  var playbackSpeed by remember { mutableFloatStateOf(1.0f) }

  // Auto-play loop
  LaunchedEffect(isPlaying, currentStepIdx, playbackSpeed, steps.size) {
    if (isPlaying) {
      if (currentStepIdx < steps.size - 1) {
        val delayMs = (700L / playbackSpeed).toLong().coerceAtLeast(100L)
        delay(delayMs)
        currentStepIdx++
      } else {
        isPlaying = false
      }
    }
  }

  val activeStep = steps.getOrNull(currentStepIdx)

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Column {
            Text(
              text = algorithm.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold
            )
            Text(
              text = algorithm.category.displayName,
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.primary
            )
          }
        },
        navigationIcon = {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("visualizer_back_button")
          ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.surface
        )
      )
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
      Spacer(modifier = Modifier.height(2.dp))

      // Live Step Explanation Banner
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("step_narration_card"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
          containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
        )
      ) {
        Row(
          modifier = Modifier.padding(14.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          Icon(
            imageVector = Icons.Default.Info,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(22.dp)
          )
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = activeStep?.description ?: "Initializing algorithm steps...",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface,
            lineHeight = 20.sp
          )
        }
      }

      // Main Visualizer Canvas
      VisualizerCanvas(
        category = algorithm.category,
        step = activeStep
      )

      // Search target input for searching algorithms
      if (algorithm.category == AlgorithmCategory.SEARCHING) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedTextField(
            value = targetInputText,
            onValueChange = {
              targetInputText = it
              val parsed = it.toIntOrNull()
              if (parsed != null) {
                searchTarget = parsed
                currentStepIdx = 0
                isPlaying = false
              }
            },
            label = { Text("Search Target") },
            singleLine = true,
            modifier = Modifier
              .weight(1f)
              .testTag("search_target_input"),
            shape = RoundedCornerShape(12.dp)
          )
        }
      }

      // Control Panel
      ControlPanel(
        isPlaying = isPlaying,
        currentStepIndex = currentStepIdx,
        totalSteps = steps.size,
        playbackSpeed = playbackSpeed,
        onPlayPauseToggle = { isPlaying = !isPlaying },
        onStepForward = {
          if (currentStepIdx < steps.size - 1) {
            isPlaying = false
            currentStepIdx++
          }
        },
        onStepBackward = {
          if (currentStepIdx > 0) {
            isPlaying = false
            currentStepIdx--
          }
        },
        onReset = {
          isPlaying = false
          currentStepIdx = 0
        },
        onRandomize = {
          isPlaying = false
          currentArray = List(10) { Random.nextInt(10, 95) }
          currentStepIdx = 0
        },
        onSpeedSelected = { playbackSpeed = it },
        onSeek = { targetStep ->
          isPlaying = false
          currentStepIdx = targetStep.coerceIn(0, steps.size - 1)
        }
      )

      // Complexity & Code Snippet Card
      ComplexityCard(algorithm = algorithm)

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
