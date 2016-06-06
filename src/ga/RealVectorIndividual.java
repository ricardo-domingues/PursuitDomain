package ga;

import java.util.Random;

public abstract class RealVectorIndividual <P extends Problem, I extends RealVectorIndividual> extends Individual<P, I>{

    //TODO: GENOME DEFINITION;
    protected double[] genome;
    
    public RealVectorIndividual(P problem, int size) {
        super(problem);
        genome = new double[size];
        for (int i = 0; i < genome.length; i++) {
            genome[i] = GeneticAlgorithm.random.nextDouble() * 2 - 1;
        }
    }

    public RealVectorIndividual(RealVectorIndividual<P, I> original) {
        super(original);
        this.genome = new double[original.genome.length];
        System.arraycopy(original.genome, 0, genome, 0, genome.length);
    }
    
    @Override
    public int getNumGenes() {
        return genome.length;
    }
    
    public double getGene(int index) {
       return genome[index];
    }
    
    public void setGene(int index, double newValue) {
        genome[index] = newValue;
    }

    @Override
    public void swapGenes(RealVectorIndividual other, int index) {
        double aux = genome[index];
        genome[index] = other.genome[index];
        other.genome[index] = aux;
    }
}
