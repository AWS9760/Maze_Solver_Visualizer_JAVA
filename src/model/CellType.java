package model;


public enum CellType {
    EMPTY(1),
    WALL(Integer.MAX_VALUE),
    START(1),
    END(1),
    VISITED(1),
    PATH(1),
    OPEN_SET(1),
    CLOSED_SET(1);
    
    private final int cost;
    
    CellType(int cost) {
        this.cost = cost;
    }
    
    public int getCost() {
        return cost;
    }
    
    public boolean isWalkable() {
        return this != WALL;
    }
}

