package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import com.google.common.collect.Multimap;

import java.util.Optional;

/**
 * Abstract class implementing a method for selecting a {@link Chromosome} based on the accumulated fitness,
 * in a random way.
 */
public abstract class AccumulatedSelectionMethod implements SelectionStrategy {

    /**
     * Selects a {@link Chromosome} based on the accumulated fitness, in a random way.
     *
     * @param rand           The probability.
     * @param individualList A {@link Multimap} containing {@link Individual}s and their accumulated fitness.
     * @return The first {@link Chromosome} matching.
     */
    protected Chromosome selectChromosomeOnAccumulatedFitnessProbability(Double rand,
                                                                         Multimap<Individual, Double> individualList) {
        return individualList.entries()
                .parallelStream()
                .filter(each -> each.getValue() > rand)
                .map(each -> each.getKey().getChromosome())
                .findFirst()
                .orElse(null);
    }
}
