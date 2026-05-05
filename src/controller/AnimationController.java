package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import model.CellType;
import model.Grid;
import utils.Pair;
import java.util.List;


public class AnimationController {
    private Grid grid;
    private List<Pair> visitedOrder;
    private List<Pair> path;
    private Timeline timeline;
    private int currentVisitedIndex;
    private int currentPathIndex;
    private Runnable onAnimationComplete;
    private Runnable onCellUpdate;
    private double speed;


    public AnimationController(Grid grid) {
        this.grid = grid;
        this.speed = 50.0;
    }


    public void setOnCellUpdate(Runnable onCellUpdate) {
        this.onCellUpdate = onCellUpdate;
    }


    public void animate(List<Pair> visitedOrder, List<Pair> path, Runnable onComplete, String algorithm) {
        this.visitedOrder = visitedOrder;
        this.path = path;
        this.onAnimationComplete = onComplete;
        this.currentVisitedIndex = 0;
        this.currentPathIndex = 0;
        
        if (timeline != null) {
            timeline.stop();
        }

        double delay = (101 - speed) * 2;

        boolean isBFSorDFS = algorithm != null && (algorithm.equals("BFS") || algorithm.equals("DFS"));
        boolean isAStarOrDijkstra = algorithm != null && (algorithm.equals("A*") || algorithm.equals("Dijkstra"));

        double visitedDelay = isAStarOrDijkstra ? delay * 2.0 : delay;
        
        int pathStartOffset;
        if (isBFSorDFS) {
            pathStartOffset = visitedOrder.size();
        } else {
            pathStartOffset = path.size() > 0 ? Math.min(30, visitedOrder.size()) : visitedOrder.size();
        }
        
        timeline = new Timeline();


        for (int i = 0; i < visitedOrder.size(); i++) {
            final int index = i;
            Pair cell = visitedOrder.get(i);
            KeyFrame keyFrame = new KeyFrame(
                Duration.millis(visitedDelay * (i + 1)),
                e -> {
                    if (grid.isValid(cell.getRow(), cell.getCol())) {
                        CellType currentType = grid.getCell(cell.getRow(), cell.getCol()).getType();
                        if (currentType != CellType.START && currentType != CellType.END && 
                            currentType != CellType.PATH) {
                            grid.setCellType(cell.getRow(), cell.getCol(), CellType.VISITED);
                            if (onCellUpdate != null) {
                                onCellUpdate.run();
                            }
                        }
                    }
                }
            );
            timeline.getKeyFrames().add(keyFrame);
        }
        

        if (path.size() > 0) {
            double pathStartTime = visitedDelay * pathStartOffset;
            for (int i = 0; i < path.size(); i++) {
                final int index = i;
                Pair cell = path.get(i);
                KeyFrame keyFrame = new KeyFrame(
                    Duration.millis(pathStartTime + delay * (i + 1)),
                    e -> {
                        if (grid.isValid(cell.getRow(), cell.getCol())) {
                            CellType currentType = grid.getCell(cell.getRow(), cell.getCol()).getType();
                            if (currentType != CellType.START && currentType != CellType.END) {
                                grid.setCellType(cell.getRow(), cell.getCol(), CellType.PATH);
                                if (onCellUpdate != null) {
                                    onCellUpdate.run();
                                }
                            }
                        }
                    }
                );
                timeline.getKeyFrames().add(keyFrame);
            }
        }

        if (onComplete != null) {
            double completionTime;
            if (path.size() > 0) {
                double pathStartTime = visitedDelay * pathStartOffset;
                completionTime = pathStartTime + delay * (path.size() + 1);
            } else {
                completionTime = visitedDelay * (visitedOrder.size() + 1);
            }
            KeyFrame completeFrame = new KeyFrame(
                Duration.millis(completionTime),
                e -> onComplete.run()
            );
            timeline.getKeyFrames().add(completeFrame);
        }
        
        timeline.play();
    }
    
    public void stop() {
        if (timeline != null) {
            timeline.stop();
        }
    }
    
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
