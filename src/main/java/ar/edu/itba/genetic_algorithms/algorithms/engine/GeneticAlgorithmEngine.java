package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.CrossoverStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import one.util.streamex.StreamEx;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Genetic Algorithm engine.
 * This class defines a method for evolving a {@link Population} in order to find a new one with a better fitness.
 */
public class GeneticAlgorithmEngine {

    /**
     * The actual population.
     */
    private Population population;

    /**
     * An ending condition strategy.
     */
    private final EndingCondition endingCondition;

    /**
     * A selection strategy.
     */
    private final SelectionStrategy selectionStrategy;

    /**
     * The amount of {@link Individual} to select.
     */
    private final int k;

    /**
     * A crossover strategy.
     */
    private final CrossoverStrategy crossoverStrategy;

    /**
     * A mutation strategy.
     */
    private final MutationStrategy mutationStrategy;

    /**
     * The probability of mutating.
     */
    private final double pm;

    /**
     * A replacement strategy.
     */
    private final ReplacementStrategy replacementStrategy;

    /**
     * An object wrapping al containers of alleles.
     */
    private final AlleleContainerWrapper alleleContainerWrapper;


    /**
     * Constructor.
     *
     * @param initialPopulation      The first population.
     * @param endingCondition        An ending condition strategy.
     * @param selectionStrategy      A selection strategy.
     * @param k                      The amount of {@link Individual} to select.
     * @param crossoverStrategy      A crossover strategy.
     * @param mutationStrategy       A mutation strategy.
     * @param pm                     The probability of mutating.
     * @param replacementStrategy    A replacement strategy.
     * @param alleleContainerWrapper An object wrapping al containers of alleles.
     */
    public GeneticAlgorithmEngine(Population initialPopulation, EndingCondition endingCondition,
                                  SelectionStrategy selectionStrategy, int k,
                                  CrossoverStrategy crossoverStrategy, MutationStrategy mutationStrategy,
                                  double pm, ReplacementStrategy replacementStrategy,
                                  AlleleContainerWrapper alleleContainerWrapper) {
        this.population = initialPopulation;
        this.endingCondition = endingCondition;
        this.selectionStrategy = selectionStrategy;
        this.k = k;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.pm = pm;
        this.replacementStrategy = replacementStrategy;
        this.alleleContainerWrapper = alleleContainerWrapper;
    }

    /**
     * Evolves the first {@link Population} in order to get a new one with a better fitness.
     *
     * @return a new {@link Population} with a better fitness.
     */
    public Population evolve() {
        while (!endingCondition.isSatisfied(population)) {
            List<Chromosome> chromosomes = selectionStrategy.select(population, k);
            List<ChromosomePair> chromosomePairs = new LinkedList<>();
            for (int i = 0; i < k; i += 2) {
                // TODO: make this in stream
                chromosomePairs.add(new ChromosomePair(chromosomes.get(i), chromosomes.get(i + 1)));
            }

            List<Individual> individuals = chromosomePairs.stream().parallel()
                    // Performs crossover of chromosome pairs in stream.
                    .map(crossoverStrategy::crossover)
                    // Transform chromosome pair stream into chromosome stream
                    .flatMap(pair -> Stream.of(pair.getFirst(), pair.getSecond()))
                    .map(chromosome -> {
                        if (new Random().nextDouble() < pm) {
                            mutationStrategy.mutate(chromosome, alleleContainerWrapper); // TODO: return new chromosome to avoid stateful operation
                        }
                        return chromosome;
                    }) // Performs mutation
                    .map(each -> population.getCreator().create(each)) // Transforms chromosome into individual
                    .collect(Collectors.toList()); // Collects individuals into list and pass it to "replace".

            population = replacementStrategy.replace(population, individuals);
        }
        return population;
    }


    public double getActualAverageFitness() {
        return population.avgFitness();
    }

    /**
     * @return The actual {@link Population}.
     */
    public Population getPopulation() {
        return population;
    }
}
