package model;

import utils.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Grid {
    private Cell[][] grid;
    private int rows;
    private int cols;
    private Pair start;
    private Pair end;
    private Random random;
    
    public Grid(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.grid = new Cell[rows][cols];
        this.random = new Random();
        initializeGrid();
    }
    
    private void initializeGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = new Cell(i, j, CellType.EMPTY);
            }
        }
        start = new Pair(0, 0);
        end = new Pair(rows - 1, cols - 1);
        grid[start.getRow()][start.getCol()].setType(CellType.START);
        grid[end.getRow()][end.getCol()].setType(CellType.END);
    }
    
    public void resetGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Cell cell = grid[i][j];
                CellType type = cell.getType();
                if (type == CellType.VISITED || type == CellType.PATH || 
                    type == CellType.OPEN_SET || type == CellType.CLOSED_SET) {
                    if (type == CellType.OPEN_SET || type == CellType.CLOSED_SET) {
                        cell.setType(CellType.EMPTY);
                    } else {
                        cell.setType(CellType.EMPTY);
                    }
                }
            }
        }

        if (start != null) {
            grid[start.getRow()][start.getCol()].setType(CellType.START);
        }
        if (end != null) {
            grid[end.getRow()][end.getCol()].setType(CellType.END);
        }
    }
    
    public void clearGrid() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].setType(CellType.EMPTY);
            }
        }
        start = new Pair(0, 0);
        end = new Pair(rows - 1, cols - 1);
        grid[start.getRow()][start.getCol()].setType(CellType.START);
        grid[end.getRow()][end.getCol()].setType(CellType.END);
    }
    
    public void generateRandomMaze(double wallProbability) {
        clearGrid();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Pair current = new Pair(i, j);
                if (!current.equals(start) && !current.equals(end)) {
                    if (random.nextDouble() < wallProbability) {
                        grid[i][j].setType(CellType.WALL);
                    }
                }
            }
        }
    }
    
    public List<Pair> getNeighbors(int row, int col) {
        List<Pair> neighbors = new ArrayList<>();
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        for (int i = 0; i < 4; i++) {
            int newRow = row + dr[i];
            int newCol = col + dc[i];
            if (isValid(newRow, newCol) && grid[newRow][newCol].isWalkable()) {
                neighbors.add(new Pair(newRow, newCol));
            }
        }
        return neighbors;
    }
    
    public int getCost(int row, int col) {
        if (isValid(row, col)) {
            return grid[row][col].getCost();
        }
        return Integer.MAX_VALUE;
    }
    
    public boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }
    
    public Cell getCell(int row, int col) {
        if (isValid(row, col)) {
            return grid[row][col];
        }
        return null;
    }
    
    public void setCellType(int row, int col, CellType type) {
        if (isValid(row, col)) {
            grid[row][col].setType(type);
        }
    }
    
    public Pair getStart() {
        return start;
    }
    
    public Pair getEnd() {
        return end;
    }
    
    public void setStart(int row, int col) {
        if (start != null && isValid(start.getRow(), start.getCol())) {
            grid[start.getRow()][start.getCol()].setType(CellType.EMPTY);
        }
        start = new Pair(row, col);
        if (isValid(row, col)) {
            grid[row][col].setType(CellType.START);
        }
    }
    
    public void setEnd(int row, int col) {
        if (end != null && isValid(end.getRow(), end.getCol())) {
            grid[end.getRow()][end.getCol()].setType(CellType.EMPTY);
        }
        end = new Pair(row, col);
        if (isValid(row, col)) {
            grid[row][col].setType(CellType.END);
        }
    }
    
    public int getRows() {
        return rows;
    }
    
    public int getCols() {
        return cols;
    }
    
    public void resize(int newRows, int newCols) {
        this.rows = newRows;
        this.cols = newCols;
        this.grid = new Cell[rows][cols];
        initializeGrid();
    }
}

