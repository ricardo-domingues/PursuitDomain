package ga.geneticOperators;

import ga.RealVectorIndividual;

public class MutationMUTATION_NAME <I extends RealVectorIndividual> extends Mutation<I> {

   
    public MutationMUTATION_NAME(double probability/*COMPLETE?*/) {
        super(probability);
        //COMPLETE
    }

    @Override
    public void run(I ind) {
        //TODO
    }
    
    @Override
    public String toString(){
        return "Uniform distribution mutation (" + probability /* + COMPLETE?*/;
    }
}