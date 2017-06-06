package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

/**
 * Class implementing the Structure ending condition strategy.
 */
public class Structure implements EndingCondition {

    /**
     * The tolerance.
     */
    private final double tolerance;

    /**
     * Constructor.
     *
     * @param tolerance The tolerance.
     */
    public Structure(double tolerance) {
        if (tolerance > 1 || tolerance < 0)
            throw new IllegalArgumentException("Tolerance should be between 0 and 1.");
        this.tolerance = tolerance;
    }

    @Override
    public boolean isSatisfied(Population population) {
        if (population.getPreviousPopulation() == null)
            return false;

        final int sameIndividuals = (int) population.getIndividuals().stream()
                .parallel()
                // Get those new individuals that exists in the previous generation.
                .filter(newIndividual -> population.getPreviousPopulation().getIndividuals().contains(newIndividual))
                .count();

        return tolerance < sameIndividuals / population.getPopulationSize();
    }
}
