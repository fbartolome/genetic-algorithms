package ar.edu.itba.genetic_algorithms.algorithms.api;

/**
 * This interface defines a method to create an {@link Individual} from a {@link Chromosome}.
 */
public interface IndividualCreator {

    /**
     * Creates an {@link Individual} from a {@link Chromosome}.
     *
     * @param chromosome The {@link Chromosome} from which the {@link Individual} is created.
     * @return The created {@link Individual}.
     */
    Individual create(Chromosome chromosome);
}
