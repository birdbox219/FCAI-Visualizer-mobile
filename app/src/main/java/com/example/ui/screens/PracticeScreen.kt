package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import com.example.data.AlgorithmRepository
import com.example.model.QuizQuestion
import com.example.ui.theme.TechEmerald
import com.example.ui.theme.TechIndigo
import com.example.ui.theme.TechRose

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PracticeScreen(
  onNavigateBack: () -> Unit,
  modifier: Modifier = Modifier
) {
  BackHandler(onBack = onNavigateBack)

  val questions = remember { AlgorithmRepository.quizQuestions }
  var currentQuestionIdx by remember { mutableIntStateOf(0) }
  var selectedOptionIdx by remember { mutableStateOf<Int?>(null) }
  var isAnswerSubmitted by remember { mutableStateOf(false) }
  var score by remember { mutableIntStateOf(0) }

  val currentQuestion = questions.getOrNull(currentQuestionIdx)

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Practice Quiz", fontWeight = FontWeight.Bold) },
        navigationIcon = {
          IconButton(
            onClick = onNavigateBack,
            modifier = Modifier.testTag("practice_back_button")
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
      // Progress Bar & Score Banner
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "Question ${currentQuestionIdx + 1} of ${questions.size}",
          style = MaterialTheme.typography.titleSmall,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.primary
        )

        Surface(
          shape = RoundedCornerShape(12.dp),
          color = TechEmerald.copy(alpha = 0.15f),
          border = androidx.compose.foundation.BorderStroke(1.dp, TechEmerald.copy(alpha = 0.3f))
        ) {
          Text(
            text = "Score: $score / ${questions.size}",
            color = TechEmerald,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }

      LinearProgressIndicator(
        progress = { (currentQuestionIdx + 1).toFloat() / questions.size },
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(4.dp)),
        color = TechIndigo
      )

      if (currentQuestion != null) {
        // Question Card
        Card(
          shape = RoundedCornerShape(16.dp),
          colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
          border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("quiz_question_card")
        ) {
          Column(modifier = Modifier.padding(18.dp)) {
            Text(
              text = currentQuestion.category.displayName.uppercase(),
              style = MaterialTheme.typography.labelSmall,
              fontWeight = FontWeight.Bold,
              color = TechIndigo
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
              text = currentQuestion.question,
              style = MaterialTheme.typography.titleMedium,
              fontWeight = FontWeight.Bold,
              lineHeight = 24.sp
            )

            Spacer(modifier = Modifier.height(18.dp))

            // Options
            currentQuestion.options.forEachIndexed { optIdx, optionText ->
              val isSelected = selectedOptionIdx == optIdx
              val isCorrect = optIdx == currentQuestion.correctIndex

              val borderColor = when {
                !isAnswerSubmitted && isSelected -> TechIndigo
                isAnswerSubmitted && isCorrect -> TechEmerald
                isAnswerSubmitted && isSelected && !isCorrect -> TechRose
                else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
              }

              val backgroundColor = when {
                !isAnswerSubmitted && isSelected -> TechIndigo.copy(alpha = 0.08f)
                isAnswerSubmitted && isCorrect -> TechEmerald.copy(alpha = 0.12f)
                isAnswerSubmitted && isSelected && !isCorrect -> TechRose.copy(alpha = 0.12f)
                else -> Color.Transparent
              }

              Surface(
                shape = RoundedCornerShape(12.dp),
                color = backgroundColor,
                border = androidx.compose.foundation.BorderStroke(1.5.dp, borderColor),
                modifier = Modifier
                  .fillMaxWidth()
                  .padding(vertical = 5.dp)
                  .clickable(enabled = !isAnswerSubmitted) {
                    selectedOptionIdx = optIdx
                    isAnswerSubmitted = true
                    if (optIdx == currentQuestion.correctIndex) {
                      score++
                    }
                  }
                  .testTag("quiz_option_$optIdx")
              ) {
                Row(
                  modifier = Modifier.padding(14.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Box(
                    modifier = Modifier
                      .size(28.dp)
                      .clip(CircleShape)
                      .background(
                        if (isAnswerSubmitted && isCorrect) TechEmerald
                        else if (isAnswerSubmitted && isSelected) TechRose
                        else MaterialTheme.colorScheme.surfaceVariant
                      ),
                    contentAlignment = Alignment.Center
                  ) {
                    if (isAnswerSubmitted && isCorrect) {
                      Icon(Icons.Default.Check, contentDescription = "Correct", tint = Color.White, modifier = Modifier.size(16.dp))
                    } else if (isAnswerSubmitted && isSelected) {
                      Icon(Icons.Default.Close, contentDescription = "Incorrect", tint = Color.White, modifier = Modifier.size(16.dp))
                    } else {
                      Text(
                        text = "${'A' + optIdx}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold
                      )
                    }
                  }

                  Spacer(modifier = Modifier.width(12.dp))

                  Text(
                    text = optionText,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                  )
                }
              }
            }
          }
        }

        // Explanation Card
        AnimatedVisibility(visible = isAnswerSubmitted) {
          Card(
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(
              containerColor = if (selectedOptionIdx == currentQuestion.correctIndex)
                TechEmerald.copy(alpha = 0.1f)
              else
                TechRose.copy(alpha = 0.1f)
            ),
            modifier = Modifier.fillMaxWidth().testTag("quiz_explanation_card")
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Text(
                text = if (selectedOptionIdx == currentQuestion.correctIndex) "✓ Correct!" else "✗ Explanation",
                fontWeight = FontWeight.Bold,
                color = if (selectedOptionIdx == currentQuestion.correctIndex) TechEmerald else TechRose,
                style = MaterialTheme.typography.titleSmall
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = currentQuestion.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface
              )
            }
          }
        }

        // Bottom Navigation Buttons
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          OutlinedButton(
            onClick = {
              currentQuestionIdx = 0
              selectedOptionIdx = null
              isAnswerSubmitted = false
              score = 0
            },
            modifier = Modifier.testTag("quiz_restart_button")
          ) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("Restart")
          }

          if (isAnswerSubmitted) {
            Button(
              onClick = {
                if (currentQuestionIdx < questions.size - 1) {
                  currentQuestionIdx++
                  selectedOptionIdx = null
                  isAnswerSubmitted = false
                }
              },
              enabled = currentQuestionIdx < questions.size - 1,
              modifier = Modifier.testTag("quiz_next_button")
            ) {
              Text("Next Question")
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
