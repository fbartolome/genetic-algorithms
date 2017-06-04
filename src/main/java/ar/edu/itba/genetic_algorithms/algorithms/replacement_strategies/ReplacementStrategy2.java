package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class represents the second replacement strategy, which consists of creating a new {@link Population}
 * replacing only a certain amount of {@link Individual}s
 * (those not replaced continue to exist in the new {@link Population}).
 */
public class ReplacementStrategy2 implements ReplacementStrategy {

    private final SelectionStrategy oldPopulationSelectionStrategy;

    public ReplacementStrategy2(SelectionStrategy oldPopulationSelectionStrategy) {
        this.oldPopulationSelectionStrategy = oldPopulationSelectionStrategy;
    }

    @Override
    public Population replace(Population actualPopulation, List<Individual> offspring) {
        if (offspring.size() > actualPopulation.getPopulationSize()) {
            throw new IllegalArgumentException("This replacement strategy requires " +
                    "a list of offspring whose size is smaller or equal than the size of the population");
        }
        return new Population(Stream.concat(offspring.stream(),
                oldPopulationSelectionStrategy
                        .select(actualPopulation, actualPopulation.getPopulationSize() - offspring.size())
                        .stream()
                        .parallel()
                        .map(chromosome -> actualPopulation.getCreator().create(chromosome)))
                .collect(Collectors.toList()), actualPopulation);
    }

}
