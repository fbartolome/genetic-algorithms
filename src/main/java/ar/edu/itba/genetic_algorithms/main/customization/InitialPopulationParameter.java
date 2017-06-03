package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.api.IndividualCreator;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    // TODO: define more parameters (for example: shuffle, random, etc.)

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
        return new Population(IntStream.range(0, this.size)
                .mapToObj(i -> new Chromosome(IntStream
                        .range(0, alleles.getAmountOfGenes()).mapToObj(alleles::getRandomAllele).toArray()))
                .map(creator::create)
                .collect(Collectors.toList()), null, creator);
    }
}
