package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlgorithmCategory
import com.example.model.AlgorithmStep
import com.example.ui.theme.VisualizerBarCompare
import com.example.ui.theme.VisualizerBarDefault
import com.example.ui.theme.VisualizerBarPivot
import com.example.ui.theme.VisualizerBarSelected
import com.example.ui.theme.VisualizerBarSorted
import com.example.ui.theme.VisualizerBarSwap

@Composable
fun VisualizerCanvas(
  category: AlgorithmCategory,
  step: AlgorithmStep?,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .height(260.dp)
      .testTag("visualizer_canvas_container"),
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
  ) {
    if (step == null || step.arrayData.isEmpty()) {
      Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(
          text = "Generate or run algorithm to start visualization",
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          style = MaterialTheme.typography.bodyMedium
        )
      }
      return@Surface
    }

    when (category) {
      AlgorithmCategory.SORTING, AlgorithmCategory.SEARCHING -> {
        BarChartVisualization(step = step)
      }
      AlgorithmCategory.DATA_STRUCTURES -> {
        DataStructureVisualization(step = step)
      }
      AlgorithmCategory.TREES, AlgorithmCategory.GRAPHS -> {
        NodeGraphVisualization(step = step)
      }
    }
  }
}

@Composable
private fun BarChartVisualization(step: AlgorithmStep) {
  val data = step.arrayData
  val maxVal = (data.maxOrNull() ?: 100).coerceAtLeast(1)

  Row(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 12.dp, vertical = 16.dp),
    horizontalArrangement = Arrangement.spacedBy(4.dp),
    verticalAlignment = Alignment.Bottom
  ) {
    data.forEachIndexed { index, value ->
      val barColor by animateColorAsState(
        targetValue = when {
          step.sortedIndices.contains(index) -> VisualizerBarSorted
          step.swappingIndices.contains(index) -> VisualizerBarSwap
          step.comparingIndices.contains(index) -> VisualizerBarCompare
          index == step.pivotIndex -> VisualizerBarPivot
          index == step.highlightIndex -> VisualizerBarSelected
          else -> VisualizerBarDefault
        },
        animationSpec = tween(durationMillis = 150),
        label = "barColor_$index"
      )

      val targetFraction = (value.toFloat() / maxVal.toFloat()).coerceIn(0.08f, 1f)
      val heightFraction by animateFloatAsState(
        targetValue = targetFraction,
        animationSpec = tween(durationMillis = 200),
        label = "barHeight_$index"
      )

      Column(
        modifier = Modifier
          .weight(1f)
          .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
      ) {
        // Value Text
        Text(
          text = "$value",
          color = MaterialTheme.colorScheme.onSurface,
          fontSize = if (data.size > 14) 9.sp else 11.sp,
          fontFamily = FontFamily.Monospace,
          fontWeight = FontWeight.Bold,
          textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(3.dp))

        // Bar
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(heightFraction * 0.78f)
            .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
            .background(barColor)
            .shadow(if (step.comparingIndices.contains(index) || step.swappingIndices.contains(index)) 4.dp else 0.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Index
        Text(
          text = "[$index]",
          color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
          fontSize = if (data.size > 14) 8.sp else 10.sp,
          fontFamily = FontFamily.Monospace,
          textAlign = TextAlign.Center
        )
      }
    }
  }
}

@Composable
private fun DataStructureVisualization(step: AlgorithmStep) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = if (step.extraLabel.isNotBlank()) step.extraLabel else "Size: ${step.arrayData.size}",
      color = MaterialTheme.colorScheme.primary,
      fontWeight = FontWeight.Bold,
      style = MaterialTheme.typography.titleSmall
    )

    Spacer(modifier = Modifier.height(16.dp))

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      modifier = Modifier.padding(horizontal = 8.dp)
    ) {
      itemsIndexed(step.arrayData) { idx, item ->
        val isHighlighted = idx == step.highlightIndex
        val isSwapping = step.swappingIndices.contains(idx)

        val nodeColor = when {
          isSwapping -> VisualizerBarSwap
          isHighlighted -> VisualizerBarSelected
          else -> MaterialTheme.colorScheme.primaryContainer
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Box(
            modifier = Modifier
              .size(54.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(nodeColor)
              .border(
                width = if (isHighlighted || isSwapping) 2.dp else 1.dp,
                color = if (isHighlighted) VisualizerBarCompare else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(12.dp)
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "$item",
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              fontFamily = FontFamily.Monospace,
              color = MaterialTheme.colorScheme.onPrimaryContainer
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "idx $idx",
            fontSize = 10.sp,
            fontFamily = FontFamily.Monospace,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}

@Composable
private fun NodeGraphVisualization(step: AlgorithmStep) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    if (step.extraLabel.isNotBlank()) {
      Text(
        text = step.extraLabel,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium
      )
      Spacer(modifier = Modifier.height(12.dp))
    }

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      itemsIndexed(step.arrayData) { idx, nodeVal ->
        val isVisited = step.sortedIndices.contains(idx)
        val isVisiting = idx == step.highlightIndex || step.comparingIndices.contains(idx)

        val circleColor = when {
          isVisiting -> VisualizerBarCompare
          isVisited -> VisualizerBarSorted
          else -> MaterialTheme.colorScheme.surfaceVariant
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
          Box(
            modifier = Modifier
              .size(50.dp)
              .clip(CircleShape)
              .background(circleColor)
              .border(
                width = 2.dp,
                color = if (isVisiting) VisualizerBarSwap else MaterialTheme.colorScheme.outline,
                shape = CircleShape
              ),
            contentAlignment = Alignment.Center
          ) {
            Text(
              text = "$nodeVal",
              color = if (isVisited || isVisiting) Color.White else MaterialTheme.colorScheme.onSurface,
              fontWeight = FontWeight.Bold,
              fontSize = 16.sp,
              fontFamily = FontFamily.Monospace
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "Node $idx",
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
          )
        }
      }
    }
  }
}
