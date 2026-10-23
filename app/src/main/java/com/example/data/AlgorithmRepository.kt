package com.example.data

import com.example.model.AlgorithmCategory
import com.example.model.AlgorithmItem
import com.example.model.QuizQuestion

object AlgorithmRepository {

  val algorithms = listOf(
    AlgorithmItem(
      id = "bubble_sort",
      name = "Bubble Sort",
      category = AlgorithmCategory.SORTING,
      description = "Repeatedly steps through the list, compares adjacent elements and swaps them if they are in the wrong order.",
      timeBest = "O(n)",
      timeAverage = "O(n²)",
      timeWorst = "O(n²)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun bubbleSort(arr: IntArray) {
          val n = arr.size
          for (i in 0 until n - 1) {
            for (j in 0 until n - i - 1) {
              if (arr[j] > arr[j + 1]) {
                val temp = arr[j]
                arr[j] = arr[j + 1]
                arr[j + 1] = temp
              }
            }
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "selection_sort",
      name = "Selection Sort",
      category = AlgorithmCategory.SORTING,
      description = "Divides the input list into a sorted and an unsorted region, repeatedly selecting the smallest element from the unsorted region.",
      timeBest = "O(n²)",
      timeAverage = "O(n²)",
      timeWorst = "O(n²)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun selectionSort(arr: IntArray) {
          for (i in 0 until arr.size - 1) {
            var minIdx = i
            for (j in i + 1 until arr.size) {
              if (arr[j] < arr[minIdx]) minIdx = j
            }
            val temp = arr[minIdx]
            arr[minIdx] = arr[i]
            arr[i] = temp
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "insertion_sort",
      name = "Insertion Sort",
      category = AlgorithmCategory.SORTING,
      description = "Builds the final sorted array one item at a time by repeatedly taking the next item and inserting it into the sorted portion.",
      timeBest = "O(n)",
      timeAverage = "O(n²)",
      timeWorst = "O(n²)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun insertionSort(arr: IntArray) {
          for (i in 1 until arr.size) {
            val key = arr[i]
            var j = i - 1
            while (j >= 0 && arr[j] > key) {
              arr[j + 1] = arr[j]
              j--
            }
            arr[j + 1] = key
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "quick_sort",
      name = "Quick Sort",
      category = AlgorithmCategory.SORTING,
      description = "Divides the array around a pivot element into smaller and larger subarrays, recursively sorting each partition.",
      timeBest = "O(n log n)",
      timeAverage = "O(n log n)",
      timeWorst = "O(n²)",
      spaceComplexity = "O(log n)",
      codeSnippet = """
        fun quickSort(arr: IntArray, low: Int, high: Int) {
          if (low < high) {
            val pi = partition(arr, low, high)
            quickSort(arr, low, pi - 1)
            quickSort(arr, pi + 1, high)
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "merge_sort",
      name = "Merge Sort",
      category = AlgorithmCategory.SORTING,
      description = "Divides the array into two halves, recursively sorts them, and then merges the two sorted halves into one.",
      timeBest = "O(n log n)",
      timeAverage = "O(n log n)",
      timeWorst = "O(n log n)",
      spaceComplexity = "O(n)",
      codeSnippet = """
        fun mergeSort(arr: IntArray, l: Int, r: Int) {
          if (l < r) {
            val m = l + (r - l) / 2
            mergeSort(arr, l, m)
            mergeSort(arr, m + 1, r)
            merge(arr, l, m, r)
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "heap_sort",
      name = "Heap Sort",
      category = AlgorithmCategory.SORTING,
      description = "Builds a max-heap from the array, then repeatedly swaps the root element with the last element and heapifies the root.",
      timeBest = "O(n log n)",
      timeAverage = "O(n log n)",
      timeWorst = "O(n log n)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun heapSort(arr: IntArray) {
          val n = arr.size
          for (i in n / 2 - 1 downTo 0) heapify(arr, n, i)
          for (i in n - 1 downTo 1) {
            swap(arr, 0, i)
            heapify(arr, i, 0)
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "linear_search",
      name = "Linear Search",
      category = AlgorithmCategory.SEARCHING,
      description = "Sequentially checks each element of the list until a match is found or the whole list has been searched.",
      timeBest = "O(1)",
      timeAverage = "O(n)",
      timeWorst = "O(n)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun linearSearch(arr: IntArray, target: Int): Int {
          for (i in arr.indices) {
            if (arr[i] == target) return i
          }
          return -1
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "binary_search",
      name = "Binary Search",
      category = AlgorithmCategory.SEARCHING,
      description = "Efficiently searches a sorted array by repeatedly dividing the search interval in half.",
      timeBest = "O(1)",
      timeAverage = "O(log n)",
      timeWorst = "O(log n)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun binarySearch(arr: IntArray, target: Int): Int {
          var low = 0
          var high = arr.size - 1
          while (low <= high) {
            val mid = low + (high - low) / 2
            if (arr[mid] == target) return mid
            if (arr[mid] < target) low = mid + 1
            else high = mid - 1
          }
          return -1
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "jump_search",
      name = "Jump Search",
      category = AlgorithmCategory.SEARCHING,
      description = "Searches a sorted array by jumping ahead by fixed steps (sqrt(n)), then performing linear search within the block.",
      timeBest = "O(1)",
      timeAverage = "O(√n)",
      timeWorst = "O(√n)",
      spaceComplexity = "O(1)",
      codeSnippet = """
        fun jumpSearch(arr: IntArray, target: Int): Int {
          val n = arr.size
          var step = Math.sqrt(n.toDouble()).toInt()
          var prev = 0
          while (arr[Math.min(step, n) - 1] < target) {
            prev = step
            step += Math.sqrt(n.toDouble()).toInt()
            if (prev >= n) return -1
          }
          for (i in prev until Math.min(step, n)) {
            if (arr[i] == target) return i
          }
          return -1
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "stack_ds",
      name = "Stack (LIFO)",
      category = AlgorithmCategory.DATA_STRUCTURES,
      description = "A Last-In, First-Out collection supporting push to add to the top, pop to remove from the top, and peek.",
      timeBest = "O(1)",
      timeAverage = "O(1)",
      timeWorst = "O(1)",
      spaceComplexity = "O(n)",
      codeSnippet = """
        class Stack<T> {
          private val items = mutableListOf<T>()
          fun push(item: T) = items.add(item)
          fun pop(): T? = if (items.isNotEmpty()) items.removeAt(items.size - 1) else null
          fun peek(): T? = items.lastOrNull()
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "queue_ds",
      name = "Queue (FIFO)",
      category = AlgorithmCategory.DATA_STRUCTURES,
      description = "A First-In, First-Out collection where elements are added at the rear and removed from the front.",
      timeBest = "O(1)",
      timeAverage = "O(1)",
      timeWorst = "O(1)",
      spaceComplexity = "O(n)",
      codeSnippet = """
        class Queue<T> {
          private val items = ArrayDeque<T>()
          fun enqueue(item: T) = items.addLast(item)
          fun dequeue(): T? = items.removeFirstOrNull()
          fun peek(): T? = items.firstOrNull()
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "bst_tree",
      name = "Binary Search Tree",
      category = AlgorithmCategory.TREES,
      description = "A rooted binary tree with nodes arranged such that each left child is smaller and each right child is greater.",
      timeBest = "O(log n)",
      timeAverage = "O(log n)",
      timeWorst = "O(n)",
      spaceComplexity = "O(n)",
      codeSnippet = """
        class Node(var key: Int, var left: Node? = null, var right: Node? = null)
        fun insert(root: Node?, key: Int): Node {
          if (root == null) return Node(key)
          if (key < root.key) root.left = insert(root.left, key)
          else if (key > root.key) root.right = insert(root.right, key)
          return root
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "avl_tree",
      name = "AVL Tree",
      category = AlgorithmCategory.TREES,
      description = "A self-balancing binary search tree where the difference between heights of left and right subtrees cannot exceed one.",
      timeBest = "O(log n)",
      timeAverage = "O(log n)",
      timeWorst = "O(log n)",
      spaceComplexity = "O(n)",
      codeSnippet = """
        // Balances tree via rotations: LL, RR, LR, RL
        fun rotateRight(y: Node): Node {
          val x = y.left!!
          val T2 = x.right
          x.right = y
          y.left = T2
          updateHeight(y)
          updateHeight(x)
          return x
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "bfs_graph",
      name = "Breadth-First Search",
      category = AlgorithmCategory.GRAPHS,
      description = "Explores all vertices at the current depth before moving on to vertices at the next depth level using a queue.",
      timeBest = "O(V + E)",
      timeAverage = "O(V + E)",
      timeWorst = "O(V + E)",
      spaceComplexity = "O(V)",
      codeSnippet = """
        fun bfs(graph: Map<Int, List<Int>>, start: Int) {
          val visited = mutableSetOf<Int>()
          val queue = ArrayDeque<Int>()
          queue.add(start)
          visited.add(start)
          while (queue.isNotEmpty()) {
            val u = queue.removeFirst()
            for (v in graph[u] ?: emptyList()) {
              if (v !in visited) {
                visited.add(v)
                queue.add(v)
              }
            }
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "dfs_graph",
      name = "Depth-First Search",
      category = AlgorithmCategory.GRAPHS,
      description = "Explores as deep as possible along each branch before backtracking, utilizing recursion or a stack.",
      timeBest = "O(V + E)",
      timeAverage = "O(V + E)",
      timeWorst = "O(V + E)",
      spaceComplexity = "O(V)",
      codeSnippet = """
        fun dfs(graph: Map<Int, List<Int>>, u: Int, visited: MutableSet<Int>) {
          visited.add(u)
          for (v in graph[u] ?: emptyList()) {
            if (v !in visited) {
              dfs(graph, v, visited)
            }
          }
        }
      """.trimIndent()
    ),
    AlgorithmItem(
      id = "dijkstra_graph",
      name = "Dijkstra's Algorithm",
      category = AlgorithmCategory.GRAPHS,
      description = "Finds the shortest paths between nodes in a graph with non-negative edge weights using a priority queue.",
      timeBest = "O((V + E) log V)",
      timeAverage = "O((V + E) log V)",
      timeWorst = "O((V + E) log V)",
      spaceComplexity = "O(V)",
      codeSnippet = """
        fun dijkstra(graph: List<List<Edge>>, start: Int): IntArray {
          val dist = IntArray(graph.size) { Int.MAX_VALUE }
          dist[start] = 0
          val pq = PriorityQueue<Pair<Int, Int>>(compareBy { it.second })
          pq.add(start to 0)
          while (pq.isNotEmpty()) {
            val (u, d) = pq.poll()
            if (d > dist[u]) continue
            for (edge in graph[u]) {
              if (dist[u] + edge.weight < dist[edge.to]) {
                dist[edge.to] = dist[u] + edge.weight
                pq.add(edge.to to dist[edge.to])
              }
            }
          }
          return dist
        }
      """.trimIndent()
    )
  )

  val quizQuestions = listOf(
    QuizQuestion(
      id = 1,
      category = AlgorithmCategory.SORTING,
      question = "What is the worst-case time complexity of Quick Sort?",
      options = listOf("O(n log n)", "O(n²)", "O(n)", "O(log n)"),
      correctIndex = 1,
      explanation = "Quick Sort degrades to O(n²) when the pivot chosen is consistently the smallest or largest element (such as an already sorted array with naive pivot selection)."
    ),
    QuizQuestion(
      id = 2,
      category = AlgorithmCategory.SORTING,
      question = "Which sorting algorithm is guaranteed to have O(n log n) worst-case time complexity and is stable?",
      options = listOf("Quick Sort", "Heap Sort", "Merge Sort", "Selection Sort"),
      correctIndex = 2,
      explanation = "Merge Sort always runs in O(n log n) time in all cases and preserves the relative order of duplicate keys (stable), unlike standard Quick Sort or Heap Sort."
    ),
    QuizQuestion(
      id = 3,
      category = AlgorithmCategory.SEARCHING,
      question = "What prerequisite is required before performing Binary Search on an array?",
      options = listOf("Elements must be distinct", "Array must be sorted", "Size must be a power of 2", "Elements must be positive"),
      correctIndex = 1,
      explanation = "Binary Search relies on sorted order to determine whether the target lies in the left or right partition at each step."
    ),
    QuizQuestion(
      id = 4,
      category = AlgorithmCategory.DATA_STRUCTURES,
      question = "Which data structure follows the Last-In, First-Out (LIFO) principle?",
      options = listOf("Queue", "Stack", "Binary Heap", "Linked List"),
      correctIndex = 1,
      explanation = "A Stack processes items in LIFO order: the most recently added item is the first one removed."
    ),
    QuizQuestion(
      id = 5,
      category = AlgorithmCategory.TREES,
      question = "In an AVL tree, what is the maximum allowed difference between the heights of the left and right subtrees?",
      options = listOf("0", "1", "2", "log n"),
      correctIndex = 1,
      explanation = "The balance factor of any node in an AVL tree must be -1, 0, or 1. Any imbalance trigger tree rotations."
    ),
    QuizQuestion(
      id = 6,
      category = AlgorithmCategory.GRAPHS,
      question = "Which data structure is typically used to implement Breadth-First Search (BFS)?",
      options = listOf("Stack", "Priority Queue", "Queue", "Binary Tree"),
      correctIndex = 2,
      explanation = "BFS visits vertices layer by layer in FIFO order, making a Queue the standard data structure."
    ),
    QuizQuestion(
      id = 7,
      category = AlgorithmCategory.GRAPHS,
      question = "Dijkstra's shortest path algorithm works correctly on graphs containing:",
      options = listOf("Negative weight cycles", "Only non-negative edge weights", "Negative edges without cycles", "Directed acyclic graphs only"),
      correctIndex = 1,
      explanation = "Dijkstra's greedy assumption requires non-negative edge weights; otherwise the Bellman-Ford algorithm must be used."
    ),
    QuizQuestion(
      id = 8,
      category = AlgorithmCategory.SORTING,
      question = "What is the best-case time complexity of Bubble Sort with early termination?",
      options = listOf("O(1)", "O(n)", "O(n log n)", "O(n²)"),
      correctIndex = 1,
      explanation = "With a swapped flag, if no swaps occur during the first pass on an already sorted array, Bubble Sort terminates in O(n) comparisons."
    )
  )

  fun getAlgorithmById(id: String): AlgorithmItem? =
    algorithms.find { it.id == id }
}
