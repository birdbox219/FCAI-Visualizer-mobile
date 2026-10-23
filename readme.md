# FCAI Visualizer (AlgoVs) - Android

Educational Algorithm & Data Structure Visualizer built with Kotlin and Jetpack Compose.

## Features

- **Algorithm Categories**:
  - **Sorting**: Bubble Sort, Selection Sort, Insertion Sort, Quick Sort, Merge Sort, Heap Sort.
  - **Searching**: Linear Search, Binary Search, Jump Search.
  - **Data Structures**: Stack (LIFO), Queue (FIFO), Arrays.
  - **Trees**: Binary Search Tree (BST), AVL Tree with rotations.
  - **Graphs**: Breadth-First Search (BFS), Depth-First Search (DFS), Dijkstra's Shortest Path.
- **Interactive Visualizer Player**:
  - Step-by-step playback with forward, backward, play/pause, seek slider, and speed control (0.5x, 1x, 2x, 4x).
  - Animated Bar Chart for sorting and searching.
  - Dynamic Node/Graph renderer for trees, graphs, and collections.
  - Live narration card describing algorithm actions in plain English.
- **Complexity & Code Analysis**:
  - Best, Average, and Worst case time complexity badges.
  - Space complexity analysis.
  - Expandable Kotlin code snippets.
- **Algorithm Race / Compare Mode**:
  - Compare two sorting algorithms side-by-side on the exact same randomized input dataset.
  - Live step counter and operation metrics.
- **Practice Mode (Quiz)**:
  - Interactive multiple choice questions covering time complexity, data structures, trees, and graphs.
  - Immediate visual feedback and detailed explanations.

## Tech Stack

- **Platform**: Android (minSdk 24, targetSdk 36)
- **UI Framework**: Jetpack Compose with Material Design 3 (M3)
- **Language**: Kotlin 2.2.x
- **Build System**: Gradle 9.3.1 with Kotlin DSL (.gradle.kts)
