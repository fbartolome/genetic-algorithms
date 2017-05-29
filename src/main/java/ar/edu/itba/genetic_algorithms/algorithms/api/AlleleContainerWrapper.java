package ar.edu.itba.genetic_algorithms.algorithms.api;

/**
 * This interface defines a method for getting alleles based on the position of the gene
 * in a defined {@link Chromosome}.
 */
public interface AlleleContainerWrapper {

    /**
     * Returns a random allele based on the given {@code positionInChromosome}.
     *
     * @param positionInChromosome The position of the gen in a defined {@link Chromosome}.
     * @return The random allele.
     */
    Object getRandomAllele(int positionInChromosome);
}
