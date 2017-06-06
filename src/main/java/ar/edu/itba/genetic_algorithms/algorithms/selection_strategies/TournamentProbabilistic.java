package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class implements the Tournament Probabilistic selection method.
 */
public class TournamentProbabilistic implements SelectionStrategy {

    @Override
    public List<Chromosome> select(Population population, int k) {
        final List<Individual> individuals = population.getIndividuals();
        return IntStream.range(0, k)
                .parallel()
                .mapToObj(i -> generateRandomPair(individuals).getWinner().getChromosome())
                .collect(Collectors.toList());
    }

    /**
     * Creates a random {@link IndividualsPair} getting {@link Individual}s from the given list of {@code individuals}.
     *
     * @param individuals The list of {@link Individual}.
     * @return A random {@link IndividualsPair}.
     */
    private static IndividualsPair generateRandomPair(List<Individual> individuals) {
        return new IndividualsPair(individuals.get(new Random().nextInt(individuals.size())),
                individuals.get(new Random().nextInt(individuals.size())));
    }

    /**
     * Class that wraps two {@link Individual} that will compete in the tournament.
     */
    private static final class IndividualsPair {

        /**
         * The first {@link Individual}.
         */
        private final Individual first;

        /**
         * The second {@link Individual}.
         */
        private final Individual second;

        /**
         * Constructor.
         *
         * @param first  The first {@link Individual}.
         * @param second The second {@link Individual}.
         */
        private IndividualsPair(Individual first, Individual second) {
            this.first = first;
            this.second = second;
        }

        /**
         * @return The first {@link Individual}.
         */
        private Individual getFirst() {
            return first;
        }

        /**
         * @return The first {@link Individual}.
         */
        private Individual getSecond() {
            return second;
        }

        /**
         * @return The {@link Individual} with best fitness.
         */
        private Individual getBest() {
            return first.getFitness() > second.getFitness() ? first : second;
        }

        /**
         * @return The {@link Individual} with worst fitness.
         */
        private Individual getWorst() {
            return first.getFitness() < second.getFitness() ? first : second;
        }

        /**
         * @return The winner of the competition.
         */
        private Individual getWinner() {
            return new Random().nextDouble() < 0.75 ? getBest() : getWorst();
        }
    }
}
