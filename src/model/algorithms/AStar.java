package model.algorithms;

import model.Grid;
import utils.Pair;
import java.util.*;


public class AStar {
    private Grid grid;
    private List<Pair> visitedOrder;
    private List<Pair> path;
    private Set<Pair> openSet;
    private Set<Pair> closedSet;
    
    public AStar(Grid grid) {
        this.grid = grid;
        this.visitedOrder = new ArrayList<>();
        this.path = new ArrayList<>();
        this.openSet = new HashSet<>();
        this.closedSet = new HashSet<>();
    }
    
    public List<Pair> findPath() {
        visitedOrder.clear();
        path.clear();
        
        Pair start = grid.getStart();
        Pair end = grid.getEnd();
        
        if (start == null || end == null) {
            return path;
        }

        PriorityQueue<Node> openSetPQ = new PriorityQueue<>(Comparator.comparingInt(Node::getF));
        Map<Pair, Integer> gScore = new HashMap<>();
        Map<Pair, Pair> cameFrom = new HashMap<>();
        
        gScore.put(start, 0);
        openSetPQ.add(new Node(start, 0, manhattanDistance(
            start.getRow(), start.getCol(), end.getRow(), end.getCol())));
        openSet.add(start);
        
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        while (!openSetPQ.isEmpty()) {
            Node current = openSetPQ.poll();
            Pair currentPos = current.getPosition();
            
            if (closedSet.contains(currentPos)) {
                continue;
            }
            
            closedSet.add(currentPos);
            openSet.remove(currentPos);
            visitedOrder.add(currentPos);
            
            if (currentPos.equals(end)) {
                // Reconstruct path
                Pair node = end;
                while (node != null) {
                    path.add(0, node);
                    node = cameFrom.get(node);
                }
                return path;
            }
            
            for (int i = 0; i < 4; i++) {
                int newRow = currentPos.getRow() + dr[i];
                int newCol = currentPos.getCol() + dc[i];
                Pair neighbor = new Pair(newRow, newCol);
                
                if (!grid.isValid(newRow, newCol) || 
                    !grid.getCell(newRow, newCol).isWalkable() || 
                    closedSet.contains(neighbor)) {
                    continue;
                }
                
                int cost = grid.getCost(newRow, newCol);
                int tentativeG = gScore.getOrDefault(currentPos, Integer.MAX_VALUE) + cost;
                
                if (tentativeG < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, currentPos);
                    gScore.put(neighbor, tentativeG);
                    int h = manhattanDistance(
                        newRow, newCol, end.getRow(), end.getCol());
                    int f = tentativeG + h;
                    openSetPQ.add(new Node(neighbor, tentativeG, h));
                    openSet.add(neighbor);
                }
            }
        }
        
        return path;
    }

    public int manhattanDistance(int row1, int col1, int row2, int col2) {
        return Math.abs(row1 - row2) + Math.abs(col1 - col2);
    }
    
    public List<Pair> getVisitedOrder() {
        return visitedOrder;
    }
    
    public List<Pair> getPath() {
        return path;
    }
    
    public Set<Pair> getOpenSet() {
        return openSet;
    }
    
    public Set<Pair> getClosedSet() {
        return closedSet;
    }
    

    private static class Node {
        private Pair position;
        private int g;
        private int h;
        
        public Node(Pair position, int g, int h) {
            this.position = position;
            this.g = g;
            this.h = h;
        }
        
        public Pair getPosition() {
            return position;
        }
        
        public int getF() {
            return g + h;
        }
        
        public int getG() {
            return g;
        }
        
        public int getH() {
            return h;
        }
    }
}

