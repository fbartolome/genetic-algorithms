package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;

/**
 * This class represents the first replacement strategy, which consists of creating a new {@link Population}
 * replacing the list of {@link Individual} from the actual {@link Population}.
 */
public class ReplacementStrategy1 implements ReplacementStrategy {
    @Override
    public Population replace(Population actualPopulation, List<Individual> offspring) {
        if (offspring.size() != actualPopulation.getPopulationSize()) {
            throw new IllegalArgumentException("This replacement strategy requires " +
                    "a list of offspring of the same size of the population");
        }
        return new Population(offspring, actualPopulation);
    }

}
