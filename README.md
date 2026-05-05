# 🧩 Maze Solver Visualizer (JavaFX)

An interactive **maze and pathfinding visualizer** built with **Java and JavaFX**, designed to demonstrate how different algorithms explore and solve grid-based problems in real time.

---

## 🚀 Overview

Maze Solver Visualizer is an educational desktop application that allows users to **build, visualize, and analyze pathfinding algorithms**.  
It provides an intuitive interface to create mazes, adjust parameters, and observe how algorithms behave step-by-step.

---

## ✨ Features

### 🧠 Pathfinding Algorithms
- **Breadth-First Search (BFS)** — guarantees shortest path (unweighted)
- **Depth-First Search (DFS)** — explores deeply, not always optimal
- **Dijkstra’s Algorithm** — shortest path using cumulative cost
- **A\*** — optimized search using heuristics (Manhattan distance)

---

### 🧱 Maze Generation
- Random Maze Generator  
- Wilson’s Algorithm (perfect maze generation)

---

### 🎮 Interactive Grid
- Left-click / drag → draw walls  
- Right-click → place or move **start** and **end** nodes  
- Adjustable grid size  

---

### ⚡ Visualization & Controls
- Real-time animation of algorithm execution  
- Adjustable speed control  
- Clear visualization of:
  - explored nodes  
  - final path  

---

### 📊 Live Metrics
- Nodes visited  
- Path length  
- Path cost  
- Execution time  
- Run status  

---

## 🛠️ Tech Stack

- **Language:** Java  
- **UI Framework:** JavaFX (`javafx.controls`, `javafx.graphics`)  
- **Architecture:** Object-Oriented Design (Model–View–Controller inspired)  

---

## 📂 Project Structure

```text
src/
├── Main.java
├── controller/
│   ├── AnimationController.java
│   └── MazeController.java
├── model/
│   ├── Cell.java
│   ├── CellType.java
│   ├── Grid.java
│   ├── algorithms/
│   │   ├── BFS.java
│   │   ├── DFS.java
│   │   ├── AStar.java
│   │   └── Dijkstra.java
│   └── maze/
│       ├── MazeGenerator.java
│       └── WilsonsAlgorithm.java
├── ui/
│   ├── GridRenderer.java
│   ├── Toolbar.java
│   └── StatusBar.java
└── utils/
    └── Pair.java
