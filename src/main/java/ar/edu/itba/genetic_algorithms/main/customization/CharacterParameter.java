package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.models.character.*;
import ar.edu.itba.genetic_algorithms.models.character.Character;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameter for choosing a {@link Character}.
 */
public class CharacterParameter {

    /**
     * The type of character.
     */
    @JsonProperty
    private CharacterType type;

    /**
     * The strength multiplier.
     */
    @JsonProperty
    private double strengthMultiplier;

    /**
     * The agility multiplier.
     */
    @JsonProperty
    private double agilityMultiplier;

    /**
     * The proficiency multiplier.
     */
    @JsonProperty
    private double proficiencyMultiplier;

    /**
     * The resistance multiplier.
     */
    @JsonProperty
    private double resistanceMultiplier;

    /**
     * The life multiplier.
     */
    @JsonProperty
    private double lifeMultiplier;


    /**
     * @return The type of character.
     */
    public Character.Builder getCharacterBuilder() {
        return type.getCharacterBuilder();
    }

    /**
     * @return The strength multiplier.
     */
    public double getStrengthMultiplier() {
        return strengthMultiplier;
    }

    /**
     * @return The agility multiplier.
     */
    public double getAgilityMultiplier() {
        return agilityMultiplier;
    }

    /**
     * @return The proficiency multiplier.
     */
    public double getProficiencyMultiplier() {
        return proficiencyMultiplier;
    }

    /**
     * @return The resistance multiplier.
     */
    public double getResistanceMultiplier() {
        return resistanceMultiplier;
    }

    /**
     * @return The life multiplier.
     */
    public double getLifeMultiplier() {
        return lifeMultiplier;
    }


    /**
     * Enum with all type of {@link Character}, defining a method to get the particular character's
     * (i.e {@link Warrior}, {@link Archer}, {@link Guard}, or {@link Assassin}) builder.
     */
    private enum CharacterType {
        WARRIOR {
            @Override
            public Character.Builder<Warrior> getCharacterBuilder() {
                return new Warrior.Builder();
            }
        },
        ARCHER {
            @Override
            public Character.Builder<Archer> getCharacterBuilder() {
                return new Archer.Builder();
            }
        },
        GUARD {
            @Override
            public Character.Builder<Guard> getCharacterBuilder() {
                return new Guard.Builder();
            }
        },
        ASSASSIN {
            @Override
            public Character.Builder<Assassin> getCharacterBuilder() {
                return new Assassin.Builder();
            }
        };

        /**
         * @return The {@link Character.Builder} represented by the enum value.
         */
        public abstract Character.Builder<? extends Character> getCharacterBuilder();
    }
}
