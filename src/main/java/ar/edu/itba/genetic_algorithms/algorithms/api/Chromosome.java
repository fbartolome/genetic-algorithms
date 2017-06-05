package ar.edu.itba.genetic_algorithms.algorithms.api;

import java.util.stream.IntStream;

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

    @Override
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Chromosome 1").append(": ").append(genes[0].toString());
        IntStream.range(1, genes.length).forEach(genNumber -> stringBuilder.append("\nChromosome ")
                .append(genNumber + 1)
                .append(": ").append(genes[genNumber].toString()));
        return stringBuilder.toString();
    }
}
