package model.maze;

import model.CellType;
import model.Grid;
import utils.Pair;
import java.util.*;

public class WilsonsAlgorithm implements MazeGenerator {
    private Random random;
    
    public WilsonsAlgorithm() {
        this.random = new Random();
    }
    
    @Override
    public void generate(Grid grid) {
        Pair actualStart = grid.getStart();
        Pair actualEnd = grid.getEnd();
        int startRow = actualStart.getRow();
        int startCol = actualStart.getCol();
        int endRow = actualEnd.getRow();
        int endCol = actualEnd.getCol();

        for (int i = 0; i < grid.getRows(); i++) {
            for (int j = 0; j < grid.getCols(); j++) {
                if ((i == startRow && j == startCol) || (i == endRow && j == endCol)) {
                    grid.setCellType(i, j, CellType.EMPTY);
                } else {
                    grid.setCellType(i, j, CellType.WALL);
                }
            }
        }

        List<Pair> unvisited = new ArrayList<>();
        for (int i = 1; i < grid.getRows() - 1; i += 2) {
            for (int j = 1; j < grid.getCols() - 1; j += 2) {
                unvisited.add(new Pair(i, j));
            }
        }

        Pair mazeStart = unvisited.remove(random.nextInt(unvisited.size()));
        grid.setCellType(mazeStart.getRow(), mazeStart.getCol(), CellType.EMPTY);
        
        int[] dr = {-2, 2, 0, 0};
        int[] dc = {0, 0, -2, 2};
        
        while (!unvisited.isEmpty()) {
            Pair current = unvisited.get(random.nextInt(unvisited.size()));
            List<Pair> path = new ArrayList<>();
            path.add(current);

            while (grid.getCell(current.getRow(), current.getCol()).getType() == CellType.WALL) {
                List<Pair> neighbors = new ArrayList<>();
                for (int i = 0; i < 4; i++) {
                    int newRow = current.getRow() + dr[i];
                    int newCol = current.getCol() + dc[i];
                    if (grid.isValid(newRow, newCol)) {
                        neighbors.add(new Pair(newRow, newCol));
                    }
                }
                
                if (!neighbors.isEmpty()) {
                    current = neighbors.get(random.nextInt(neighbors.size()));

                    int index = path.indexOf(current);
                    if (index != -1) {
                        path = path.subList(0, index + 1);
                    } else {
                        path.add(current);
                    }
                }
            }

            for (int i = 0; i < path.size() - 1; i++) {
                Pair cell = path.get(i);
                Pair next = path.get(i + 1);
                
                grid.setCellType(cell.getRow(), cell.getCol(), CellType.EMPTY);

                int wallRow = (cell.getRow() + next.getRow()) / 2;
                int wallCol = (cell.getCol() + next.getCol()) / 2;
                grid.setCellType(wallRow, wallCol, CellType.EMPTY);
                
                unvisited.remove(cell);
            }

            Pair last = path.get(path.size() - 1);
            grid.setCellType(last.getRow(), last.getCol(), CellType.EMPTY);
            unvisited.remove(last);
        }

        connectToMaze(grid, startRow, startCol);
        connectToMaze(grid, endRow, endCol);

        grid.setStart(startRow, startCol);
        grid.setEnd(endRow, endCol);
    }
    

    private void connectToMaze(Grid grid, int row, int col) {
        Pair nearestEmpty = findNearestEmpty(grid, row, col, row, col);
        
        if (nearestEmpty != null) {
            int currentRow = row;
            int currentCol = col;
            int targetRow = nearestEmpty.getRow();
            int targetCol = nearestEmpty.getCol();

            while (currentRow != targetRow || currentCol != targetCol) {
                if (currentCol != targetCol) {
                    int step = (targetCol > currentCol) ? 1 : -1;
                    currentCol += step;
                } else if (currentRow != targetRow) {
                    int step = (targetRow > currentRow) ? 1 : -1;
                    currentRow += step;
                }
                
                if (grid.isValid(currentRow, currentCol)) {
                    grid.setCellType(currentRow, currentCol, CellType.EMPTY);
                } else {
                    break;
                }
            }
        } else {
            int[] dr = {-1, 1, 0, 0};
            int[] dc = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) {
                int newRow = row + dr[i];
                int newCol = col + dc[i];
                if (grid.isValid(newRow, newCol)) {
                    grid.setCellType(newRow, newCol, CellType.EMPTY);
                    if (grid.isValid(newRow + dr[i], newCol + dc[i])) {
                        grid.setCellType(newRow + dr[i], newCol + dc[i], CellType.EMPTY);
                    }
                }
            }
        }
    }

    private Pair findNearestEmpty(Grid grid, int row, int col, int excludeRow, int excludeCol) {
        Pair nearest = null;
        int minManhattanDist = Integer.MAX_VALUE;

        int maxRadius = Math.max(grid.getRows(), grid.getCols());
        for (int radius = 1; radius <= maxRadius; radius++) {
            for (int dr = -radius; dr <= radius; dr++) {
                for (int dc = -radius; dc <= radius; dc++) {
                    if (Math.abs(dr) + Math.abs(dc) == radius) {
                        int newRow = row + dr;
                        int newCol = col + dc;
                        if ((newRow == excludeRow && newCol == excludeCol)) {
                            continue;
                        }
                        if (grid.isValid(newRow, newCol)) {
                            CellType type = grid.getCell(newRow, newCol).getType();
                            if (type == CellType.EMPTY) {
                                int manhattanDist = Math.abs(dr) + Math.abs(dc);
                                if (manhattanDist < minManhattanDist) {
                                    minManhattanDist = manhattanDist;
                                    nearest = new Pair(newRow, newCol);
                                }
                            }
                        }
                    }
                }
            }
            if (nearest != null && minManhattanDist <= 10) {
                return nearest;
            }
        }
        
        return nearest;
    }
}

