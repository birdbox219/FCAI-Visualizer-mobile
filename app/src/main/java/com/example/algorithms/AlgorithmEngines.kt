package com.example.algorithms

import com.example.model.AlgorithmStep
import kotlin.math.min
import kotlin.math.sqrt

object AlgorithmEngines {

  fun generateStepsForAlgorithm(algoId: String, initialArray: List<Int>, searchTarget: Int = 42): List<AlgorithmStep> {
    return when (algoId) {
      "bubble_sort" -> generateBubbleSort(initialArray)
      "selection_sort" -> generateSelectionSort(initialArray)
      "insertion_sort" -> generateInsertionSort(initialArray)
      "quick_sort" -> generateQuickSort(initialArray)
      "merge_sort" -> generateMergeSort(initialArray)
      "heap_sort" -> generateHeapSort(initialArray)
      "linear_search" -> generateLinearSearch(initialArray, searchTarget)
      "binary_search" -> generateBinarySearch(initialArray.sorted(), searchTarget)
      "jump_search" -> generateJumpSearch(initialArray.sorted(), searchTarget)
      "stack_ds" -> generateStackDemo()
      "queue_ds" -> generateQueueDemo()
      "bst_tree" -> generateBSTDemo()
      "avl_tree" -> generateAVLDemo()
      "bfs_graph" -> generateBFSDemo()
      "dfs_graph" -> generateDFSDemo()
      "dijkstra_graph" -> generateDijkstraDemo()
      else -> generateBubbleSort(initialArray)
    }
  }

  // --- SORTING ---
  private fun generateBubbleSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    val sorted = mutableSetOf<Int>()
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Bubble Sort on array of size ${arr.size}.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )

    val n = current.size
    for (i in 0 until n - 1) {
      var swapped = false
      for (j in 0 until n - i - 1) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Comparing arr[$j] (${current[j]}) and arr[${j + 1}] (${current[j + 1]}).",
            arrayData = current.toList(),
            comparingIndices = setOf(j, j + 1),
            sortedIndices = sorted.toSet()
          )
        )

        if (current[j] > current[j + 1]) {
          val temp = current[j]
          current[j] = current[j + 1]
          current[j + 1] = temp
          swapped = true

          steps.add(
            AlgorithmStep(
              stepIndex = stepCount++,
              description = "Swapped ${current[j + 1]} and ${current[j]}.",
              arrayData = current.toList(),
              swappingIndices = setOf(j, j + 1),
              sortedIndices = sorted.toSet()
            )
          )
        }
      }
      sorted.add(n - i - 1)
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Element at index ${n - i - 1} (${current[n - i - 1]}) is now placed in sorted position.",
          arrayData = current.toList(),
          sortedIndices = sorted.toSet()
        )
      )
      if (!swapped) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "No swaps occurred in this pass. Early termination triggered!",
            arrayData = current.toList(),
            sortedIndices = current.indices.toSet()
          )
        )
        break
      }
    }
    sorted.addAll(current.indices)
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Bubble Sort completed! All elements sorted.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )
    return steps
  }

  private fun generateSelectionSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    val sorted = mutableSetOf<Int>()
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Selection Sort.",
        arrayData = current.toList()
      )
    )

    for (i in 0 until current.size - 1) {
      var minIdx = i
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Assuming current minimum is at index $i (value ${current[i]}).",
          arrayData = current.toList(),
          highlightIndex = minIdx,
          sortedIndices = sorted.toSet()
        )
      )

      for (j in i + 1 until current.size) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Comparing candidate arr[$j] (${current[j]}) with minimum arr[$minIdx] (${current[minIdx]}).",
            arrayData = current.toList(),
            comparingIndices = setOf(j, minIdx),
            sortedIndices = sorted.toSet()
          )
        )
        if (current[j] < current[minIdx]) {
          minIdx = j
          steps.add(
            AlgorithmStep(
              stepIndex = stepCount++,
              description = "New minimum found at index $minIdx (value ${current[minIdx]}).",
              arrayData = current.toList(),
              highlightIndex = minIdx,
              sortedIndices = sorted.toSet()
            )
          )
        }
      }

      if (minIdx != i) {
        val temp = current[i]
        current[i] = current[minIdx]
        current[minIdx] = temp
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Swapping minimum ${current[i]} into sorted index $i.",
            arrayData = current.toList(),
            swappingIndices = setOf(i, minIdx),
            sortedIndices = sorted.toSet()
          )
        )
      }
      sorted.add(i)
    }
    sorted.addAll(current.indices)
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Selection Sort finished successfully.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )
    return steps
  }

  private fun generateInsertionSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    val sorted = mutableSetOf<Int>()
    sorted.add(0)
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Insertion Sort. Initial element at index 0 considered sorted.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )

    for (i in 1 until current.size) {
      val key = current[i]
      var j = i - 1
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Current key to insert: $key from index $i.",
          arrayData = current.toList(),
          highlightIndex = i,
          sortedIndices = sorted.toSet()
        )
      )

      while (j >= 0 && current[j] > key) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Comparing key $key with arr[$j] (${current[j]}). Since ${current[j]} > $key, shifting right.",
            arrayData = current.toList(),
            comparingIndices = setOf(j, j + 1),
            sortedIndices = sorted.toSet()
          )
        )
        current[j + 1] = current[j]
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Shifted ${current[j + 1]} to index ${j + 1}.",
            arrayData = current.toList(),
            swappingIndices = setOf(j, j + 1),
            sortedIndices = sorted.toSet()
          )
        )
        j--
      }
      current[j + 1] = key
      sorted.add(i)
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Placed key $key at index ${j + 1}.",
          arrayData = current.toList(),
          highlightIndex = j + 1,
          sortedIndices = sorted.toSet()
        )
      )
    }

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Insertion Sort completed.",
        arrayData = current.toList(),
        sortedIndices = current.indices.toSet()
      )
    )
    return steps
  }

  private fun generateQuickSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    val sorted = mutableSetOf<Int>()
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Quick Sort.",
        arrayData = current.toList()
      )
    )

    fun partition(low: Int, high: Int): Int {
      val pivot = current[high]
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Selected pivot ${current[high]} at index $high.",
          arrayData = current.toList(),
          pivotIndex = high,
          sortedIndices = sorted.toSet()
        )
      )

      var i = low - 1
      for (j in low until high) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Comparing arr[$j] (${current[j]}) with pivot $pivot.",
            arrayData = current.toList(),
            comparingIndices = setOf(j, high),
            pivotIndex = high,
            sortedIndices = sorted.toSet()
          )
        )
        if (current[j] <= pivot) {
          i++
          val temp = current[i]
          current[i] = current[j]
          current[j] = temp
          steps.add(
            AlgorithmStep(
              stepIndex = stepCount++,
              description = "Swapped arr[$i] (${current[i]}) and arr[$j] (${current[j]}).",
              arrayData = current.toList(),
              swappingIndices = setOf(i, j),
              pivotIndex = high,
              sortedIndices = sorted.toSet()
            )
          )
        }
      }
      val temp = current[i + 1]
      current[i + 1] = current[high]
      current[high] = temp
      val pi = i + 1
      sorted.add(pi)
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Placed pivot $pivot at partitioned position $pi.",
          arrayData = current.toList(),
          swappingIndices = setOf(pi, high),
          sortedIndices = sorted.toSet()
        )
      )
      return pi
    }

    fun qsort(low: Int, high: Int) {
      if (low < high) {
        val pi = partition(low, high)
        qsort(low, pi - 1)
        qsort(pi + 1, high)
      } else if (low == high) {
        sorted.add(low)
      }
    }

    qsort(0, current.size - 1)
    sorted.addAll(current.indices)
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Quick Sort finished.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )
    return steps
  }

  private fun generateMergeSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Merge Sort (Divide & Conquer).",
        arrayData = current.toList()
      )
    )

    fun merge(l: Int, m: Int, r: Int) {
      val left = current.subList(l, m + 1).toList()
      val right = current.subList(m + 1, r + 1).toList()
      var i = 0
      var j = 0
      var k = l

      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Merging subarrays: left [$l..$m] and right [${m + 1}..$r].",
          arrayData = current.toList(),
          comparingIndices = (l..r).toSet()
        )
      )

      while (i < left.size && j < right.size) {
        if (left[i] <= right[j]) {
          current[k] = left[i]
          i++
        } else {
          current[k] = right[j]
          j++
        }
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Inserted ${current[k]} into merged array at index $k.",
            arrayData = current.toList(),
            highlightIndex = k
          )
        )
        k++
      }
      while (i < left.size) {
        current[k] = left[i]
        i++
        k++
      }
      while (j < right.size) {
        current[k] = right[j]
        j++
        k++
      }
    }

    fun msort(l: Int, r: Int) {
      if (l < r) {
        val m = l + (r - l) / 2
        msort(l, m)
        msort(m + 1, r)
        merge(l, m, r)
      }
    }

    msort(0, current.size - 1)
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Merge Sort complete.",
        arrayData = current.toList(),
        sortedIndices = current.indices.toSet()
      )
    )
    return steps
  }

  private fun generateHeapSort(arr: List<Int>): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    val current = arr.toMutableList()
    val sorted = mutableSetOf<Int>()
    var stepCount = 0

    fun heapify(n: Int, i: Int) {
      var largest = i
      val l = 2 * i + 1
      val r = 2 * i + 2
      if (l < n && current[l] > current[largest]) largest = l
      if (r < n && current[r] > current[largest]) largest = r
      if (largest != i) {
        val swap = current[i]
        current[i] = current[largest]
        current[largest] = swap
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Heapify: Swapped parent arr[$i] with larger child arr[$largest].",
            arrayData = current.toList(),
            swappingIndices = setOf(i, largest),
            sortedIndices = sorted.toSet()
          )
        )
        heapify(n, largest)
      }
    }

    val n = current.size
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Building Max Heap from array.",
        arrayData = current.toList()
      )
    )
    for (i in n / 2 - 1 downTo 0) heapify(n, i)

    for (i in n - 1 downTo 1) {
      val temp = current[0]
      current[0] = current[i]
      current[i] = temp
      sorted.add(i)
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Max root $temp moved to end of heap (sorted index $i).",
          arrayData = current.toList(),
          swappingIndices = setOf(0, i),
          sortedIndices = sorted.toSet()
        )
      )
      heapify(i, 0)
    }
    sorted.addAll(current.indices)
    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Heap Sort completed.",
        arrayData = current.toList(),
        sortedIndices = sorted.toSet()
      )
    )
    return steps
  }

  // --- SEARCHING ---
  private fun generateLinearSearch(arr: List<Int>, target: Int): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    var stepCount = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Linear Search for target value $target.",
        arrayData = arr
      )
    )

    var foundIdx = -1
    for (i in arr.indices) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Checking index $i: arr[$i] = ${arr[i]}. Does it equal $target?",
          arrayData = arr,
          comparingIndices = setOf(i),
          highlightIndex = i
        )
      )
      if (arr[i] == target) {
        foundIdx = i
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Target $target FOUND at index $i!",
            arrayData = arr,
            sortedIndices = setOf(i),
            highlightIndex = i
          )
        )
        break
      }
    }
    if (foundIdx == -1) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Target $target not found in the array.",
          arrayData = arr
        )
      )
    }
    return steps
  }

  private fun generateBinarySearch(sortedArr: List<Int>, target: Int): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    var stepCount = 0
    var low = 0
    var high = sortedArr.size - 1

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Binary Search for target $target on sorted array [low=0, high=$high].",
        arrayData = sortedArr
      )
    )

    var foundIdx = -1
    while (low <= high) {
      val mid = low + (high - low) / 2
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Window [$low..$high], calculating mid index $mid (value ${sortedArr[mid]}).",
          arrayData = sortedArr,
          comparingIndices = setOf(mid),
          pivotIndex = mid,
          highlightIndex = mid
        )
      )
      if (sortedArr[mid] == target) {
        foundIdx = mid
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Match found! arr[$mid] == $target.",
            arrayData = sortedArr,
            sortedIndices = setOf(mid),
            highlightIndex = mid
          )
        )
        break
      }
      if (sortedArr[mid] < target) {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "${sortedArr[mid]} < $target: Discarding left half. New low = ${mid + 1}.",
            arrayData = sortedArr,
            highlightIndex = mid
          )
        )
        low = mid + 1
      } else {
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "${sortedArr[mid]} > $target: Discarding right half. New high = ${mid - 1}.",
            arrayData = sortedArr,
            highlightIndex = mid
          )
        )
        high = mid - 1
      }
    }
    if (foundIdx == -1) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Search interval exhausted. Target $target is not present.",
          arrayData = sortedArr
        )
      )
    }
    return steps
  }

  private fun generateJumpSearch(sortedArr: List<Int>, target: Int): List<AlgorithmStep> {
    val steps = mutableListOf<AlgorithmStep>()
    var stepCount = 0
    val n = sortedArr.size
    val blockSize = sqrt(n.toDouble()).toInt().coerceAtLeast(1)
    var step = blockSize
    var prev = 0

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Starting Jump Search with block size √n = $blockSize for target $target.",
        arrayData = sortedArr
      )
    )

    while (sortedArr[min(step, n) - 1] < target) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Jumped to index ${min(step, n) - 1} (${sortedArr[min(step, n) - 1]}). Still < $target.",
          arrayData = sortedArr,
          comparingIndices = setOf(min(step, n) - 1)
        )
      )
      prev = step
      step += blockSize
      if (prev >= n) break
    }

    steps.add(
      AlgorithmStep(
        stepIndex = stepCount++,
        description = "Target may be in block between indices $prev and ${min(step, n) - 1}. Performing linear search.",
        arrayData = sortedArr,
        comparingIndices = (prev until min(step, n)).toSet()
      )
    )

    var foundIdx = -1
    for (i in prev until min(step, n)) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Checking index $i (value ${sortedArr[i]}).",
          arrayData = sortedArr,
          highlightIndex = i
        )
      )
      if (sortedArr[i] == target) {
        foundIdx = i
        steps.add(
          AlgorithmStep(
            stepIndex = stepCount++,
            description = "Target $target found at index $i!",
            arrayData = sortedArr,
            sortedIndices = setOf(i)
          )
        )
        break
      }
    }
    if (foundIdx == -1) {
      steps.add(
        AlgorithmStep(
          stepIndex = stepCount++,
          description = "Target $target not found.",
          arrayData = sortedArr
        )
      )
    }
    return steps
  }

  // --- DATA STRUCTURES & TREES ---
  private fun generateStackDemo(): List<AlgorithmStep> {
    return listOf(
      AlgorithmStep(0, "Empty stack initialized. top = -1.", arrayData = emptyList()),
      AlgorithmStep(1, "push(15): Element placed on top.", arrayData = listOf(15), highlightIndex = 0),
      AlgorithmStep(2, "push(32): Element placed on top.", arrayData = listOf(15, 32), highlightIndex = 1),
      AlgorithmStep(3, "push(68): Element placed on top.", arrayData = listOf(15, 32, 68), highlightIndex = 2),
      AlgorithmStep(4, "peek(): Top element is 68. O(1) operation.", arrayData = listOf(15, 32, 68), highlightIndex = 2),
      AlgorithmStep(5, "pop(): Removed top element 68. New top is 32.", arrayData = listOf(15, 32), swappingIndices = setOf(1)),
      AlgorithmStep(6, "push(99): Placed on top.", arrayData = listOf(15, 32, 99), highlightIndex = 2),
      AlgorithmStep(7, "pop(): Removed top element 99.", arrayData = listOf(15, 32), highlightIndex = 1)
    )
  }

  private fun generateQueueDemo(): List<AlgorithmStep> {
    return listOf(
      AlgorithmStep(0, "Empty queue initialized.", arrayData = emptyList()),
      AlgorithmStep(1, "enqueue(10): Added to rear (front=0, rear=0).", arrayData = listOf(10), highlightIndex = 0),
      AlgorithmStep(2, "enqueue(25): Added to rear.", arrayData = listOf(10, 25), highlightIndex = 1),
      AlgorithmStep(3, "enqueue(40): Added to rear.", arrayData = listOf(10, 25, 40), highlightIndex = 2),
      AlgorithmStep(4, "peek(): Front element is 10. O(1).", arrayData = listOf(10, 25, 40), highlightIndex = 0),
      AlgorithmStep(5, "dequeue(): Removed front element 10.", arrayData = listOf(25, 40), swappingIndices = setOf(0)),
      AlgorithmStep(6, "enqueue(55): Added to rear.", arrayData = listOf(25, 40, 55), highlightIndex = 2),
      AlgorithmStep(7, "dequeue(): Removed front element 25.", arrayData = listOf(40, 55), highlightIndex = 0)
    )
  }

  private fun generateBSTDemo(): List<AlgorithmStep> {
    return listOf(
      AlgorithmStep(0, "Empty Binary Search Tree initialized.", arrayData = emptyList()),
      AlgorithmStep(1, "Insert 50: Root node created.", arrayData = listOf(50), highlightIndex = 0, extraLabel = "Root: 50"),
      AlgorithmStep(2, "Insert 30: 30 < 50 -> left child of 50.", arrayData = listOf(50, 30), highlightIndex = 1, extraLabel = "50 -> L:30"),
      AlgorithmStep(3, "Insert 70: 70 > 50 -> right child of 50.", arrayData = listOf(50, 30, 70), highlightIndex = 2, extraLabel = "50 -> R:70"),
      AlgorithmStep(4, "Insert 20: 20 < 50 -> left, 20 < 30 -> left child of 30.", arrayData = listOf(50, 30, 70, 20), highlightIndex = 3, extraLabel = "30 -> L:20"),
      AlgorithmStep(5, "Insert 40: 40 < 50 -> left, 40 > 30 -> right child of 30.", arrayData = listOf(50, 30, 70, 20, 40), highlightIndex = 4, extraLabel = "30 -> R:40"),
      AlgorithmStep(6, "Inorder Traversal (Sorted order): [20, 30, 40, 50, 70].", arrayData = listOf(20, 30, 40, 50, 70), sortedIndices = (0..4).toSet(), extraLabel = "Inorder complete")
    )
  }

  private fun generateAVLDemo(): List<AlgorithmStep> {
    return listOf(
      AlgorithmStep(0, "AVL Tree initialized. Maintains height balance factor ∈ {-1, 0, 1}.", arrayData = emptyList()),
      AlgorithmStep(1, "Insert 10: Root created. BF(10) = 0.", arrayData = listOf(10), highlightIndex = 0),
      AlgorithmStep(2, "Insert 20: Right child of 10. BF(10) = -1.", arrayData = listOf(10, 20), highlightIndex = 1),
      AlgorithmStep(3, "Insert 30: Right-Right imbalance at 10! BF(10) = -2.", arrayData = listOf(10, 20, 30), comparingIndices = setOf(0, 1, 2)),
      AlgorithmStep(4, "Performing Left Rotation on node 10. Node 20 becomes new root.", arrayData = listOf(20, 10, 30), swappingIndices = setOf(0, 1), sortedIndices = setOf(0)),
      AlgorithmStep(5, "Tree re-balanced! Heights: left=1, right=1. BF=0.", arrayData = listOf(20, 10, 30), sortedIndices = (0..2).toSet(), extraLabel = "Balanced Root: 20")
    )
  }

  // --- GRAPHS ---
  private fun generateBFSDemo(): List<AlgorithmStep> {
    val nodes = listOf(0, 1, 2, 3, 4, 5)
    return listOf(
      AlgorithmStep(0, "Graph with 6 vertices: 0-1, 0-2, 1-3, 1-4, 2-5.", arrayData = nodes),
      AlgorithmStep(1, "Start BFS at Vertex 0. Mark 0 visited, enqueue [0].", arrayData = nodes, highlightIndex = 0, sortedIndices = setOf(0)),
      AlgorithmStep(2, "Dequeue 0. Visit adjacent unvisited neighbors: 1, 2. Queue: [1, 2].", arrayData = nodes, comparingIndices = setOf(1, 2), sortedIndices = setOf(0, 1, 2)),
      AlgorithmStep(3, "Dequeue 1. Visit adjacent unvisited neighbors: 3, 4. Queue: [2, 3, 4].", arrayData = nodes, comparingIndices = setOf(3, 4), sortedIndices = setOf(0, 1, 2, 3, 4)),
      AlgorithmStep(4, "Dequeue 2. Visit neighbor 5. Queue: [3, 4, 5].", arrayData = nodes, comparingIndices = setOf(5), sortedIndices = setOf(0, 1, 2, 3, 4, 5)),
      AlgorithmStep(5, "Dequeue 3, 4, 5. All vertices visited in order: 0 -> 1 -> 2 -> 3 -> 4 -> 5.", arrayData = nodes, sortedIndices = (0..5).toSet())
    )
  }

  private fun generateDFSDemo(): List<AlgorithmStep> {
    val nodes = listOf(0, 1, 3, 4, 2, 5)
    return listOf(
      AlgorithmStep(0, "Graph ready for Depth-First Search exploration.", arrayData = nodes),
      AlgorithmStep(1, "Visit Vertex 0. Push to call stack.", arrayData = nodes, highlightIndex = 0, sortedIndices = setOf(0)),
      AlgorithmStep(2, "Deep dive to first neighbor: Vertex 1.", arrayData = nodes, highlightIndex = 1, sortedIndices = setOf(0, 1)),
      AlgorithmStep(3, "Deep dive to Vertex 3. Dead end, backtrack to 1.", arrayData = nodes, highlightIndex = 2, sortedIndices = setOf(0, 1, 2)),
      AlgorithmStep(4, "Deep dive to Vertex 4. Dead end, backtrack to 0.", arrayData = nodes, highlightIndex = 3, sortedIndices = setOf(0, 1, 2, 3)),
      AlgorithmStep(5, "Explore branch from 0 to Vertex 2, then Vertex 5.", arrayData = nodes, highlightIndex = 4, sortedIndices = setOf(0, 1, 2, 3, 4, 5)),
      AlgorithmStep(6, "DFS Traversal complete: [0, 1, 3, 4, 2, 5].", arrayData = nodes, sortedIndices = (0..5).toSet())
    )
  }

  private fun generateDijkstraDemo(): List<AlgorithmStep> {
    val distances = listOf(0, 4, 2, 7, 5)
    return listOf(
      AlgorithmStep(0, "Dijkstra: Source vertex 0. Initial distances: [0, ∞, ∞, ∞, ∞].", arrayData = listOf(0, 99, 99, 99, 99), highlightIndex = 0),
      AlgorithmStep(1, "Relax edges from 0: to 1 (weight 4), to 2 (weight 2). Distances: [0, 4, 2, ∞, ∞].", arrayData = listOf(0, 4, 2, 99, 99), comparingIndices = setOf(1, 2)),
      AlgorithmStep(2, "Extract min unvisited: Vertex 2 (dist=2). Relax neighbor 3 (weight 5): dist=7.", arrayData = listOf(0, 4, 2, 7, 99), highlightIndex = 2),
      AlgorithmStep(3, "Extract min unvisited: Vertex 1 (dist=4). Relax neighbor 4 (weight 1): dist=5.", arrayData = listOf(0, 4, 2, 7, 5), highlightIndex = 1),
      AlgorithmStep(4, "All reachable nodes relaxed. Shortest distances from 0: [0, 4, 2, 7, 5].", arrayData = distances, sortedIndices = (0..4).toSet())
    )
  }
}
