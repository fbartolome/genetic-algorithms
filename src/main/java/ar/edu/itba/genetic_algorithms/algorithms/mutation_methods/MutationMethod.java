package ar.edu.itba.genetic_algorithms.algorithms.mutation_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;

public interface MutationMethod {

    /**
     * Mutates a chromosome.
     *
     * @param originalChromosome the original chromosome for mutation.
     * @return mutated chromosome.
     */
    Chromosome mutate(Chromosome originalChromosome);

}
