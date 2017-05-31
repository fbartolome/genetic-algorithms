package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import com.google.common.collect.Multimap;

import java.util.Map;

public abstract class AccumulatedSelectionMethod implements SelectionStrategy {

    protected Chromosome selectChromosomeOnAccumulatedFitnessProbability(Double rand,
                                                                         Multimap<Individual, Double> individualList) {
        for (Map.Entry<Individual, Double> e : individualList.entries()) {
            if (e.getValue() > rand) {
                return e.getKey().getChromosome();
            }
        }
        return null;
    }
}
