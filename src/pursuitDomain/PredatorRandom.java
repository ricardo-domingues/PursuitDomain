/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pursuitDomain;

import java.awt.Color;
import java.util.List;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class PredatorRandom extends Predator {

    private Random random;

    public PredatorRandom(Cell cell, int inputLayerSize, int hiddenLayerSize, int outputLayerSize, Random random) {
        super(cell, inputLayerSize, hiddenLayerSize, outputLayerSize);
        this.random = random;
    }

    @Override
    public void act(Environment environment) {
        execute(decide(environment), environment);
    }

    private Action decide(Environment environment) {
        int rand = random.nextInt(4);
        Action action = null;
        
        do{
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

