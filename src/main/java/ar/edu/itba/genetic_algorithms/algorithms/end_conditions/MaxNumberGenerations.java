package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

/**
 * Class implementing the Max. Number of Generations ending condition strategy.
 */
public class MaxNumberGenerations implements EndingCondition {

    /**
     * The amount of generations.
     */
    private final int maxGenerations;

    /**
     * Constructor.
     *
     * @param maxGenerations The amount of generations.
     */
    public MaxNumberGenerations(int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return population.getGeneration() >= maxGenerations;
    }
}
