package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

/**
 * Class implementing the Optimum ending condition strategy.
 */
public class Optimum implements EndingCondition {

    /**
     * The optimum fitness.
     */
    private final double optimumFitness;

    /**
     * Constructor.
     *
     * @param optimumFitness The optimum fitness.
     */
    public Optimum(double optimumFitness) {
        this.optimumFitness = optimumFitness;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return population.bestIndividual().getFitness() >= optimumFitness;
    }
}
