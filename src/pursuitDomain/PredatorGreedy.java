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
    private Action previousAction;
    private Action previousAction2;

    public PredatorGreedy(Cell cell, Color color) {
        super(cell, color);
        this.random = new Random();
    }

    @Override
    public void act(Environment environment) {

        if(environment.predatorGreedyDistancePrey(this) > 0){
            execute(decide(environment), environment);
        }

    }

    private Action decide(Environment environment) {
        Cell preyCell = environment.getPrey().getCell();
        Action action = null;

        int distanceWest = environment.distanceBetweenTwoCellsGreedy(new Cell(this.getCell().getLine(), this.getCell().getColumn() - 1), preyCell);
        int distanceEast = environment.distanceBetweenTwoCellsGreedy(new Cell(this.getCell().getLine(), this.getCell().getColumn() + 1), preyCell);
        int distanceNorth = environment.distanceBetweenTwoCellsGreedy(new Cell(this.getCell().getLine() - 1, this.getCell().getColumn()), preyCell);
        int distanceSouth = environment.distanceBetweenTwoCellsGreedy(new Cell(this.getCell().getLine() + 1, this.getCell().getColumn()), preyCell);

        //System.out.println("NORTH:"+distanceNorth+"\nSOUTH:"+distanceSouth+"\nWEST:"+distanceWest+"\nEAST:"+distanceEast);

        int menorDistance = environment.getSize()+1;

        if(distanceWest<menorDistance){
            menorDistance=distanceWest;
        }
        if(distanceEast<menorDistance){
            menorDistance=distanceEast;
        }
        if(distanceNorth<menorDistance){
            menorDistance=distanceNorth;
        }
        if(distanceSouth<menorDistance){
            menorDistance=distanceSouth;
        }


        int tryingsToNorth = 0;
        int tryingsToSouth = 0;
        int tryingsToWest = 0;
        int tryingsToEast = 0;

        do{
            /*if (!environment.getNorthCell(this.cell).hasAgent() && distanceNorth < distanceWest && distanceNorth < distanceSouth && distanceNorth < distanceEast) {
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
            */

            double rand = random.nextDouble();

            if (menorDistance == distanceNorth && distanceNorth == distanceWest) {
                if (!environment.getNorthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.NORTH;
                } else if(!environment.getWestCell(this.cell).hasAgent()){
                        action = Action.WEST;
                }
            }
            if (menorDistance == distanceNorth && distanceNorth == distanceEast) {
                if (!environment.getNorthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.NORTH;
                } else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }
            if (menorDistance == distanceNorth && distanceNorth == distanceSouth) {
                if (!environment.getNorthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.NORTH;
                } else if(!environment.getSouthCell(this.cell).hasAgent()){
                    action = Action.SOUTH;
                }
            }
            if (menorDistance == distanceSouth && distanceSouth == distanceWest) {
                if (!environment.getSouthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.SOUTH;
                } else if(!environment.getWestCell(this.cell).hasAgent()){
                    action = Action.WEST;
                }
            }
            if (menorDistance == distanceSouth && distanceSouth == distanceEast) {
                if (!environment.getSouthCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.SOUTH;
                } else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }
            if (menorDistance == distanceWest && distanceWest == distanceEast) {
                if (!environment.getWestCell(this.cell).hasAgent() && rand < 0.5) {
                    action = Action.WEST;
                } else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }

            if(action == null) {
                if (menorDistance == distanceWest && !environment.getWestCell(this.cell).hasAgent()) {
                    action = Action.WEST;
                }
                if (menorDistance == distanceEast && !environment.getEastCell(this.cell).hasAgent()) {
                    action = Action.EAST;
                }
                if (menorDistance == distanceNorth && !environment.getNorthCell(this.cell).hasAgent()) {
                    action = Action.NORTH;
                }
                if (menorDistance == distanceSouth && !environment.getSouthCell(this.cell).hasAgent()) {
                    action = Action.SOUTH;
                }
            }
            //CASO TENHA AGENTE

            rand = random.nextDouble();
            if(menorDistance == distanceWest && environment.getWestCell(this.cell).hasAgent()){
                if (!environment.getNorthCell(this.cell).hasAgent() && rand <= 0.33) {
                    action = Action.NORTH;
                } else if(!environment.getSouthCell(this.cell).hasAgent() && rand > 0.33 && rand <= 0.66){
                    action = Action.SOUTH;
                }else if(!environment.getEastCell(this.cell).hasAgent()){
                    action = Action.EAST;
                }
            }
            if(menorDistance == distanceEast && environment.getEastCell(this.cell).hasAgent()){
                if (!environment.getNorthCell(this.cell).hasAgent() && rand <= 0.33) {
                    action = Action.NORTH;
                } else if(!environment.getSouthCell(this.cell).hasAgent() && rand > 0.33 && rand <= 0.66){
                    action = Action.SOUTH;
                }else if(!environment.getWestCell(this.cell).hasAgent()){
                    action = Action.WEST;
                }
            }
            if(menorDistance == distanceNorth && environment.getNorthCell(this.cell).hasAgent()){
                if (!environment.getWestCell(this.cell).hasAgent() && rand <= 0.33) {
                    action = Action.WEST;
                } else if(!environment.getEastCell(this.cell).hasAgent() && rand > 0.33 && rand <= 0.66){
                    action = Action.EAST;
                }else if(!environment.getSouthCell(this.cell).hasAgent()){
                    action = Action.SOUTH;
                }
            }
            if(menorDistance == distanceSouth && environment.getSouthCell(this.cell).hasAgent()){
                if (!environment.getWestCell(this.cell).hasAgent() && rand <= 0.33) {
                    action = Action.WEST;
                } else if(!environment.getEastCell(this.cell).hasAgent() && rand > 0.33 && rand <= 0.66){
                    action = Action.EAST;
                }else if(!environment.getNorthCell(this.cell).hasAgent()){
                    action = Action.NORTH;
                }
            }

            if(action == Action.NORTH || action == Action.WEST){
                //if(environment.getWestCell(this.cell).hasAgent() && )
            }
        }
        while(action == null);

        previousAction2 = previousAction;
        previousAction = action;

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

