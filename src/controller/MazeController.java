package controller;

import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.CellType;
import model.Grid;
import model.algorithms.AStar;
import model.algorithms.BFS;
import model.algorithms.DFS;
import model.algorithms.Dijkstra;
import model.maze.WilsonsAlgorithm;
import model.maze.MazeGenerator;
import ui.GridRenderer;
import ui.StatusBar;
import utils.Pair;
import java.util.List;
import java.util.Set;


public class MazeController {
    private Grid grid;
    private GridRenderer renderer;
    private StatusBar statusBar;
    private AnimationController animationController;
    private Canvas canvas;
    private Stage primaryStage;
    private String currentAlgorithm;
    private boolean isVisualizing;
    private Set<Pair> currentOpenSet;
    private Set<Pair> currentClosedSet;
    
    public MazeController(Grid grid, GridRenderer renderer, StatusBar statusBar, Canvas canvas, Stage primaryStage) {
        this.grid = grid;
        this.renderer = renderer;
        this.statusBar = statusBar;
        this.canvas = canvas;
        this.primaryStage = primaryStage;
        this.animationController = new AnimationController(grid);
        this.animationController.setOnCellUpdate(() -> {
            renderer.setOpenSet(currentOpenSet);
            renderer.setClosedSet(currentClosedSet);
            renderer.render();
        });
        this.currentAlgorithm = "BFS";
        this.isVisualizing = false;
        
        setupMouseHandlers();
    }
    
    private void setupMouseHandlers() {
        canvas.setOnMousePressed(e -> handleMousePress(e));
        canvas.setOnMouseDragged(e -> handleMouseDrag(e));
    }
    
    private void handleMousePress(MouseEvent e) {
        if (isVisualizing) return;
        
        Pair cellPos = renderer.getCellFromMouse(e.getX(), e.getY());
        if (cellPos == null) return;
        
        int row = cellPos.getRow();
        int col = cellPos.getCol();
        CellType currentType = grid.getCell(row, col).getType();
        
        if (e.getButton() == MouseButton.PRIMARY) {
            if (currentType == CellType.WALL) {
                grid.setCellType(row, col, CellType.EMPTY);
            } else if (currentType != CellType.START && currentType != CellType.END) {
                grid.setCellType(row, col, CellType.WALL);
            }
        } else if (e.getButton() == MouseButton.SECONDARY) {
            if (currentType == CellType.START) {
                grid.setEnd(row, col);
            } else if (currentType == CellType.END) {
                grid.setCellType(row, col, CellType.EMPTY);
            } else {
                grid.setStart(row, col);
            }
        }
        
        renderer.render();
    }
    
    private void handleMouseDrag(MouseEvent e) {
        if (isVisualizing || e.getButton() != MouseButton.PRIMARY) return;
        
        Pair cellPos = renderer.getCellFromMouse(e.getX(), e.getY());
        if (cellPos == null) return;
        
        int row = cellPos.getRow();
        int col = cellPos.getCol();
        CellType currentType = grid.getCell(row, col).getType();
        
        if (currentType != CellType.START && currentType != CellType.END && currentType != CellType.WALL) {
            grid.setCellType(row, col, CellType.WALL);
            renderer.render();
        }
    }
    
    public void startVisualization() {
        if (isVisualizing) return;
        
        isVisualizing = true;
        grid.resetGrid();
        renderer.setOpenSet(null);
        renderer.setClosedSet(null);
        renderer.render();
        
        statusBar.updateStatus("Running " + currentAlgorithm + "...");
        
        long startTime = System.currentTimeMillis();

        new Thread(() -> {
            List<Pair> visitedOrder;
            List<Pair> path;
            Set<Pair> openSet = null;
            Set<Pair> closedSet = null;
            int pathCost = 0;
            
            switch (currentAlgorithm) {
                case "BFS":
                    BFS bfs = new BFS(grid);
                    path = bfs.findPath();
                    visitedOrder = bfs.getVisitedOrder();
                    break;
                case "DFS":
                    DFS dfs = new DFS(grid);
                    path = dfs.findPath();
                    visitedOrder = dfs.getVisitedOrder();
                    break;
                case "A*":
                    AStar aStar = new AStar(grid);
                    path = aStar.findPath();
                    visitedOrder = aStar.getVisitedOrder();
                    openSet = aStar.getOpenSet();
                    closedSet = aStar.getClosedSet();
                    break;
                case "Dijkstra":
                    Dijkstra dijkstra = new Dijkstra(grid);
                    path = dijkstra.findPath();
                    visitedOrder = dijkstra.getVisitedOrder();
                    openSet = dijkstra.getOpenSet();
                    closedSet = dijkstra.getClosedSet();
                    break;
                default:
                    visitedOrder = List.of();
                    path = List.of();
            }
            

            if (path.size() > 0) {
                for (Pair p : path) {
                    pathCost += grid.getCost(p.getRow(), p.getCol());
                }
            }
            
            long endTime = System.currentTimeMillis();
            long duration = endTime - startTime;
            
            final List<Pair> finalVisited = visitedOrder;
            final List<Pair> finalPath = path;
            final Set<Pair> finalOpenSet = openSet;
            final Set<Pair> finalClosedSet = closedSet;
            final int finalCost = pathCost;
            final long finalDuration = duration;
            

            javafx.application.Platform.runLater(() -> {
                currentOpenSet = finalOpenSet;
                currentClosedSet = finalClosedSet;
                
                statusBar.updateVisited(finalVisited.size());
                statusBar.updatePathLength(finalPath.size() > 0 ? finalPath.size() - 1 : 0);
                statusBar.updatePathCost(finalCost);
                statusBar.updateTime(finalDuration);
                
                renderer.setOpenSet(finalOpenSet);
                renderer.setClosedSet(finalClosedSet);
                
                animationController.animate(finalVisited, finalPath, () -> {
                    isVisualizing = false;
                    if (finalPath.size() > 0) {
                        statusBar.updateStatus("Path found!");
                    } else {
                        statusBar.updateStatus("No path found!");
                    }
                }, currentAlgorithm);
            });
        }).start();
    }
    
    public void clearGrid() {
        if (isVisualizing) return;
        animationController.stop();
        grid.clearGrid();
        renderer.setOpenSet(null);
        renderer.setClosedSet(null);
        renderer.render();
        statusBar.reset();
    }
    
    public void resetPath() {
        if (isVisualizing) return;
        animationController.stop();
        grid.resetGrid();
        renderer.setOpenSet(null);
        renderer.setClosedSet(null);
        renderer.render();
        statusBar.updateVisited(0);
        statusBar.updatePathLength(0);
        statusBar.updatePathCost(0);
        statusBar.updateTime(0);
        statusBar.updateStatus("Ready");
    }
    
    public void generateMaze(String generatorType) {
        if (isVisualizing) return;
        animationController.stop();
        
        MazeGenerator generator;
        switch (generatorType) {
            case "Wilson's Algorithm":
                generator = new WilsonsAlgorithm();
                break;
            default:
                grid.generateRandomMaze(0.3);
                renderer.render();
                statusBar.reset();
                return;
        }
        
        generator.generate(grid);
        renderer.render();
        statusBar.reset();
    }
    
    public void setAlgorithm(String algorithm) {
        this.currentAlgorithm = algorithm;
        statusBar.updateAlgorithm(algorithm);
    }
    
    public void setSpeed(double speed) {
        animationController.setSpeed(speed);
    }
    
    public void resizeGrid(int newSize) {
        if (isVisualizing) return;
        animationController.stop();
        grid.resize(newSize, newSize);
        renderer.setGrid(grid);
        renderer.render();
        statusBar.reset();
    }
    
    public boolean isVisualizing() {
        return isVisualizing;
    }
}
