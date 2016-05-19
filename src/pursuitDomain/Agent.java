package pursuitDomain;

import java.awt.Color;

public abstract class Agent {

    protected Cell cell;
    protected Color color;

    public Agent(Cell cell, Color color) {
        this.cell = cell;
        if(cell != null){this.cell.setAgent(this);}
        this.color = color;
    }

    public abstract void act(Environment environment);

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell newCell) {
        if(this.cell != null){this.cell.setAgent(null);}
        this.cell = newCell;
        if(newCell != null){newCell.setAgent(this);}
    }    
    
    public Color getColor() {
        return color;
    }
}
