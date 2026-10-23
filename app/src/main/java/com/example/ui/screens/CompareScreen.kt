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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.algorithms.AlgorithmEngines
import com.example.data.AlgorithmRepository
import com.example.model.AlgorithmCategory
import com.example.ui.components.VisualizerCanvas
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechEmerald
import com.example.ui.theme.TechRose
import kotlinx.coroutines.delay
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CompareScreen(
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler(onBack = onNavigateBack)

  val sortingAlgorithms = remember {
    AlgorithmRepository.algorithms.filter { it.category == AlgorithmCategory.SORTING }
  }

  var algoA by remember { mutableStateOf(sortingAlgorithms.firstOrNull() ?: AlgorithmRepository.algorithms[0]) }
  var algoB by remember {
    mutableStateOf(sortingAlgorithms.getOrNull(3) ?: sortingAlgorithms.lastOrNull() ?: AlgorithmRepository.algorithms[0])
  }

  var sharedArray by remember {
    mutableStateOf(List(8) { Random.nextInt(15, 95) })
  }

  var stepsA by remember(algoA.id, sharedArray) {
    mutableStateOf(AlgorithmEngines.generateStepsForAlgorithm(algoA.id, sharedArray))
  }
  var stepsB by remember(algoB.id, sharedArray) {
    mutableStateOf(AlgorithmEngines.generateStepsForAlgorithm(algoB.id, sharedArray))
  }

  var stepIdxA by remember(stepsA) { mutableIntStateOf(0) }
  var stepIdxB by remember(stepsB) { mutableIntStateOf(0) }
  var isPlaying by remember { mutableStateOf(false) }

  // Dual Playback loop
  LaunchedEffect(isPlaying, stepIdxA, stepIdxB, stepsA.size, stepsB.size) {
    if (isPlaying) {
      val canAdvanceA = stepIdxA < stepsA.size - 1
      val canAdvanceB = stepIdxB < stepsB.size - 1

      if (canAdvanceA || canAdvanceB) {
        delay(400L)
        if (canAdvanceA) stepIdxA++
        if (canAdvanceB) stepIdxB++
      } else {
        isPlaying = false
      }
    }
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = {
          Text("Algorithm Race / Compare", fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("compare_back_button")
          ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
          }
        }
      )
    }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(horizontal = 16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      // Comparison controls: Play, Reset, Randomize
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          FilledIconButton(
            onClick = { isPlaying = !isPlaying },
            modifier = Modifier.testTag("compare_play_button")
          ) {
            Icon(
              imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
              contentDescription = if (isPlaying) "Pause Race" else "Start Race"
            )
          }

          IconButton(
            onClick = {
              isPlaying = false
              stepIdxA = 0
              stepIdxB = 0
            },
            modifier = Modifier.testTag("compare_reset_button")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = "Reset race")
          }
        }

        IconButton(
          onClick = {
            isPlaying = false
            sharedArray = List(8) { Random.nextInt(15, 95) }
            stepIdxA = 0
            stepIdxB = 0
          },
          modifier = Modifier.testTag("compare_randomize_button")
        ) {
          Icon(Icons.Default.Shuffle, contentDescription = "New random dataset", tint = MaterialTheme.colorScheme.primary)
        }
      }

      // Algorithm A Pane
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, TechCyan.copy(alpha = 0.4f)),
        modifier = Modifier.testTag("compare_card_a")
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Algorithm A: ${algoA.name}",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = TechCyan
            )
            Text(
              text = "Step $stepIdxA / ${stepsA.size - 1}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          VisualizerCanvas(
            category = AlgorithmCategory.SORTING,
            step = stepsA.getOrNull(stepIdxA),
            modifier = Modifier.height(180.dp)
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = stepsA.getOrNull(stepIdxA)?.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
          )
        }
      }

      // Algorithm B Pane
      Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, TechRose.copy(alpha = 0.4f)),
        modifier = Modifier.testTag("compare_card_b")
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Algorithm B: ${algoB.name}",
              style = MaterialTheme.typography.titleSmall,
              fontWeight = FontWeight.Bold,
              color = TechRose
            )
            Text(
              text = "Step $stepIdxB / ${stepsB.size - 1}",
              style = MaterialTheme.typography.labelSmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }

          Spacer(modifier = Modifier.height(8.dp))

          VisualizerCanvas(
            category = AlgorithmCategory.SORTING,
            step = stepsB.getOrNull(stepIdxB),
            modifier = Modifier.height(180.dp)
          )

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = stepsB.getOrNull(stepIdxB)?.description ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
          )
        }
      }

      // Summary Comparison
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        modifier = Modifier.fillMaxWidth()
      ) {
        Column(modifier = Modifier.padding(14.dp)) {
          Text(
            text = "Comparative Metrics",
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "• ${algoA.name}: Total ${stepsA.size} operations | Space: ${algoA.spaceComplexity} | Avg: ${algoA.timeAverage}",
            style = MaterialTheme.typography.bodySmall
          )
          Text(
            text = "• ${algoB.name}: Total ${stepsB.size} operations | Space: ${algoB.spaceComplexity} | Avg: ${algoB.timeAverage}",
            style = MaterialTheme.typography.bodySmall
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
