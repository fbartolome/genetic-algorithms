package ar.edu.itba.genetic_algorithms.algorithms.api;

import java.util.List;

/**
 * This interface defines a method for getting alleles based on the position of the gene
 * in a defined {@link Chromosome}.
 */
public interface AlleleContainerWrapper {

    /**
     * Returns a specific allele (that one with the given allele number),
     * according to the given {@code positionInChromosome}.
     *
     * @param alleleNumber         The number of the allele.
     * @param positionInChromosome The position of the gen in a defined {@link Chromosome}.
     * @return The specified allele, or {@code null} if no allele exists with the given {@code alleleNumber}
     * in the container of alleles for the gen positioned in the given {@code positionInChromosome}.
     * @apiNote For infinite amount of alleles container, define a finite subset of alleles,
     * being those sorted and returned by this method.
     */
    Object getSpecificAllele(int alleleNumber, int positionInChromosome);

    /**
     * Returns a random allele based on the given {@code positionInChromosome}.
     *
     * @param positionInChromosome The position of the gen in a defined {@link Chromosome}.
     * @return The random allele.
     */
    Object getRandomAllele(int positionInChromosome);

    /**
     * Returns the amount of genes the wrapper support (i.e the amount of {@link AlleleContainer}).
     *
     * @return The amount of genes the wrapper support (i.e the amount of {@link AlleleContainer}).
     */
    int getAmountOfGenes();

    /**
     * Returns the amount of possible alleles held in the container of alleles
     * of the given {@code positionInChromosome}.
     *
     * @param positionInChromosome The position of the gen in a defined {@link Chromosome}.
     * @return The amount of possible alleles held in the container of alleles
     * of the given {@code positionInChromosome}.
     * @apiNote For infinite amount of alleles container, define a finite subset of alleles,
     * being those sorted, and the amount of them returned by this method.
     */
    int getAmountOfAlleles(int positionInChromosome);

    /**
     * @return A {@link List} of alleles numbers of those alleles held in the container
     * of the given {@code positionInChromosome}.
     * @apiNote For infinite amount of alleles container, define a finite subset of alleles,
     * being those sorted and returned by this method.
     */
    List<Integer> getAllelesNumbers(int positionInChromosome);
}
