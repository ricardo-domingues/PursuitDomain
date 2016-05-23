/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pursuitDomain;

import java.awt.Color;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class PredatorGreedy extends Agent {

    private Random random;

    public PredatorGreedy(Cell cell, Color color) {
        super(cell, color);
        random = new Random();
    }

    @Override
    public void act(Environment environment) {
        execute(decide(environment), environment);
    }

    private Action decide(Environment environment) {
        Cell preyCell = environment.getPrey().getCell();
        
        int distanceWest = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine()-1, this.getCell().getColumn()), preyCell);
        int distanceEast = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine()+1, this.getCell().getColumn()), preyCell);
        int distanceNorth = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine(), this.getCell().getColumn()-1), preyCell);
        int distanceSouth = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine(), this.getCell().getColumn()+1), preyCell);
        
        if(distanceNorth < distanceSouth && distanceNorth < distanceEast && distanceNorth < distanceWest){
            return Action.NORTH;
        }
        
        
        return null;
    }

    private void execute(Action action, Environment environment) {
        Cell nextCell;
        if (action == Action.NORTH) {
            nextCell = environment.getNorthCell(cell);
        } else if (action == Action.SOUTH) {
            nextCell = environment.getSouthCell(cell);
        } else if (action == Action.WEST) {
            nextCell = environment.getWestCell(cell);
        } else {
            nextCell = environment.getEastCell(cell);
        }

        if (!nextCell.hasAgent()) {
            setCell(nextCell);
        }
    }

}

