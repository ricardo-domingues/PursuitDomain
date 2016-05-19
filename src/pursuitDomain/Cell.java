package pursuitDomain;

import java.awt.Color;

public class Cell {
    private final int line, column;
    private Agent agent;

    public Cell(int line, int column){
        this.line = line;
        this.column = column;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public boolean hasAgent() {
        return agent != null;
    }

    public int getLine() {
        return line;
    }

    public int getColumn() {
        return column;
    }


    public Color getColor(){
        return agent != null ? agent.getColor() : Color.WHITE;
    }
}