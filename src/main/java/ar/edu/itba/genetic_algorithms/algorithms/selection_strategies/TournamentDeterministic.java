package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class implements the Tournament Deterministic selection method.
 */
public class TournamentDeterministic implements SelectionStrategy {

    /**
     * The "m" parameter for tournament deterministic (i.e amount of random {@link Individual}s).
     */
    private final int m;

    /**
     * Constructor.
     *
     * @param m The "m" parameter for tournament deterministic (i.e amount of random {@link Individual}s).
     */
    public TournamentDeterministic(int m) {
        this.m = m;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        final List<Individual> individuals = population.getIndividuals();

        final int size = population.getPopulationSize();
        return IntStream.range(0, k)
                .parallel()
                .mapToObj(j -> new Random().ints((long) m, 0, size)
                        .mapToObj(individuals::get)
                        .max(((o1, o2) -> Double.compare(o1.getFitness(), o2.getFitness())))
                        .orElseThrow(() -> new IllegalArgumentException("Empty individuals lists."))
                        .getChromosome())
                .collect(Collectors.toList());
    }
}
