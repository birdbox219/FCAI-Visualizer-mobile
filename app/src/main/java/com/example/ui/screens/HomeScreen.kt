package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.CompareArrows
import androidx.compose.material.icons.filled.DataArray
import androidx.compose.material.icons.filled.Hub
import androidx.compose.material.icons.filled.Quiz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AlgorithmRepository
import com.example.model.AlgorithmCategory
import com.example.model.AlgorithmItem
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechEmerald
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechRose

@Composable
fun HomeScreen(
  onAlgorithmSelected: (String) -> Unit,
  onNavigateToCompare: () -> Unit,
  onNavigateToQuiz: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedCategory by remember { mutableStateOf<AlgorithmCategory?>(null) }
  var searchQuery by remember { mutableStateOf("") }

  val filteredAlgorithms = remember(selectedCategory, searchQuery) {
    AlgorithmRepository.algorithms.filter { algo ->
      (selectedCategory == null || algo.category == selectedCategory) &&
        (searchQuery.isBlank() || algo.name.contains(searchQuery, ignoreCase = true) || algo.description.contains(searchQuery, ignoreCase = true))
    }
  }

  LazyColumn(
    modifier = modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    item {
      Spacer(modifier = Modifier.height(8.dp))
      // Hero Header
      HeroCard(
        onCompareClick = onNavigateToCompare,
        onQuizClick = onNavigateToQuiz
      )
    }

    item {
      // Search Bar
      OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        modifier = Modifier
          .fillMaxWidth()
          .testTag("home_search_input"),
        placeholder = { Text("Search algorithm or data structure...") },
        leadingIcon = {
          Icon(Icons.Default.Search, contentDescription = "Search", tint = MaterialTheme.colorScheme.primary)
        },
        singleLine = true,
        shape = RoundedCornerShape(14.dp)
      )
    }

    item {
      // Category Filter Chips
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        item {
          FilterChip(
            selected = selectedCategory == null,
            onClick = { selectedCategory = null },
            label = { Text("All (${AlgorithmRepository.algorithms.size})") },
            modifier = Modifier.testTag("filter_all")
          )
        }
        items(AlgorithmCategory.values()) { category ->
          FilterChip(
            selected = selectedCategory == category,
            onClick = {
              selectedCategory = if (selectedCategory == category) null else category
            },
            label = { Text(category.displayName) },
            leadingIcon = {
              Icon(
                imageVector = getCategoryIcon(category),
                contentDescription = null,
                modifier = Modifier.size(16.dp)
              )
            },
            modifier = Modifier.testTag("filter_${category.name.lowercase()}")
          )
        }
      }
    }

    items(filteredAlgorithms, key = { it.id }) { algo ->
      AlgorithmCard(
        algorithm = algo,
        onClick = { onAlgorithmSelected(algo.id) }
      )
    }

    item {
      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}

@Composable
private fun HeroCard(
  onCompareClick: () -> Unit,
  onQuizClick: () -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .testTag("hero_banner_card"),
    shape = RoundedCornerShape(20.dp),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant
    )
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.linearGradient(
            colors = listOf(
              TechIndigo.copy(alpha = 0.25f),
              TechCyan.copy(alpha = 0.15f)
            )
          )
        )
        .padding(18.dp)
    ) {
      Column {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(44.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(TechIndigo),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = Icons.Default.Sort,
              contentDescription = "Logo",
              tint = Color.White
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Text(
              text = "FCAI Visualizer",
              style = MaterialTheme.typography.titleLarge,
              fontWeight = FontWeight.ExtraBold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = "Interactive Algorithm & Data Structure Playground",
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          // Compare shortcut
          Surface(
            modifier = Modifier
              .weight(1f)
              .clickable(onClick = onCompareClick)
              .testTag("hero_compare_button"),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, TechCyan.copy(alpha = 0.4f))
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(Icons.Default.CompareArrows, contentDescription = null, tint = TechCyan, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Compare", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
          }

          // Quiz shortcut
          Surface(
            modifier = Modifier
              .weight(1f)
              .clickable(onClick = onQuizClick)
              .testTag("hero_quiz_button"),
            shape = RoundedCornerShape(12.dp),
            color = MaterialTheme.colorScheme.surface,
            border = androidx.compose.foundation.BorderStroke(1.dp, TechEmerald.copy(alpha = 0.4f))
          ) {
            Row(
              modifier = Modifier.padding(12.dp),
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.Center
            ) {
              Icon(Icons.Default.Quiz, contentDescription = null, tint = TechEmerald, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text("Practice Quiz", style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }
  }
}

@Composable
private fun AlgorithmCard(
  algorithm: AlgorithmItem,
  onClick: () -> Unit
) {
  ElevatedCard(
    modifier = Modifier
      .fillMaxWidth()
      .clickable(onClick = onClick)
      .testTag("algo_card_${algorithm.id}"),
    shape = RoundedCornerShape(16.dp),
    colors = CardDefaults.elevatedCardColors(
      containerColor = MaterialTheme.colorScheme.surface
    ),
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp)
  ) {
    Column(modifier = Modifier.padding(16.dp)) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            modifier = Modifier
              .size(36.dp)
              .clip(CircleShape)
              .background(getCategoryColor(algorithm.category).copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
          ) {
            Icon(
              imageVector = getCategoryIcon(algorithm.category),
              contentDescription = null,
              tint = getCategoryColor(algorithm.category),
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Text(
              text = algorithm.name,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              color = MaterialTheme.colorScheme.onSurface
            )
            Text(
              text = algorithm.category.displayName,
              style = MaterialTheme.typography.labelSmall,
              color = getCategoryColor(algorithm.category),
              fontWeight = FontWeight.SemiBold
            )
          }
        }

        Icon(
          imageVector = Icons.Default.ArrowForward,
          contentDescription = "Open",
          tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
          modifier = Modifier.size(18.dp)
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      Text(
        text = algorithm.description,
        style = MaterialTheme.typography.bodySmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 2
      )

      Spacer(modifier = Modifier.height(12.dp))

      // Badges
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
      ) {
        BadgeTag(label = "Avg: ${algorithm.timeAverage}", color = TechCyan)
        BadgeTag(label = "Worst: ${algorithm.timeWorst}", color = TechRose)
        BadgeTag(label = "Space: ${algorithm.spaceComplexity}", color = TechEmerald)
      }
    }
  }
}

@Composable
private fun BadgeTag(label: String, color: Color) {
  Surface(
    shape = RoundedCornerShape(6.dp),
    color = color.copy(alpha = 0.1f),
    border = androidx.compose.foundation.BorderStroke(0.5.dp, color.copy(alpha = 0.3f))
  ) {
    Text(
      text = label,
      fontFamily = FontFamily.Monospace,
      fontSize = 10.sp,
      fontWeight = FontWeight.Medium,
      color = color,
      modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
    )
  }
}

private fun getCategoryIcon(category: AlgorithmCategory): ImageVector = when (category) {
  AlgorithmCategory.SORTING -> Icons.Default.Sort
  AlgorithmCategory.SEARCHING -> Icons.Default.Search
  AlgorithmCategory.DATA_STRUCTURES -> Icons.Default.DataArray
  AlgorithmCategory.TREES -> Icons.Default.AccountTree
  AlgorithmCategory.GRAPHS -> Icons.Default.Hub
}

private fun getCategoryColor(category: AlgorithmCategory): Color = when (category) {
  AlgorithmCategory.SORTING -> TechIndigo
  AlgorithmCategory.SEARCHING -> TechCyan
  AlgorithmCategory.DATA_STRUCTURES -> TechEmerald
  AlgorithmCategory.TREES -> TechRose
  AlgorithmCategory.GRAPHS -> Color(0xFFF59E0B)
}
