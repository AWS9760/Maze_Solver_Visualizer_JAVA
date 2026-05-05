package ui;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import model.Cell;
import model.CellType;
import model.Grid;
import utils.Pair;
import java.util.Set;

public class GridRenderer {
    private Canvas canvas;
    private GraphicsContext gc;
    private Grid grid;
    private double cellWidth;
    private double cellHeight;
    private Pair hoveredCell;
    private Set<Pair> openSet;
    private Set<Pair> closedSet;
    private boolean darkMode;
    
    public GridRenderer(Canvas canvas, Grid grid) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.grid = grid;
        this.darkMode = true;
        updateCellSize();
        setupHoverHandlers();
    }
    
    private void setupHoverHandlers() {
        canvas.setOnMouseMoved(e -> {
            Pair cellPos = getCellFromMouse(e.getX(), e.getY());
            if (cellPos != null && !cellPos.equals(hoveredCell)) {
                hoveredCell = cellPos;
                render();
            }
        });
        
        canvas.setOnMouseExited(e -> {
            hoveredCell = null;
            render();
        });
    }
    
    public void updateCellSize() {
        cellWidth = canvas.getWidth() / grid.getCols();
        cellHeight = canvas.getHeight() / grid.getRows();
    }
    
    public void setOpenSet(Set<Pair> openSet) {
        this.openSet = openSet;
    }
    
    public void setClosedSet(Set<Pair> closedSet) {
        this.closedSet = closedSet;
    }
    
    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        gc.setFill(darkMode ? Color.rgb(30, 30, 30) : Color.WHITE);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        
        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                Cell cell = grid.getCell(i, j);
                if (cell != null) {
                    Pair pos = new Pair(i, j);

                    if (openSet != null && openSet.contains(pos) && 
                        cell.getType() != CellType.START && cell.getType() != CellType.END) {
                        drawCellWithType(cell, j * cellWidth, i * cellHeight, CellType.OPEN_SET);
                    } else if (closedSet != null && closedSet.contains(pos) && 
                               cell.getType() != CellType.START && cell.getType() != CellType.END &&
                               cell.getType() != CellType.PATH) {
                        drawCellWithType(cell, j * cellWidth, i * cellHeight, CellType.CLOSED_SET);
                    } else {
                        drawCell(cell, j * cellWidth, i * cellHeight);
                    }
                }
            }
        }

        if (hoveredCell != null) {
            double x = hoveredCell.getCol() * cellWidth;
            double y = hoveredCell.getRow() * cellHeight;
            gc.setStroke(Color.rgb(255, 200, 0, 0.7));
            gc.setLineWidth(3);
            gc.strokeRect(x + 1, y + 1, cellWidth - 2, cellHeight - 2);
        }

        gc.setStroke(darkMode ? Color.rgb(60, 60, 60) : Color.LIGHTGRAY);
        gc.setLineWidth(0.5);
        for (int i = 0; i <= grid.getRows(); i++) {
            gc.strokeLine(0, i * cellHeight, canvas.getWidth(), i * cellHeight);
        }
        for (int j = 0; j <= grid.getCols(); j++) {
            gc.strokeLine(j * cellWidth, 0, j * cellWidth, canvas.getHeight());
        }
    }
    
    private void drawCell(Cell cell, double x, double y) {
        Color color = getColorForCellType(cell.getType());
        gc.setFill(color);
        gc.fillRect(x, y, cellWidth, cellHeight);

        if (cell.getType() == CellType.PATH) {
            gc.setStroke(Color.rgb(255, 215, 0, 0.8));
            gc.setLineWidth(2);
            gc.strokeRect(x + 1, y + 1, cellWidth - 2, cellHeight - 2);
        }
    }
    
    private void drawCellWithType(Cell cell, double x, double y, CellType overrideType) {
        Color color = getColorForCellType(overrideType);
        gc.setFill(color);
        gc.fillRect(x, y, cellWidth, cellHeight);
    }
    
    private Color getColorForCellType(CellType type) {
        if (darkMode) {
            switch (type) {
                case EMPTY:
                    return Color.rgb(40, 40, 40);
                case WALL:
                    return Color.rgb(20, 20, 20);
                case START:
                    return Color.rgb(76, 175, 80);
                case END:
                    return Color.rgb(244, 67, 54);
                case VISITED:
                    return Color.rgb(33, 150, 243);
                case PATH:
                    return Color.rgb(255, 235, 59);
                case OPEN_SET:
                    return Color.rgb(129, 212, 250, 0.6);
                case CLOSED_SET:
                    return Color.rgb(100, 181, 246, 0.4);
                default:
                    return Color.rgb(40, 40, 40);
            }
        } else {
            switch (type) {
                case EMPTY:
                    return Color.WHITE;
                case WALL:
                    return Color.BLACK;
                case START:
                    return Color.rgb(76, 175, 80);
                case END:
                    return Color.rgb(244, 67, 54);
                case VISITED:
                    return Color.rgb(144, 202, 249);
                case PATH:
                    return Color.rgb(255, 235, 59);
                case OPEN_SET:
                    return Color.rgb(187, 222, 251, 0.7);
                case CLOSED_SET:
                    return Color.rgb(144, 202, 249, 0.5);
                default:
                    return Color.WHITE;
            }
        }
    }
    
    public Pair getCellFromMouse(double mouseX, double mouseY) {
        int col = (int) (mouseX / cellWidth);
        int row = (int) (mouseY / cellHeight);
        if (grid.isValid(row, col)) {
            return new Pair(row, col);
        }
        return null;
    }
    
    public void setGrid(Grid grid) {
        this.grid = grid;
        updateCellSize();
    }
}

