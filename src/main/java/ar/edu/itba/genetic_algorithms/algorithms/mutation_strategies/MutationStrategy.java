package ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;

public interface MutationStrategy {

    /**
     * Mutates a {@link Chromosome}.
     *
     * @param originalChromosome     The original chromosome for mutation.
     * @param alleleContainerWrapper The {@link AlleleContainerWrapper} with possible alleles.
     */
    void mutate(Chromosome originalChromosome, AlleleContainerWrapper alleleContainerWrapper);

}
