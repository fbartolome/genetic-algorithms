package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.Population;

public interface EndingCondition {

    /**
     * Checks if the end condition of a genetic algorithm is satisfied.
     *
     * @param population actual population in the genetic algorithm.
     * @return true if the condition is satisfied, false otherwise.
     */
    boolean isSatified(Population population);

}
