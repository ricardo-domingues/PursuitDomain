package pursuitDomain;

import java.awt.Color;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javax.swing.JComboBox;

public class Environment {

    public static int SELECTEDMETHOD = 0;
    public Random random;
    private final Cell[][] grid;
    private final List<Agent> agents;
    private final Prey prey;
    private final int maxIterations;
    private int numPredators;
    private JComboBox jComboBox;
    private int inputLayerSize;
    private int hiddenLayerSize;
    private int outputLayerSize;

    //MORE ATTRIBUTES?
    public Environment(
            int size,
            int maxIterations,
            double probPreyRests,
            int numPredators,
            int predatorsNumInputs,
            int predatorsNumHiddenLayers,
            int predatorsNumOutputs) {

        this.maxIterations = maxIterations;
        this.numPredators = numPredators;
        grid = new Cell[size][size];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }

        //THE PREY AND THE PREDATORS ARE CREATED BUT THEY ARE NOT PLACED IN ANY CELL.
        //THEY ARE PLACED IN AN INITIAL CELL IN THE BEGINNING OF EACH SIMULATION
        //(SEE METHOD initializeAgentsPositions).
        prey = new Prey(null, probPreyRests);
        agents = new LinkedList<>();

        switch (SELECTEDMETHOD) {
            case 0: //PredatorsRandom
                for (int i = 0; i < numPredators; i++) {
                    agents.add(new PredatorRandom(null, Color.BLUE));
                }

                break;
            case 1: //PredatorsGreedy
                for (int i = 0; i < numPredators; i++) {
                    agents.add(new PredatorGreedy(null, Color.GREEN));
                }

                break;
            case 3: // Neural network
                    for(int i = 0;i< this.numPredators;i++){
                        agents.add(new PredatorNeuralNetwork(null, predatorsNumInputs,predatorsNumHiddenLayers,predatorsNumOutputs));
                    }
                break;
            default:
                break;
        }

        this.random = new Random();
    }

    /*
    //THIS METHOD SHOULD BE CALLED IN THE METHOD computeFitness BEFORE A
    //ALL THE SIMULATIONS START.
    public void setPredatorsNeuralNetworkWeights(double[] weights) {
        for (PredatorNeuralNetwork predator : (PredatorNeuralNetwork) agents) {
            predatorNeuralNetwork.setWeights(weights);
        }
    }*/
    //THIS METHOD SHOULD BE CALLED RIGHT BEFORE EACH CALL TO METHOD simulate (SEE BELOW).
    //THAT IS, IT MUST BE CALLED RIGHT BEFORE EACH SIMULATION (.
    public void initializeAgentsPositions(int seed) {
        //THE NEXT LINE MEANS THAT ALL INDIVIDUALS WILL BE EVALUATED WITH THE SAME
        //ENVIRONEMNT STARTING POSITIONS.
        random.setSeed(seed);
        //reset cells
        prey.setCell(null);
        for (Agent agent : agents) {
            agent.setCell(null);
        }

        prey.setCell(getCell(random.nextInt(grid.length), random.nextInt(grid.length)));

        for (Agent agent : agents) {
            do {
                Cell cell = getCell(
                        random.nextInt(grid.length), random.nextInt(grid.length));
                if (!cell.hasAgent()) {
                    agent.setCell(cell);
                }
            } while (agent.getCell() == null);
        }

    }

    //MAKES A SIMULATION OF THE ENVIRONMENT. THE AGENTS START IN THE POSITIONS
    //WHERE THEY WHERE PLACED IN METHOD initializeAgentsPositions.
    public int simulate() {
        int numIterations = 0;
        for (int i = 0; i < maxIterations; i++) {
            prey.act(this);
            fireUpdatedEnvironment();

            for (Agent agent : agents) {
                if (distanceBetweenTwoCells(agent.getCell(), prey.getCell()) != 1) {
                    if(!checkMoveConditions(agent)){
                        continue;
                    }
                    agent.act(this);
                    fireUpdatedEnvironment();
                }
            }
            if(computePredatorsPreyDistanceSum() == 4){
                return numIterations;
            }
            numIterations++;
        }
        return computePredatorsPreyDistanceSum() * 5;
    }
    
    private boolean checkMoveConditions(Agent agent){
        boolean canMove = true;
        if(agent.getCell().getLine() == 9 && agent.getCell().getColumn() == prey.getCell().getColumn() && prey.getCell().getLine() == 0){
            canMove = false;
        }else if(agent.getCell().getLine() == 0 && agent.getCell().getColumn() == prey.getCell().getColumn() && prey.getCell().getLine() == 9){
            canMove = false;
        }else if(agent.getCell().getLine() == prey.getCell().getLine() && agent.getCell().getColumn() == 9 && prey.getCell().getColumn()== 0){
            canMove = false;
        }else if(agent.getCell().getLine() == prey.getCell().getLine() && agent.getCell().getColumn() == 0 && prey.getCell().getColumn() == 9){
            canMove = false;
        }
        return canMove;
    }

    public int distanceBetweenTwoCells(Cell cell, Cell another) {
        return Math.abs(cell.getLine() - another.getLine()) + Math.abs(cell.getColumn() - another.getColumn());
    }

    public int distanceBetweenTwoCellsGreedy(Cell cell, Cell another) {

        return Math.min(Math.abs(cell.getColumn()-another.getColumn()), getSize()-1-Math.abs(cell.getColumn()-another.getColumn()))
                +  Math.min(Math.abs(cell.getLine()-another.getLine()), getSize()-1-Math.abs(cell.getLine()-another.getLine()));

    }

    public int predatorGreedyDistancePrey(PredatorGreedy predator) {
        return distanceBetweenTwoCellsGreedy(predator.getCell(), prey.getCell());
    }

    //COMPUTES THE SUM OF THE (SMALLEST) DISTANCES OF ALL THE PREDATORS TO THE PREY.
    //IT TAKES INTO ACCOUNT THAT THE ENVIRONMENT IS TOROIDAL.
    public int computePredatorsPreyDistanceSum() {
        int distance = 0;
        /*for(int i=0;i<predatorsGreedy.size();i++){
            distance += predatorDistancePrey(predatorsGreedy.get(i));
        }*/
        return distance;
    }

    public int getSize() {
        return grid.length;
    }

    public Prey getPrey() {
        return prey;
    }

    public List getAgents() {
        return agents;
    }

    public final Cell getCell(int line, int column) {
        return grid[line][column];
    }

    //THIS METHOD *MAY* BE USED BY THE PREY IF YOU WANT TO SELECT THE RANDOM
    //PREY MOVEMENT JUST BETWEEN FREE SORROUNDING CELLS.
    public List<Cell> getFreeSorroundingCells(Cell cell) {
        List<Cell> freeCells = new LinkedList<>();
        if (!getNorthCell(cell).hasAgent()) {
            freeCells.add(getNorthCell(cell));
        }
        if (!getSouthCell(cell).hasAgent()) {
            freeCells.add(getSouthCell(cell));
        }
        if (!getEastCell(cell).hasAgent()) {
            freeCells.add(getEastCell(cell));
        }
        if (!getWestCell(cell).hasAgent()) {
            freeCells.add(getWestCell(cell));
        }
        return freeCells;
    }

    public Color getCellColor(int line, int column) {
        return grid[line][column].getColor();
    }

    public Cell getNorthCell(Cell cell) {
        return cell.getLine() > 0 ? grid[cell.getLine() - 1][cell.getColumn()] : grid[grid.length - 1][cell.getColumn()];
    }

    public Cell getSouthCell(Cell cell) {
        return cell.getLine() < grid.length - 1 ? grid[cell.getLine() + 1][cell.getColumn()] : grid[0][cell.getColumn()];
    }

    public Cell getWestCell(Cell cell) {
        return cell.getColumn() > 0 ? grid[cell.getLine()][cell.getColumn() - 1] : grid[cell.getLine()][grid.length - 1];
    }

    public Cell getEastCell(Cell cell) {
        return cell.getColumn() < grid.length - 1 ? grid[cell.getLine()][cell.getColumn() + 1] : grid[cell.getLine()][0];
    }

    //listeners
    private final ArrayList<EnvironmentListener> listeners = new ArrayList<>();

    public synchronized void addEnvironmentListener(EnvironmentListener l) {
        if (!listeners.contains(l)) {
            listeners.add(l);
        }
    }

    public synchronized void removeEnvironmentListener(EnvironmentListener l) {
        listeners.remove(l);
    }

    public void fireUpdatedEnvironment() {
        for (EnvironmentListener listener : listeners) {
            listener.environmentUpdated();
        }
    }

    public void getMinimumDistanceToPrey(PredatorGreedy predator, Prey prey) {

    }
    
    //THIS METHOD SHOULD BE CALLED IN METHOD COMPUTE FITNESS BEFORE ALL THE SIMULATIONS
    void setPredatorsWeights(double[] genome) {
       
    }
}
