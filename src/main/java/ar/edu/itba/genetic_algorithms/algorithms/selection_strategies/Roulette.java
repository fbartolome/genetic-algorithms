package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * This class implements the Roulette selection method.
 */
public class Roulette extends AccumulatedSelectionMethod {

    @Override
    public List<Chromosome> select(Population population, int k) {

        final Multimap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        return IntStream.range(0, k)
                .parallel()
                .mapToObj(each ->
                        selectChromosomeOnAccumulatedFitnessProbability(new Random().nextDouble(),
                                populationRelativeFitnesses))
                .collect(Collectors.toList());

    }


}
