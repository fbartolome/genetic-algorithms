package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import com.google.common.collect.Multimap;

import java.util.Optional;

public abstract class AccumulatedSelectionMethod implements SelectionStrategy {

    protected Chromosome selectChromosomeOnAccumulatedFitnessProbability(Double rand,
                                                                         Multimap<Individual, Double> individualList) {
        Optional<Chromosome> chromosomeOptional = individualList.entries()
                .parallelStream()
                .filter(each -> each.getValue() > rand)
                .map(each -> each.getKey().getChromosome()).findFirst();

        return chromosomeOptional.isPresent() ? chromosomeOptional.get() : null;
    }
}
