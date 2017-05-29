package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

public class Optimum implements EndingCondition {

    private final double optimumFitness;

    public Optimum(double optimumFitness){
        this.optimumFitness = optimumFitness;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return population.bestIndividual().getFitness() >= optimumFitness;
    }
}
