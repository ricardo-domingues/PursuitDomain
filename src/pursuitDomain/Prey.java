package pursuitDomain;

import java.awt.Color;
import java.util.Random;

public class Prey extends Agent{

    final private double restProbability;
    private Random random;
    private Random random2;
    
    public Prey(Cell cell, double restProbability){
        super(cell, Color.RED);
        this.restProbability = restProbability;
        this.random = new Random();
    }
    
    @Override
    public void act(Environment environment) {
        execute(decide(environment), environment);
    }   
    
    private Action decide(Environment environment){
        double rand = random.nextDouble();
        Action action = null;
        if(rand <= restProbability){
            return action;
        }
        else{
            double random = random2.nextDouble();
            
            do{
                if (random >= 0 && random <0.25 && !environment.getNorthCell(cell).hasAgent()) {
                    action = Action.NORTH;
                }
                if (random >= 0.25 && random < 0.50 && !environment.getSouthCell(cell).hasAgent()) {
                    action = Action.SOUTH;
                }
                if (random >= 0.50 && random < 0.75 && !environment.getEastCell(cell).hasAgent()) {
                    action = Action.EAST;
                }
                if (random >= 0.75 && random < 1 && !environment.getEastCell(cell).hasAgent()) {
                    action = Action.WEST;
                }
            }while(action == null);
        }

        return action;
    }
    
    private void execute(Action action, Environment environment) {
        if(action == null){
            setCell(this.getCell());
        }else{
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
}
