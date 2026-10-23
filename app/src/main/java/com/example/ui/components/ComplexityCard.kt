package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AlgorithmItem
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechEmerald
import com.example.ui.theme.TechRose

@Composable
fun ComplexityCard(
  algorithm: AlgorithmItem,
  modifier: Modifier = Modifier
) {
  var isCodeExpanded by remember { mutableStateOf(false) }

  Card(
    modifier = modifier
      .fillMaxWidth()
      .testTag("complexity_card"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Complexity & Analysis",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
          onClick = { isCodeExpanded = !isCodeExpanded },
          modifier = Modifier.testTag("toggle_code_button")
        ) {
          Icon(
            imageVector = if (isCodeExpanded) Icons.Default.ExpandLess else Icons.Default.Code,
            contentDescription = "Toggle code view",
            tint = MaterialTheme.colorScheme.primary
          )
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Complexity badges
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        ComplexityBadge(
          title = "Best Time",
          value = algorithm.timeBest,
          color = TechEmerald,
          modifier = Modifier.weight(1f)
        )
        ComplexityBadge(
          title = "Avg Time",
          value = algorithm.timeAverage,
          color = TechCyan,
          modifier = Modifier.weight(1f)
        )
        ComplexityBadge(
          title = "Worst Time",
          value = algorithm.timeWorst,
          color = TechRose,
          modifier = Modifier.weight(1f)
        )
        ComplexityBadge(
          title = "Space",
          value = algorithm.spaceComplexity,
          color = MaterialTheme.colorScheme.primary,
          modifier = Modifier.weight(1f)
        )
      }

      AnimatedVisibility(visible = isCodeExpanded) {
        Column(modifier = Modifier.padding(top = 14.dp)) {
          Text(
            text = "Kotlin Implementation",
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.SemiBold
          )
          Spacer(modifier = Modifier.height(6.dp))
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(8.dp))
              .background(Color(0xFF0F172A))
              .padding(12.dp)
          ) {
            Text(
              text = algorithm.codeSnippet,
              color = Color(0xFFE2E8F0),
              fontFamily = FontFamily.Monospace,
              fontSize = 12.sp,
              lineHeight = 18.sp
            )
          }
        }
      }
    }
  }
}

@Composable
private fun ComplexityBadge(
  title: String,
  value: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    modifier = modifier,
    shape = RoundedCornerShape(10.dp),
    color = color.copy(alpha = 0.12f),
    border = androidx.compose.foundation.BorderStroke(1.dp, color.copy(alpha = 0.35f))
  ) {
    Column(
      modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = title,
        fontSize = 9.sp,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Medium
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = value,
        fontSize = 11.sp,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        color = color
      )
    }
  }
}
