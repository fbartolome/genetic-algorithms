package ar.edu.itba.genetic_algorithms.algorithms.mutation_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;

public interface MutationStrategy {

    /**
     * Mutates a chromosome.
     *
     * @param originalChromosome the original chromosome for mutation.
     * @return mutated chromosome.
     */
    Chromosome mutate(Chromosome originalChromosome);

}
