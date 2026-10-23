package com.example.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FastForward
import androidx.compose.material.icons.filled.FastRewind
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp

@Composable
fun ControlPanel(
  isPlaying: Boolean,
  currentStepIndex: Int,
  totalSteps: Int,
  playbackSpeed: Float,
  onPlayPauseToggle: () -> Unit,
  onStepForward: () -> Unit,
  onStepBackward: () -> Unit,
  onReset: () -> Unit,
  onRandomize: () -> Unit,
  onSpeedSelected: (Float) -> Unit,
  onSeek: (Int) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier
      .fillMaxWidth()
      .testTag("control_panel_surface"),
    shape = RoundedCornerShape(16.dp),
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 2.dp,
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
  ) {
    Column(
      modifier = Modifier.padding(14.dp)
    ) {
      // Step counter and Progress Slider
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Step $currentStepIndex / ${if (totalSteps > 0) totalSteps - 1 else 0}",
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.primary
        )
        Text(
          text = if (totalSteps > 0) "${((currentStepIndex.toFloat() / (totalSteps - 1).coerceAtLeast(1)) * 100).toInt()}%" else "0%",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      }

      Slider(
        value = if (totalSteps > 1) currentStepIndex.toFloat() else 0f,
        onValueChange = { onSeek(it.toInt()) },
        valueRange = 0f..(if (totalSteps > 1) (totalSteps - 1).toFloat() else 1f),
        steps = if (totalSteps > 2) totalSteps - 2 else 0,
        modifier = Modifier
          .fillMaxWidth()
          .testTag("playback_slider")
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Main Controls
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
      ) {
        IconButton(
          onClick = onReset,
          modifier = Modifier.testTag("reset_button")
        ) {
          Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = "Reset playback",
            tint = MaterialTheme.colorScheme.onSurface
          )
        }

        IconButton(
          onClick = onStepBackward,
          enabled = currentStepIndex > 0,
          modifier = Modifier.testTag("step_backward_button")
        ) {
          Icon(
            imageVector = Icons.Default.FastRewind,
            contentDescription = "Step backward",
            tint = if (currentStepIndex > 0) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
          )
        }

        // Large Play/Pause FAB-like button
        FilledIconButton(
          onClick = onPlayPauseToggle,
          modifier = Modifier
            .size(54.dp)
            .testTag("play_pause_button"),
          colors = IconButtonDefaults.filledIconButtonColors(
            containerColor = MaterialTheme.colorScheme.primary
          )
        ) {
          Icon(
            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
            contentDescription = if (isPlaying) "Pause" else "Play",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(30.dp)
          )
        }

        IconButton(
          onClick = onStepForward,
          enabled = currentStepIndex < totalSteps - 1,
          modifier = Modifier.testTag("step_forward_button")
        ) {
          Icon(
            imageVector = Icons.Default.FastForward,
            contentDescription = "Step forward",
            tint = if (currentStepIndex < totalSteps - 1) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
          )
        }

        IconButton(
          onClick = onRandomize,
          modifier = Modifier.testTag("randomize_button")
        ) {
          Icon(
            imageVector = Icons.Default.Shuffle,
            contentDescription = "Generate new array",
            tint = MaterialTheme.colorScheme.primary
          )
        }
      }

      Spacer(modifier = Modifier.height(8.dp))

      // Speed selection
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        listOf(0.5f, 1.0f, 2.0f, 4.0f).forEach { speed ->
          FilterChip(
            selected = playbackSpeed == speed,
            onClick = { onSpeedSelected(speed) },
            label = { Text("${speed}x") },
            modifier = Modifier
              .padding(horizontal = 4.dp)
              .testTag("speed_chip_${speed}x")
          )
        }
      }
    }
  }
}
