package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import ar.edu.itba.genetic_algorithms.models.character.Character;
import ar.edu.itba.genetic_algorithms.models.item.Item;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 */
public class Parameters {


    /**
     * Parameters for {@link Character} initialization.
     */
    @JsonProperty
    private CharacterParameter character;

    /**
     * Parameters for {@link Item} initialization.
     */
    @JsonProperty
    private ItemFilesParameter items;

    /**
     * Parameters for all strategies initialization.
     */
    @JsonProperty
    private StrategiesParameter strategies;

    /**
     * Parameters for initializing the initial {@link Population}.
     */
    @JsonProperty
    private InitialPopulationParameter initialPopulation;

    /**
     * Default constructor for Jackson.
     */
    public Parameters() {
        // For Jackson.
    }

    /**
     * @return Parameters for {@link Character} initialization.
     */
    public CharacterParameter getCharacter() {
        return character;
    }

    /**
     * @return Parameters for {@link Item} initialization.
     */
    public ItemFilesParameter getItems() {
        return items;
    }

    /**
     * @return Parameters for all strategies initialization.
     */
    public StrategiesParameter getStrategies() {
        return strategies;
    }

    /**
     * @return Parameters for initializing the initial {@link Population}.
     */
    public InitialPopulationParameter getInitialPopulation() {
        return initialPopulation;
    }

    /**
     * Generates a {@link Parameters} instance from a JSON file located in the given {@code filePath}.
     *
     * @param filePath The path to the JSON file.
     * @return The {@link Parameters} instance.
     * @throws IOException If some IO error occurs while reading the JSON file.
     */
    public static Parameters fromFile(String filePath) throws IOException {
        return new ObjectMapper().readValue(Files.lines(Paths.get(filePath))
                        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString(),
                new TypeReference<Parameters>() {
                });
    }
}
