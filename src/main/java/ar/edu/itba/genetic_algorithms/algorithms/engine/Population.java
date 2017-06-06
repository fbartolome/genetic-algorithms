package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.api.IndividualCreator;
import ar.edu.itba.genetic_algorithms.utils.MultimapCollector;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

/**
 * A population is a collection of possible solutions to the problem to be solved.
 */

public class Population {

    /**
     * Individuals of the population.
     */
    private List<Individual> individuals;

    /**
     * Previous generation.
     */
    private Population previousPopulation;

    /**
     * Number of population's generation.
     */
    private final int generation;

    /**
     * Size of the population.
     */
    private final int populationSize;

    /**
     * The {@link IndividualCreator} used for creating the {@link Individual}s of this population.
     */
    private final IndividualCreator creator;

    /**
     * Constructor.
     *
     * @param individuals        The individuals belonging to the population.
     * @param previousPopulation The previous population (from which this new one is created from).
     */
    public Population(List<Individual> individuals, Population previousPopulation, IndividualCreator creator) {
        this.individuals = individuals;
        this.previousPopulation = previousPopulation;
        this.generation = previousPopulation == null ? 1 : previousPopulation.getGeneration() + 1;
        this.populationSize = individuals.size();
        this.creator = creator;
    }

    public Population(List<Individual> individuals, Population previousPopulation) {
        //TODO: if previousPopulation is null
        this(individuals, previousPopulation, previousPopulation.getCreator());
    }


    /**
     * @return The fitness sum of all {@link Individual} of this population.
     */
    public double sumFitness() {
        return individuals.stream().parallel().mapToDouble(Individual::getFitness).parallel().sum();
    }

    /**
     * @return The average fitness of all {@link Individual} of this population.
     */

    public double avgFitness() {
        return sumFitness() / populationSize;
    }

    /**
     * @return The relative fitness of this population.
     */
    public Multimap<Individual, Double> getRelativeFitnesses() {
        final double totalFitness = sumFitness();

        return individuals.stream()
                .parallel()
                .collect(new MultimapCollector<>(Function.identity(),
                        individual -> individual.getFitness() / totalFitness));
    }

    /**
     * @return The accumulated relative fitness of this population.
     */
    public Multimap<Individual, Double> getAccumulatedRelativeFitnesses() {
        return getRelativeFitnesses().entries().stream().sequential() // Collector used is not concurrent and stateful.
                .collect(new AccumulatedRelativeFitnessMapCollector());
    }

    /**
     * @return The best individual of this population,
     * or {@code null} if there is not {@link Individual} in this population.
     */
    public Individual bestIndividual() {
        Optional<Individual> individual = individuals.stream()
                .parallel()
                .max((ind1, ind2) -> Double.compare(ind1.getFitness(), ind2.getFitness()));
        return individual.isPresent() ? individual.get() : null;
    }

    /**
     * @return The best individual of this population,
     * or {@code null} if there is not {@link Individual} in this population.
     */
    public Individual worstIndividual() {
        Optional<Individual> individual = individuals.stream()
                .max((ind1, ind2) -> Double.compare(ind1.getFitness(), ind2.getFitness()));
        return individual.isPresent() ? individual.get() : null;
    }

    /**
     * @return The fitness of the best {@link Individual}, or -1 if there is not {@link Individual} in this population.
     */
    public double bestFitness() {
        final Individual individual = bestIndividual();
        return individual == null ? -1 : individual.getFitness();
    }

    /**
     * @return The fitness of the worst {@link Individual}, or -1 if there is not {@link Individual} in this population.
     */
    public double worstFitness() {
        final Individual individual = worstIndividual();
        return individual == null ? -1 : individual.getFitness();
    }

    /**
     * @return The median fitness (sorted from worst to best).
     */
    public double medianFitness() {
        List<Individual> sortedIndividuals = getSortedIndividualsFromWorstToBest();
        return sortedIndividuals.get(populationSize / 2).getFitness();
    }

    /**
     * @return The list of {@link Individual} of this population, sorted by fitness asc (i.e from worst to best).
     */
    public List<Individual> getSortedIndividualsFromWorstToBest() {
        List<Individual> sortedIndividuals = new ArrayList<>(individuals);
        sortedIndividuals.sort((o1, o2) -> Double.compare(o1.getFitness(), o2.getFitness()));
        return sortedIndividuals;
    }

    /**
     * @return The list of {@link Individual} of this population, sorted by fitness desc (i.e from best to worst).
     */
    public List<Individual> getSortedIndividualsFromBestToWorst() {
        List<Individual> sortedIndividuals = new ArrayList<>(individuals);
        sortedIndividuals.sort(((o1, o2) -> Double.compare(o2.getFitness(), o1.getFitness())));
        return sortedIndividuals;
    }


    // ================================================
    // Getters
    // ================================================

    /**
     * @return The previous population.
     */
    public Population getPreviousPopulation() {
        return previousPopulation;
    }

    /**
     * @return The list of individuals.
     * @implNote The returned list is not modifiable.
     */
    public List<Individual> getIndividuals() {
        return Collections.unmodifiableList(individuals);
    }

    /**
     * @return This population's generation number.
     */
    public int getGeneration() {
        return generation;
    }

    /**
     * @return The population's size.
     */
    public int getPopulationSize() {
        return populationSize;
    }

    /**
     * @return The {@link IndividualCreator} used for creating the {@link Individual}s of this population.
     */
    public IndividualCreator getCreator() {
        return creator;
    }

    /**
     * {@link Collector} to use for calculating the accumulated relative fitness.
     *
     * @implNote This {@link Collector} is not concurrent, and the {@link Collector#accumulator()}
     * method returns a stateful function.
     */
    private static class AccumulatedRelativeFitnessMapCollector
            implements Collector<Map.Entry<Individual, Double>,
            Multimap<Individual, Double>, Multimap<Individual, Double>> {

        /**
         * Accumulated relative fitness.
         */
        private double accumulated = 0.0; // TODO: check how to do this without being stateful.

        @Override
        public Supplier<Multimap<Individual, Double>> supplier() {
            return ArrayListMultimap::create;
        }

        @Override
        public BiConsumer<Multimap<Individual, Double>, Map.Entry<Individual, Double>> accumulator() {
            return (map, entry) -> {
                accumulated += entry.getValue();
                map.put(entry.getKey(), accumulated);
            };
        }

        @Override
        public BinaryOperator<Multimap<Individual, Double>> combiner() {
            return null;
        }

        @Override
        public Function<Multimap<Individual, Double>, Multimap<Individual, Double>> finisher() {
            return Function.identity();
        }

        @Override
        public Set<Characteristics> characteristics() {
            return Collections.singleton(Characteristics.IDENTITY_FINISH);
        }
    }
}
