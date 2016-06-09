package pursuitDomain;

import ga.RealVectorIndividual;

public class PredatorIndividual extends RealVectorIndividual<PursuitDomainProblem, PredatorIndividual> {

    public PredatorIndividual(PursuitDomainProblem problem, int size) {
        super(problem, size);
    }

    public PredatorIndividual(PredatorIndividual original) {
        super(original);
    }

    @Override
    public double computeFitness() {    
        Environment e = problem.getEnvironment();
        e.setPredatorsWeights(genome);
        fitness=0;
        for(int i = 0; i<problem.getNumEvironmentSimulations(); i++){
            e.initializeAgentsPositions(i);
            fitness += e.simulate();
        }
        fitness = fitness / problem.getNumEvironmentSimulations();
        return fitness;
    }

    public double[] getGenome(){
        return genome;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\nfitness: ");
        sb.append(fitness);
        return sb.toString();
    }

    /**
     *
     * @param i
     * @return 1 if this object is BETTER than i, -1 if it is WORST than I and
     * 0, otherwise.
     */
    @Override
    public int compareTo(PredatorIndividual i) {
         return (this.fitness == i.getFitness())? 0 : (this.fitness > i.getFitness())? 1 : -1;
    }

    @Override
    public PredatorIndividual clone() {
        return new PredatorIndividual(this);
    }
}
