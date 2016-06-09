package pursuitDomain;

import ga.Problem;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class PursuitDomainProblem implements Problem<PredatorIndividual> {

    public static int NUM_PREDATOR_OUTPUTS = 2;
    final private int environmentSize;
    final private int maxIterations;
    final private double probPreyRest;
    final private double numPredators;
    final private int predatorsNumInputs;
    final private int predatorsNumHiddenUnits;
    final private int predatorNumOutputs;
    final private int numEnvironmentRuns;

    final private Environment environment;

    public PursuitDomainProblem(
            int environmentSize,
            int maxIterations,
            double probPreyRests,
            int numPredators,
            int numPredatorHiddenUnits,
            int numEnvironmentRuns) {
        this.environmentSize = environmentSize;
        this.maxIterations = maxIterations;
        this.probPreyRest = probPreyRests;
        this.numPredators = numPredators;
        this.predatorsNumInputs = 9; 
        this.predatorsNumHiddenUnits = numPredatorHiddenUnits;
        this.predatorNumOutputs = NUM_PREDATOR_OUTPUTS;
        this.numEnvironmentRuns = numEnvironmentRuns;

        environment = new Environment(
                environmentSize,
                maxIterations,
                probPreyRests,
                numPredators,
                predatorsNumInputs,
                numPredatorHiddenUnits,
                predatorNumOutputs);
    }

    @Override
    public PredatorIndividual getNewIndividual() {
        int genomeSize = ((predatorsNumInputs + 1 )* predatorsNumHiddenUnits) + ((predatorsNumHiddenUnits + 1) * predatorNumOutputs);
        return new PredatorIndividual(this, genomeSize);
    }

    public Environment getEnvironment() {
        return environment;
    }
    
    public int getNumEvironmentSimulations(){
        return numEnvironmentRuns;
    }

    public static PursuitDomainProblem buildProblemFromFile(File file) throws IOException {
        java.util.Scanner f = new java.util.Scanner(file);

        List<String> lines = new LinkedList<>();

        while (f.hasNextLine()) {
            String s = f.nextLine();
            if (!s.equals("") && !s.startsWith("//")) {
                lines.add(s);
            }
        }        
        
        List<String> parametersValues = new LinkedList<>();
        for (String line : lines) {
            String[] tokens = line.split(":");
            parametersValues.add(tokens[1].trim());
        }
                
        int environmentSize = Integer.parseInt(parametersValues.get(0));
        int maxIterations = Integer.parseInt(parametersValues.get(1));
        double probPreyRests = Double.parseDouble(parametersValues.get(2));
        int numPredators = Integer.parseInt(parametersValues.get(3));
        int numHiddenUnits = Integer.parseInt(parametersValues.get(4));
        int numEnvironmentRuns = Integer.parseInt(parametersValues.get(5));

        return new PursuitDomainProblem(
                environmentSize,
                maxIterations,
                probPreyRests,
                numPredators,
                numHiddenUnits,
                numEnvironmentRuns);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Environment size: ");
        sb.append(environmentSize);
        sb.append("\n");
        sb.append("Maximum number of iterations: ");
        sb.append(maxIterations);
        sb.append("\n");
        sb.append("Prey rest probability: ");
        sb.append(probPreyRest);
        sb.append("\n");
        sb.append("Number of predators: ");
        sb.append(numPredators);
        sb.append("\n");
        sb.append("Number of predator inputs: ");
        sb.append(predatorsNumInputs);
        sb.append("\n");
        sb.append("Number of hidden units: ");
        sb.append(predatorsNumHiddenUnits);
        sb.append("\n");
        sb.append("Number of predator outputs: ");
        sb.append(NUM_PREDATOR_OUTPUTS);
        sb.append("\n");
        sb.append("Number of environment runs: ");
        sb.append(numEnvironmentRuns);
        return sb.toString();
    }

}
