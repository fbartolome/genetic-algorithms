package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.ChromosomePair;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.List;

public interface SelectionMethod {

    /**
     * Selects the pairs of individuals for crossing.
     *
     * @param population actual population in the genetic algorithm.
     * @param k number of selected parents.
     * @return pairs of individuals for crossing.
     */
    List<ChromosomePair> select(Population population, int k);

}
