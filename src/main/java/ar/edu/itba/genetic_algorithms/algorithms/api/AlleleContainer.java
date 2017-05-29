package ar.edu.itba.genetic_algorithms.algorithms.api;

import java.util.Random;

/**
 * Interface defining a method for getting an allele, optionally based on a given random.
 */
public interface AlleleContainer {

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
}
