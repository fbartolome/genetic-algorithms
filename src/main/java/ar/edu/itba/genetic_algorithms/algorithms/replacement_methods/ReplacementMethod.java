package ar.edu.itba.genetic_algorithms.algorithms.replacement_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;

public interface ReplacementMethod {

    /**
     * Replaces individuals from the population with new individuals.
     *
     * @param actualPopulation the actual population.
     * @param offsprings       the new individuals.
     * @return individuals of the new generation.
     */
    public List<Individual> replace(Population actualPopulation, List<Individual> offsprings);

}
