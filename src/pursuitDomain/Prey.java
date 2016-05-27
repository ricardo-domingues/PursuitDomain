package pursuitDomain;

import java.awt.Color;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Prey extends Agent{

    final private double restProbability;
    private Random random;
    
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
            rand = random.nextDouble();
            
            do{
                List<Cell> freeCells = environment.getFreeSorroundingCells(this.cell);

                boolean northCellFree = false;
                boolean southCellFree = false;
                boolean westCellFree = false;
                boolean eastCellFree = false;



                for(Cell cell: freeCells){
                    if(cell == environment.getNorthCell(this.cell)){
                        northCellFree = true;
                    }
                    if(cell == environment.getSouthCell(this.cell)){
                        southCellFree = true;
                    }
                    if(cell == environment.getWestCell(this.cell)){
                        westCellFree = true;
                    }
                    if(cell == environment.getEastCell(this.cell)){
                        eastCellFree = true;
                    }
                }

                int numberOfCells = freeCells.size();

                for(int i=0; i<numberOfCells;i++){

                    if (northCellFree && rand >= 0 && rand <1/numberOfCells && !environment.getNorthCell(cell).hasAgent()) {
                        action = Action.NORTH;
                    }

                    if (southCellFree && rand >= 1/numberOfCells && rand < 2/numberOfCells && !environment.getSouthCell(cell).hasAgent()) {
                        action = Action.SOUTH;
                    }
                    if (rand >= 2/numberOfCells && rand < 3/numberOfCells && !environment.getEastCell(cell).hasAgent()) {
                        action = Action.EAST;
                    }
                    if (rand >= 3/numberOfCells && rand < 1 && !environment.getEastCell(cell).hasAgent()) {
                        action = Action.WEST;
                    }
                }

                /*
                if (rand >= 0 && rand <0.25 && !environment.getNorthCell(cell).hasAgent()) {
                    action = Action.NORTH;
                }
                if (rand >= 0.25 && rand < 0.50 && !environment.getSouthCell(cell).hasAgent()) {
                    action = Action.SOUTH;
                }
                if (rand >= 0.50 && rand < 0.75 && !environment.getEastCell(cell).hasAgent()) {
                    action = Action.EAST;
                }
                if (rand >= 0.75 && rand < 1 && !environment.getEastCell(cell).hasAgent()) {
                    action = Action.WEST;
                }
                */
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
