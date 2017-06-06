package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.List;
import java.util.OptionalDouble;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * This class implements the Boltzmann selection method.
 */
public class Boltzmann extends AccumulatedSelectionMethod {

    /**
     * The "n" parameters for calculating the temperature.
     */
    private final double n;

    /**
     * Constructor.
     *
     * @param n The "n" parameters for calculating the temperature.
     */
    public Boltzmann(double n) {
        this.n = n;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        final double temp = temperature(population.getGeneration());
        // Create a supplier of streams that contains exp(f(i) / temp) for each i : individual
        Supplier<Stream<Double>> streamSupplier = () -> population.getIndividuals().stream()
                .parallel()
                .map(individual -> Math.exp(individual.getFitness() / temp));

        List<Double> numerators = streamSupplier.get().parallel().collect(Collectors.toList());
        final OptionalDouble optional = streamSupplier.get().mapToDouble(each -> each).parallel().average();
        if (!optional.isPresent()) {
            throw new IllegalArgumentException("Population has no individuals");
        }
        final double denominator = optional.getAsDouble();

        Multimap<Individual, Double> accumulatedExpVal = ArrayListMultimap.create();
        double accumulated = 0;
        for (int i = 0; i < population.getPopulationSize(); i++) {
            accumulated += (numerators.get(i) / denominator);
            accumulatedExpVal.put(population.getIndividuals().get(i), accumulated);
        }

        final double finalAccumulated = accumulated;
        return IntStream.range(0, k)
                .parallel()
                .mapToObj(i ->
                        selectChromosomeOnAccumulatedFitnessProbability(new Random().nextDouble() * finalAccumulated,
                                accumulatedExpVal)).collect(Collectors.toList());
    }

    /**
     * Calculates the temperature of the given {@code generation}.
     *
     * @param generation The generation number.
     * @return The temperature of the given {@code generation}.
     */
    private double temperature(int generation) {
        return (1 / (n * generation)) + 0.1;
    }
}

///https://doc.lagout.org/science/0_Computer%20Science/2_Algorithms/Practical%20Handbook%20of%20GENETIC%20ALGORITHMS%2C%20Volume%20II/ganf5.pdf