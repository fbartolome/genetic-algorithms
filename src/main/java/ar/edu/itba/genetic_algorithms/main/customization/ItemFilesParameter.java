package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.models.item.*;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameter for choosing files containing {@link Item}s.
 */
public class ItemFilesParameter {

    /**
     * String representation of the path to the file containing {@link Armor}s.
     */
    @JsonProperty
    private String armorsFilePath;

    /**
     * String representation of the path to the file containing {@link Boot}s.
     */
    @JsonProperty
    private String bootsFilePath;

    /**
     * String representation of the path to the file containing {@link Gauntlet}s.
     */
    @JsonProperty
    private String gauntletsFilePath;

    /**
     * String representation of the path to the file containing {@link Helmet}s.
     */
    @JsonProperty
    private String helmetsFilePath;

    /**
     * String representation of the path to the file containing {@link Weapon}s.
     */
    @JsonProperty
    private String weaponsFilePath;

    /**
     * @return String representation of the path to the file containing {@link Armor}s.
     */
    public String getArmorsFilePath() {
        return armorsFilePath;
    }

    /**
     * @return String representation of the path to the file containing {@link Boot}s.
     */
    public String getBootsFilePath() {
        return bootsFilePath;
    }

    /**
     * @return String representation of the path to the file containing {@link Gauntlet}s.
     */
    public String getGauntletsFilePath() {
        return gauntletsFilePath;
    }

    /**
     * @return String representation of the path to the file containing {@link Helmet}s.
     */
    public String getHelmetsFilePath() {
        return helmetsFilePath;
    }

    /**
     * @return String representation of the path to the file containing {@link Weapon}s.
     */
    public String getWeaponsFilePath() {
        return weaponsFilePath;
    }
}
