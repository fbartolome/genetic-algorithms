package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReplacementStrategy3 implements ReplacementStrategy {

    private final SelectionStrategy oldPopulationSelectionStrategy;

    private final SelectionStrategy newPopulationSelectionStrategy;


    public ReplacementStrategy3(SelectionStrategy oldPopulationSelectionStrategy,
                                SelectionStrategy newPopulationSelectionStrategy) {
        this.oldPopulationSelectionStrategy = oldPopulationSelectionStrategy;
        this.newPopulationSelectionStrategy = newPopulationSelectionStrategy;
    }

    @Override
    public Population replace(Population actualPopulation, List<Individual> offspring) {
        if (offspring.size() > actualPopulation.getPopulationSize()) {
            throw new IllegalArgumentException("This replacement strategy requires " +
                    "a list of offspring whose size is smaller or equal than the size of the population");
        }
        final int k = offspring.size();
        return Stream.concat(
                /* Previous generation chosen individuals */
                oldPopulationSelectionStrategy
                        .select(actualPopulation, actualPopulation.getPopulationSize() - k)
                        .stream()
                        .map(chromosome -> actualPopulation.getCreator().create(chromosome)),
                /* Previous generation and offspring individuals */
                newPopulationSelectionStrategy
                        .select(/*
                                 * Aux Population created for selecting Individuals
                                 * from the previous population and the list of offspring
                                 */
                                Stream.concat(
                                        actualPopulation.getIndividuals().stream(), offspring.stream())
                                        // Collect individuals in a container
                                        .collect(new IndividualsContainerCollector())
                                        .createNewPopulation(actualPopulation), // Generates a Population from container
                                k)
                        .stream()
                        .map(chromosome -> actualPopulation.getCreator().create(chromosome)))
                .collect(new IndividualsContainerCollector()) // Collect individuals in a container
                .createNewPopulation(actualPopulation); // Generates a Population from container
    }

    /**
     * Container of {@link Individual} for collecting them.
     */
    private static final class IndividualsContainer {

        /**
         * Holds the collected {@link Individual}.
         */
        private final List<Individual> individuals;

        /**
         * Constructor.
         */
        private IndividualsContainer() {
            this.individuals = Collections.synchronizedList(new LinkedList<>());
        }

        /**
         * Adds an {@link Individual} to the container.
         *
         * @param individual The {@link Individual} to add.
         */
        private void addIndividual(Individual individual) {
            this.individuals.add(individual);
        }

        /**
         * Combines this container with the given {@code container}.
         *
         * @param container The container to be combined with this one.
         * @return This container (with the other combined with it).
         */
        private IndividualsContainer combine(IndividualsContainer container) {
            this.individuals.addAll(container.individuals);
            return this;
        }

        /**
         * Creates a new {@link Population} using the contained {@link Individual}.
         *
         * @param actualPopulation The {@link Population} from which the new one will be created.
         * @return The new {@link Population}.
         */
        private Population createNewPopulation(Population actualPopulation) {
            return new Population(individuals, actualPopulation);
        }
    }

    /**
     * {@link Collector} to be used for collecting {@link Individual} into {@link IndividualsContainer}.
     */
    private static final class IndividualsContainerCollector
            implements Collector<Individual, IndividualsContainer, IndividualsContainer> {

        @Override
        public Supplier<IndividualsContainer> supplier() {
            return IndividualsContainer::new;
        }

        @Override
        public BiConsumer<IndividualsContainer, Individual> accumulator() {
            return IndividualsContainer::addIndividual;
        }

        @Override
        public BinaryOperator<IndividualsContainer> combiner() {
            return IndividualsContainer::combine;
        }

        @Override
        public Function<IndividualsContainer, IndividualsContainer> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Stream.of(Characteristics.IDENTITY_FINISH, Characteristics.CONCURRENT).collect(Collectors.toSet());
        }
    }
}
