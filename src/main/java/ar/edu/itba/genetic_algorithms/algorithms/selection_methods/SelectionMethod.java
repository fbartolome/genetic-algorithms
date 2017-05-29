package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.List;

public interface SelectionMethod {

    /**
     * Selects the individuals for crossing.
     *
     * @param population actual population in the genetic algorithm.
     * @param k number of selected parents.
     * @return individuals for crossing.
     */
    List<Chromosome> select(Population population, int k);

}
