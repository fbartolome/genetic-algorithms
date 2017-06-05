package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.api.IndividualCreator;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Parameter for choosing the initial {@link Population}.
 */
public class InitialPopulationParameter {

    /**
     * The size of the initial {@link Population}.
     */
    @JsonProperty
    private int size;

    /**
     * The type of initialization (i.e SORTED, SHUFFLE or RANDOM).
     */
    @JsonProperty
    private Initialization initialization;

    /**
     * @return The size of the initial {@link Population}.
     */
    public int getSize() {
        return size;
    }


    /**
     * Creates an initial {@link Population} based on the set parameters.
     *
     * @param creator The {@link IndividualCreator} used for creating {@link Individual} from a {@link Chromosome}.
     * @param alleles The {@link AlleleContainerWrapper} holding all alleles that can be used
     *                for creating {@link Individual}s for the initial {@link Population}
     * @return The created {@link Population}.
     */
    public Population generateInitialPopulation(IndividualCreator creator, AlleleContainerWrapper alleles) {
        return this.initialization.generateInitialPopulation(size, creator, alleles);
    }

    /**
     * Enum indicating types of population initialization.
     */
    private enum Initialization {
        /**
         * This kind of initialization will create each individual using the allele with number
         * {@code numberOfIndividual % amountOfAllelesForEachGen}.
         */
        SORTED {
            @Override
            protected Population generateInitialPopulation(int populationSize, IndividualCreator creator,
                                                           AlleleContainerWrapper alleles) {

                final int amountOfGenes = alleles.getAmountOfGenes();
                final Integer[][] numbersOfAlleles = IntStream.range(0, amountOfGenes)
                        .mapToObj(alleles::getAllelesNumbers)
                        .map(each -> each.stream().toArray(Integer[]::new))
                        .toArray(Integer[][]::new);

                return new Population(IntStream.range(0, populationSize)
                        .mapToObj(numberOfIndividual -> new Chromosome(IntStream.range(0, amountOfGenes)
                                .mapToObj(gen -> alleles
                                        .getSpecificAllele(numberOfIndividual % (numbersOfAlleles[gen]).length, gen))
                                .toArray()))
                        .map(creator::create)
                        .collect(Collectors.toList()), null, creator);

            }
        },
        /**
         * This kind of initialization will create each individuals the same way as SORTED
         * but randomizing the numberOfIndividual.
         */
        SHUFFLE {
            @Override
            protected Population generateInitialPopulation(int populationSize, IndividualCreator creator,
                                                           AlleleContainerWrapper alleles) {
                final List<Individual> individuals =
                        new LinkedList<>(SORTED.generateInitialPopulation(populationSize, creator, alleles)
                                .getIndividuals());
                // New list as population might return unmodifiable list.
                Collections.shuffle(individuals, new Random());
                return new Population(individuals, null, creator);
            }
        },
        /**
         * This kind of initialization will create each individual getting random alleles.
         */
        RANDOM {
            @Override
            protected Population generateInitialPopulation(int populationSize, IndividualCreator creator,
                                                           AlleleContainerWrapper alleles) {
                return new Population(IntStream.range(0, populationSize)
                        .mapToObj(i -> new Chromosome(IntStream
                                .range(0, alleles.getAmountOfGenes()).mapToObj(alleles::getRandomAllele).toArray()))
                        .map(creator::create)
                        .collect(Collectors.toList()), null, creator);
            }
        };

        protected abstract Population generateInitialPopulation(int populationSize, IndividualCreator creator,
                                                                AlleleContainerWrapper alleles);

    }
}
