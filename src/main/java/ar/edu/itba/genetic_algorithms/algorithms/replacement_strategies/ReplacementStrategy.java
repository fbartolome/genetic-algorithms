package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;

/**
 * This interface defines a method for creating a new {@link Population}, replacing an old one.
 */
public interface ReplacementStrategy {

    /**
     * Creates a new {@link Population} by replaces {@link Individual}s, from the given {@code actualPopulation},
     * based on the given {@code offspring} list.
     *
     * @param actualPopulation The actual population.
     * @param offspring        The list of new individuals.
     * @return The new {@link Population}.
     */
    Population replace(Population actualPopulation, List<Individual> offspring);

}
