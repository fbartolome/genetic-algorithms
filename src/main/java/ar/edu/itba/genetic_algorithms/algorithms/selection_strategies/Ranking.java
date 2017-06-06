package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class implements the Ranking selection method.
 */
public class Ranking extends AccumulatedSelectionMethod {

    @Override
    public List<Chromosome> select(Population population, int k) {

        final List<Individual> individuals = population.getSortedIndividualsFromWorstToBest();
        final int populationSize = population.getPopulationSize();
        final int sum = (populationSize * (populationSize + 1)) / 2;

        final Multimap<Individual, Double> accumulatedProbabilities = ArrayListMultimap.create();
        double prevValue = 0;
        for (int i = 0; i < populationSize; i++) {
            prevValue += ((double) (i + 1) / sum);
            accumulatedProbabilities.put(individuals.get(i), prevValue);
        }

        return IntStream.range(0, k)
                .parallel()
                .mapToObj(i ->
                        selectChromosomeOnAccumulatedFitnessProbability(new Random().nextDouble(),
                                accumulatedProbabilities)).collect(Collectors.toList());

    }
}
