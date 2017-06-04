package ar.edu.itba.genetic_algorithms.algorithms.api;

import java.util.List;
import java.util.Random;

/**
 * Interface defining a method for getting an allele, optionally based on a given random.
 */
public interface AlleleContainer {

    /**
     * Returns a specific allele (that one with the given allele number).
     *
     * @param alleleNumber The number of the allele.
     * @return The specific allele, or {@code null} if no allele exists with the given {@code alleleNumber}.
     * @apiNote For infinite amount of alleles, define a finite subset of alleles,
     * being those sorted and returned by this method.
     */
    Object getSpecificAllele(int alleleNumber);

    /**
     * Returns an allele based on the given {@code random}.
     *
     * @param random {@link Random} class for getting a random number.
     * @return The allele if it exists, or {@code null} otherwise.
     */
    Object getAllele(Random random);

    /**
     * @return An allele if it exists, or {@code null} otherwise.
     */
    Object getAllele();

    /**
     * Returns the amount of possible alleles held.
     *
     * @return The amount of possible alleles held.
     * @apiNote For infinite amount of alleles, define a finite subset of alleles,
     * being those sorted, and the amount of them returned by this method.
     */
    int getAmountOfAlleles();

    /**
     * @return A {@link List} of alleles numbers of those alleles held in the container.
     * @apiNote For infinite amount of alleles, define a finite subset of alleles,
     * being those sorted and returned by this method.
     */
    List<Integer> getAllelesNumbers();


}
