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
public class PredatorRandom extends Agent {

    private Random random;

    public PredatorRandom(Cell cell, Color color) {
        super(cell, color);
        this.random = new Random();
    }

    @Override
    public void act(Environment environment) {
        execute(decide(environment), environment);
    }

    private Action decide(Environment environment) {
        
        Action action = null;
        System.out.println(environment.distanceBetweenTwoCells(this.getCell(), environment.getPrey().getCell()));

        do{
            int rand = random.nextInt(4);
            if (rand == 0 && !environment.getNorthCell(cell).hasAgent()) {
                action = Action.NORTH;
            }
            if (rand == 1 && !environment.getSouthCell(cell).hasAgent()) {
                action = Action.SOUTH;
            }
            if (rand == 2 && !environment.getEastCell(cell).hasAgent()) {
                action = Action.EAST;
            }
            if (rand == 3 && !environment.getEastCell(cell).hasAgent()) {
                action = Action.WEST;
            }
        }while(action == null);

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

