package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the second replacement strategy, which consists of creating a new {@link Population}
 * replacing only a certain amount of {@link Individual}s
 * (those not replaced continue to exist in the new {@link Population}).
 */
public class ReplacementStrategy2 implements ReplacementStrategy {
    @Override
    public Population replace(Population actualPopulation, List<Individual> offspring) {
        if (offspring.size() > actualPopulation.getPopulationSize()) {
            throw new IllegalArgumentException("This replacement strategy requires " +
                    "a list of offspring whose size is smaller or equal than the size of the population");
        }
        return new Population(Stream.concat(offspring.stream(),
                actualPopulation.getIndividuals()
                        .subList(0, actualPopulation.getPopulationSize() - offspring.size()).stream())
                .collect(Collectors.toList()), actualPopulation);
        // TODO: which individuals from the actual population should I keep?
    }

}
