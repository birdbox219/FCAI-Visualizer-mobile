package com.example.model

enum class AlgorithmCategory(val displayName: String, val iconName: String) {
  SORTING("Sorting", "sort"),
  SEARCHING("Searching", "search"),
  DATA_STRUCTURES("Data Structures", "data_array"),
  TREES("Trees", "account_tree"),
  GRAPHS("Graphs", "hub")
}

data class AlgorithmItem(
  val id: String,
  val name: String,
  val category: AlgorithmCategory,
  val description: String,
  val timeBest: String,
  val timeAverage: String,
  val timeWorst: String,
  val spaceComplexity: String,
  val codeSnippet: String
)

data class AlgorithmStep(
  val stepIndex: Int,
  val description: String,
  val codeLine: Int = -1,
  val arrayData: List<Int> = emptyList(),
  val comparingIndices: Set<Int> = emptySet(),
  val swappingIndices: Set<Int> = emptySet(),
  val sortedIndices: Set<Int> = emptySet(),
  val pivotIndex: Int = -1,
  val highlightIndex: Int = -1,
  val extraLabel: String = ""
)

data class QuizQuestion(
  val id: Int,
  val category: AlgorithmCategory,
  val question: String,
  val options: List<String>,
  val correctIndex: Int,
  val explanation: String
)
