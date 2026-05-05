package model.algorithms;

import model.Grid;
import utils.Pair;
import java.util.*;

public class Dijkstra {
    private Grid grid;
    private List<Pair> visitedOrder;
    private List<Pair> path;
    private Set<Pair> openSet;
    private Set<Pair> closedSet;
    
    public Dijkstra(Grid grid) {
        this.grid = grid;
        this.visitedOrder = new ArrayList<>();
        this.path = new ArrayList<>();
        this.openSet = new HashSet<>();
        this.closedSet = new HashSet<>();
    }
    
    public List<Pair> findPath() {
        visitedOrder.clear();
        path.clear();
        openSet.clear();
        closedSet.clear();
        
        Pair start = grid.getStart();
        Pair end = grid.getEnd();
        
        if (start == null || end == null) {
            return path;
        }

        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(Node::getDistance));
        Map<Pair, Integer> distances = new HashMap<>();
        Map<Pair, Pair> cameFrom = new HashMap<>();
        
        distances.put(start, 0);
        pq.add(new Node(start, 0));
        openSet.add(start);
        
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};
        
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            Pair currentPos = current.getPosition();
            
            if (closedSet.contains(currentPos)) {
                continue;
            }
            
            closedSet.add(currentPos);
            openSet.remove(currentPos);
            visitedOrder.add(currentPos);
            
            if (currentPos.equals(end)) {
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
                
                int edgeCost = grid.getCost(newRow, newCol);
                int tentativeDistance = distances.getOrDefault(currentPos, Integer.MAX_VALUE) + edgeCost;
                
                if (tentativeDistance < distances.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, currentPos);
                    distances.put(neighbor, tentativeDistance);
                    pq.add(new Node(neighbor, tentativeDistance));
                    openSet.add(neighbor);
                }
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
    
    public Set<Pair> getOpenSet() {
        return openSet;
    }
    
    public Set<Pair> getClosedSet() {
        return closedSet;
    }

    private static class Node {
        private Pair position;
        private int distance;
        
        public Node(Pair position, int distance) {
            this.position = position;
            this.distance = distance;
        }
        
        public Pair getPosition() {
            return position;
        }
        
        public int getDistance() {
            return distance;
        }
    }
}

