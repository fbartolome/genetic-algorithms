package ar.edu.itba.genetic_algorithms.algorithms.api;

/**
 * Represents an individual (basic unit of a genetic algorithm).
 * Each individual is a possible solution.
 */
public interface Individual {

    /**
     * @return The fitness of this individual (i.e how good this individual is).
     */
    double getFitness();

    /**
     * @return This individual's chromosome.
     */
    Chromosome getChromosome();


    default String getChromosomeStringRepresentation() {
        return getChromosome().toString();
    }


}
