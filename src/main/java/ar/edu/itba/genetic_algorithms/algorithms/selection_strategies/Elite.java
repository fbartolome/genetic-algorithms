package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class implements the Elite selection method.
 */
public class Elite implements SelectionStrategy {

    @Override
    public List<Chromosome> select(Population population, int k) {
        return population.getSortedIndividualsFromBestToWorst().stream()
                .limit((long) k)
                .parallel()
                .map(Individual::getChromosome)
                .parallel()
                .collect(Collectors.toList());
    }

}
