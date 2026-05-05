package model.algorithms;

import model.Grid;
import utils.Pair;
import java.util.*;

public class DFS {
    private Grid grid;
    private List<Pair> visitedOrder;
    private List<Pair> path;
    
    public DFS(Grid grid) {
        this.grid = grid;
        this.visitedOrder = new ArrayList<>();
        this.path = new ArrayList<>();
    }
    
    public List<Pair> findPath() {
        visitedOrder.clear();
        path.clear();
        
        Pair start = grid.getStart();
        Pair end = grid.getEnd();
        
        if (start == null || end == null) {
            return path;
        }
        
        Stack<Pair> stack = new Stack<>();
        Map<Pair, Pair> parent = new HashMap<>();
        Set<Pair> visited = new HashSet<>();
        
        stack.push(start);
        visited.add(start);
        
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        while (!stack.isEmpty()) {
            Pair current = stack.pop();
            visitedOrder.add(current);
            
            if (current.equals(end)) {
                Pair node = end;
                while (node != null) {
                    path.add(0, node);
                    node = parent.get(node);
                }
                return path;
            }

            List<Pair> neighbors = new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                int newRow = current.getRow() + dr[i];
                int newCol = current.getCol() + dc[i];
                Pair neighbor = new Pair(newRow, newCol);
                
                if (grid.isValid(newRow, newCol) && 
                    grid.getCell(newRow, newCol).isWalkable() && 
                    !visited.contains(neighbor)) {
                    neighbors.add(neighbor);
                }
            }

            Collections.reverse(neighbors);
            for (Pair neighbor : neighbors) {
                visited.add(neighbor);
                parent.put(neighbor, current);
                stack.push(neighbor);
            }
        }
        
        return path;
    }
    
    public List<Pair> getVisitedOrder() {
        return visitedOrder;
    }
    
    public List<Pair> getPath() {
        return path;
    }
}

