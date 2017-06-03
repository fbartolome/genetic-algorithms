package ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.engine.ChromosomePair;

public interface CrossoverStrategy {

    /**
     * Crosses two chromosomes, generating two new ones.
     *
     * @param parents pair of chromosomes that will be crossed.
     * @return new pair of chromosomes generated by the crossover.
     */
    ChromosomePair crossover(ChromosomePair parents);

}