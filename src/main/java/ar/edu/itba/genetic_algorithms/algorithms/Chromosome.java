package ar.edu.itba.genetic_algorithms.algorithms;

/**
 * Represents a chromosome.
 */
public class Chromosome {

    /**
     * The genes that make up the chromosome.
     */
    private final Object[] genes;

    /**
     * Constructor.
     *
     * @param genes The genes that make up the chromosome.
     */
    public Chromosome(Object... genes) {
        this.genes = genes;
    }

    /**
     * @return The genes that make up the chromosome.
     */
    public Object[] getGenes() {
        return genes;
    }
}
