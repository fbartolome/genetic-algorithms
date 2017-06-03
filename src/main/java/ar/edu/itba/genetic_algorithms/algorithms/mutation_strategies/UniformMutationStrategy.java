package ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;

import java.util.Random;

public class UniformMutationStrategy implements MutationStrategy {

    @Override
    public void mutate(Chromosome originalChromosome, AlleleContainerWrapper alleleContainerWrapper) {
        Random r = new Random();
        int gene = r.nextInt(originalChromosome.getGenes().length);

        originalChromosome.getGenes()[gene] = alleleContainerWrapper.getRandomAllele(gene);
    }
}
