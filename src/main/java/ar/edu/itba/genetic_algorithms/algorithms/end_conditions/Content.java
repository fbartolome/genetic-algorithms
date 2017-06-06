package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

/**
 * Class implementing the Content ending condition strategy.
 */
public class Content implements EndingCondition {

    /**
     * The amount of generations.
     */
    private final int generations;

    /**
     * Constructor.
     *
     * @param generations The amount of generations.
     */
    public Content(int generations) {
        this.generations = generations;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return sameFitnessCount(population, population.bestIndividual().getFitness()) >= generations;
    }

    /**
     * Counts the fitness across generations.
     *
     * @param population The actual population.
     * @param fitness    The fitness.
     * @return The count.
     */
    private int sameFitnessCount(Population population, double fitness) {
        if (population == null) {
            return -1;
        } else if (population.bestIndividual().getFitness() != fitness) {
            return 0;
        } else {
            return 1 + sameFitnessCount(population.getPreviousPopulation(), fitness); // TODO: avoid recursive.
        }
    }
}
