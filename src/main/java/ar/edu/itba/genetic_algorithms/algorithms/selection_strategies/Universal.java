package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class Universal extends AccumulatedSelectionMethod {

    @Override
    public List<Chromosome> select(Population population, int k) {
        Multimap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        return IntStream.range(1, k + 1)
                .parallel()
                .mapToDouble(j -> (Math.random() + (double) j - (double) 1) / ((double) k))
                .parallel()
                .mapToObj(d -> selectChromosomeOnAccumulatedFitnessProbability(d, populationRelativeFitnesses))
                .parallel()
                .collect(Collectors.toList());
    }


}
