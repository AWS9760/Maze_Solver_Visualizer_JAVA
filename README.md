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
```

## ⚙️ How to Run

### ▶️ Option 1: IntelliJ IDEA (Recommended)

1. Open the project in IntelliJ IDEA  
2. Configure **JDK 17+**  
3. Add **JavaFX SDK** to project libraries  
4. Set VM options:

```bash
--module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.graphics
```

5. Run:
```bash
Main.java
```

### 💻 Option 2: Command Line

```bash
javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.graphics -d out src/Main.java src/controller/*.java src/model/*.java src/model/algorithms/*.java src/model/maze/*.java src/ui/*.java src/utils/*.java

java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.graphics -cp out Main
```
> Replace `path/to/javafx-sdk/lib` with your local JavaFX SDK path.

---

## 🎯 Usage Guide

1. Select a pathfinding algorithm  
2. Create or generate a maze  
3. Adjust speed and grid size (optional)  
4. Click **Start Visualization**  
5. Observe algorithm behavior and results  

### Controls

- **Reset Path** → clears only the visualization  
- **Clear Grid** → resets the entire board  

---

## 📘 Algorithm Summary

| Algorithm   | Description |
|------------|------------|
| **BFS**    | Finds shortest path in unweighted grids |
| **DFS**    | Explores deeply, not always optimal |
| **Dijkstra** | Computes shortest path using cumulative cost |
| **A\***    | Uses heuristics for faster optimal pathfinding |

---

## 💡 Future Improvements

- Side-by-side algorithm comparison  
- Additional maze generation techniques  
- Save/load maze configurations  
- Export run statistics  
- Step-by-step execution mode  

---

## 🤝 Contributing

Contributions are welcome!  
Feel free to fork the project and submit a pull request.

---

## 📜 License

This project is open-source and available under the **MIT License**.

---

## 👨‍💻 Author

**Abdul Wali**  
Computer Science Student | Software Developer  

---

⭐ *If you find this project useful, consider giving it a star!*
   
