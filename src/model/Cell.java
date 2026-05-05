package model;

public class Cell {
    private int row;
    private int col;
    private CellType type;
    
    public Cell(int row, int col, CellType type) {
        this.row = row;
        this.col = col;
        this.type = type;
    }
    
    public int getRow() {
        return row;
    }
    
    public int getCol() {
        return col;
    }
    
    public CellType getType() {
        return type;
    }
    
    public void setType(CellType type) {
        this.type = type;
    }
    
    public boolean isWalkable() {
        return type.isWalkable();
    }
    
    public int getCost() {
        return type.getCost();
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cell cell = (Cell) obj;
        return row == cell.row && col == cell.col;
    }
    
    @Override
    public int hashCode() {
        return 31 * row + col;
    }
    
    @Override
    public String toString() {
        return "Cell(" + row + ", " + col + ", " + type + ")";
    }
}

