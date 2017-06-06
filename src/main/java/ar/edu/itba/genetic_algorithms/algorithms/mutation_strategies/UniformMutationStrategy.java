package ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;

import java.util.Random;

/**
 * Class implementing the Uniform mutation method.
 */
public class UniformMutationStrategy implements MutationStrategy {

    @Override
    public void mutate(Chromosome originalChromosome, AlleleContainerWrapper alleleContainerWrapper) {
        final int gene = new Random().nextInt(originalChromosome.getGenes().length);
        originalChromosome.getGenes()[gene] = alleleContainerWrapper.getRandomAllele(gene);
    }
}
