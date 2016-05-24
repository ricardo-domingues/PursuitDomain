/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pursuitDomain;

import java.awt.*;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class PredatorGreedy extends Agent {

    private Random random;

    public PredatorGreedy(Cell cell, Color color) {
        super(cell, color);
        this.random = new Random();
    }

    @Override
    public void act(Environment environment) {

        if(environment.predatorDistancePrey(this) != 1){
            execute(decide(environment), environment);
        }
    }

    private Action decide(Environment environment) {
        Cell preyCell = environment.getPrey().getCell();
        Action action = null;

        int distanceWest = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine(), this.getCell().getColumn() - 1), preyCell);
        int distanceEast = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine(), this.getCell().getColumn() + 1), preyCell);
        int distanceNorth = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine() - 1, this.getCell().getColumn()), preyCell);
        int distanceSouth = environment.distanceBetweenTwoCells(new Cell(this.getCell().getLine() + 1, this.getCell().getColumn()), preyCell);

        do{
            if (!environment.getNorthCell(this.cell).hasAgent() && distanceNorth < distanceWest && distanceNorth < distanceSouth && distanceNorth < distanceEast) {
                action = Action.NORTH;
            }
            if (!environment.getEastCell(this.cell).hasAgent() && distanceEast < distanceNorth && distanceEast < distanceSouth && distanceEast < distanceWest) {
                action = Action.EAST;
            }
            if (!environment.getSouthCell(this.cell).hasAgent() && distanceSouth < distanceEast && distanceSouth < distanceNorth && distanceSouth < distanceWest) {
                action = Action.SOUTH;
            }
            if (!environment.getWestCell(this.cell).hasAgent() && distanceWest < distanceSouth && distanceWest < distanceEast && distanceWest < distanceNorth) {
                action = Action.WEST;
            }
            

            double rand = random.nextDouble();

            if (distanceNorth == distanceWest) {
                if (!environment.getNorthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.NORTH;
                } else if(!environment.getWestCell(this.cell).hasAgent()){
                        action = Action.WEST;
                }
            }
            if (distanceNorth == distanceEast) {
                if (!environment.getNorthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.NORTH;
                } else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }
            if (distanceSouth == distanceWest) {
                if (!environment.getSouthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.SOUTH;
                } else if(!environment.getWestCell(this.cell).hasAgent()){
                    action = Action.WEST;
                }
            }
            if (distanceSouth == distanceEast) {
                if (!environment.getSouthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.SOUTH;
                } else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }
        }
        while(action == null);


        return action;
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

