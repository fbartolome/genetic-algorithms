package ar.edu.itba.genetic_algorithms.models.character;

import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.models.item.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Class representing a character.
 * A character is an {@link Individual} for the Genetic Algorithms.
 */
public abstract class Character implements Individual {


    /**
     * The character's height.
     */
    private final double height;

    /**
     * The character's equipment.
     */
    private final Equipment equipment;

    private static Multipliers multipliersInstance;



    // ================================================
    // Instance variables that might be removed.    //|
    // ================================================
    private final double strength;                  //|
    private final double agility;                   //|
    private final double proficiency;               //|
    private final double life;                      //|
    private final double resistance;                //|
    private final Multipliers multipliers;          //|
    // ================================================


    /**
     * The character's attack.
     */
    private final double attack;

    /**
     * The character's defense.
     */
    private final double defense;


    /**
     * Constructor.
     *
     * @param height      The character's height.
     * @param equipment   The character's equipment.
     * @param multipliers The character's multipliers for the items' stats.
     */
    protected Character(double height, Equipment equipment, Multipliers multipliers) {
        if (!(height >= 1.3 && height <= 2.0) || equipment == null || multipliers == null) {
            throw new IllegalArgumentException("Wrong parameters.");
        }
        this.height = height;
        this.equipment = equipment;
        this.multipliers = multipliers;

        // Character's stats
        List<Item> items = equipment.asItemList();
        this.strength = 100 * Math.tanh(0.01 * multipliers.strength * items.stream().mapToDouble(Item::getStrength)
                .sum());
        this.agility = Math.tanh(0.01 * multipliers.agility * items.stream().mapToDouble(Item::getAgility).sum());
        this.proficiency = 0.6 * Math.tanh(0.01 * multipliers.proficiency * items.stream()
                .mapToDouble(Item::getProficiency).sum());
        this.resistance = Math.tanh(0.01 * multipliers.resistance * items.stream().mapToDouble(Item::getResistance)
                .sum());
        this.life = 100 * Math.tanh(0.01 * multipliers.life * items.stream().mapToDouble(Item::getLife).sum());


        // Modifiers
        final double pow2 = Math.pow((3 * this.height - 5), 2);
        final double pow4 = Math.pow((3 * this.height - 5), 4);
        final double halfHeight = this.height / 2;
        double attackModifier = 0.5 - pow4 + pow2 + halfHeight;
        double defenseModifier = 2 + pow4 - pow2 - halfHeight;

        // Attack and defense
        this.attack = (this.agility + this.proficiency) * this.strength * attackModifier;
        this.defense = (this.resistance + this.proficiency) * this.life * defenseModifier;
    }


    /**
     * @return The attack of the character.
     */
    public double getAttack() {
        return attack;
    }

    /**
     * @return The defense of the character.
     */
    public double getDefense() {
        return defense;
    }


    /**
     * Creates the {@link Multipliers} instance to be used during all execution.
     *
     * @param strength    Multiplier for strength.
     * @param agility     Multiplier for agility.
     * @param proficiency Multiplier for proficiency.
     * @param resistance  Multiplier for resistance.
     * @param life        Multiplier for life.
     */
    public static void createMultipliersInstance(double strength, double agility, double proficiency,
                                                 double resistance, double life) {
        multipliersInstance = new Multipliers(strength, agility, proficiency, resistance, life);
    }


    /**
     * Builder for {@link Character}.
     */
    public abstract static class Builder<T extends Character> {


        /**
         * The value of height for the built {@link Character}.
         */
        private double height;

        /**
         * The {@link Equipment} for the built {@link Character}.
         */
        private Equipment equipment;

        /**
         * The {@link Multipliers} for the built {@link Character}.
         */
        private Multipliers multipliers;


        /**
         * Constructor.
         */
        public Builder() {
            this.multipliers = multipliersInstance; // If set, used that instance. Else, create one with the builder.
        }

        /**
         * Creates a new {@link Equipment.Builder} instance for setting this builder's {@link Equipment}.
         *
         * @return The {@link Equipment.Builder}.
         */
        public Equipment.Builder equipment() {
            return new Equipment.Builder(this);
        }


        /**
         * Creates a new {@link Multipliers.Builder} instance for setting this builder's {@link Multipliers}.
         *
         * @return The {@link Multipliers.Builder}.
         */
        public Multipliers.Builder multipliers() {
            return new Multipliers.Builder(this);
        }


        /**
         * Sets the {@link Character} height.
         *
         * @param height The new height.
         * @return this (for method chaining).
         */
        public Builder height(double height) {
            this.height = height;
            return this;
        }

        /**
         * @return The set height.
         */
        /* package */ double getHeight() {
            return height;
        }

        /**
         * @return The set {@link Equipment}.
         */
        /* package */ Equipment getEquipment() {
            return equipment;
        }

        /**
         * @return The set {@link Multipliers}.
         */
        /* package */ Multipliers getMultipliers() {
            return multipliers;
        }

        /**
         * Clears the builder (i.e sets all values to default ones).
         *
         * @return this (for method chaining).
         */
        public Builder clear() {
            this.height = 0;
            this.equipment = null;
            this.multipliers = null;
            return this;
        }

        /**
         * Builds the {@link Character}
         *
         * @return The built {@link Character}.
         */
        public abstract T build();


        // =========================================================
        // Private setters for being called by the other builders
        // =========================================================

        /**
         * {@link Equipment} setter.
         *
         * @param equipment The new {@link Equipment}.
         * @return this (for method chaining).
         */
        private Builder setEquipment(Equipment equipment) {
            this.equipment = equipment;
            return this;
        }

        /**
         * {@link Multipliers} setter.
         *
         * @param multipliers The new {@link Multipliers}.
         * @return this (for method chaining).
         */
        private Builder setMultipliers(Multipliers multipliers) {
            this.multipliers = multipliers;
            return this;
        }


    }


    // ================================================================================
    // Equipment
    // ================================================================================


    /**
     * Class encapsulating the {@link Character} equipment.
     */
    /* package */ static class Equipment {

        /**
         * The {@link Weapon} for the character.
         */
        private final Weapon weapon;

        /**
         * The {@link Boot} for the character.
         */
        private final Boot boot;

        /**
         * The {@link Gauntlet} for the character.
         */
        private final Gauntlet gauntlet;

        /**
         * The {@link Helmet} for the character.
         */
        private final Helmet helmet;

        /**
         * The {@link Armor} for the character.
         */
        private final Armor armor;

        /**
         * Constructor.
         *
         * @param weapon   The {@link Weapon} for the character.
         * @param boot     The {@link Boot} for the character.
         * @param gauntlet The {@link Gauntlet} for the character.
         * @param helmet   The {@link Helmet} for the character.
         * @param armor    The {@link Armor} for the character.
         * @throws IllegalArgumentException If any of the passed arguments is null.
         */
        public Equipment(Weapon weapon, Boot boot, Gauntlet gauntlet, Helmet helmet, Armor armor)
                throws IllegalArgumentException {
            if (weapon == null || boot == null || gauntlet == null || helmet == null || armor == null) {
                throw new IllegalArgumentException("Items cannot be null.");
            }
            this.weapon = weapon;
            this.boot = boot;
            this.gauntlet = gauntlet;
            this.helmet = helmet;
            this.armor = armor;
        }

        /**
         * @return The {@link Weapon} for the character.
         */
        public Weapon getWeapon() {
            return weapon;
        }

        /**
         * @return The {@link Boot} for the character.
         */
        public Boot getBoot() {
            return boot;
        }

        /**
         * @return The {@link Gauntlet} for the character.
         */
        public Gauntlet getGauntlet() {
            return gauntlet;
        }

        /**
         * @return The {@link Helmet} for the character.
         */
        public Helmet getHelmet() {
            return helmet;
        }

        /**
         * @return The {@link Armor} for the character.
         */
        public Armor getArmor() {
            return armor;
        }

        /**
         * @return All the Equipment as a {@link List} of {@link Item}.
         */
        private List<Item> asItemList() {
            return Stream.of(this.weapon, this.boot, this.gauntlet, this.helmet, this.armor)
                    .collect(Collectors.toList());
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Equipment)) return false;

            Equipment equipment = (Equipment) o;

            return weapon.equals(equipment.weapon)
                    && boot.equals(equipment.boot)
                    && gauntlet.equals(equipment.gauntlet)
                    && helmet.equals(equipment.helmet)
                    && armor.equals(equipment.armor);

        }

        @Override
        public int hashCode() {
            int result = weapon.hashCode();
            result = 31 * result + boot.hashCode();
            result = 31 * result + gauntlet.hashCode();
            result = 31 * result + helmet.hashCode();
            result = 31 * result + armor.hashCode();
            return result;
        }


        /**
         * Builder for {@link Equipment}.
         */
        public static class Builder {


            /**
             * The builder from which this builder was created.
             */
            private final Character.Builder characterBuilder;


            /**
             * The {@link Weapon} for the character.
             */
            private Weapon weapon;

            /**
             * The {@link Boot} for the character.
             */
            private Boot boot;

            /**
             * The {@link Gauntlet} for the character.
             */
            private Gauntlet gauntlet;

            /**
             * The {@link Helmet} for the character.
             */
            private Helmet helmet;

            /**
             * The {@link Armor} for the character.
             */
            private Armor armor;


            /**
             * Constructor.
             *
             * @param characterBuilder The builder from which this builder was created.
             */
            /* package */ Builder(Character.Builder characterBuilder) {
                this.characterBuilder = characterBuilder;
            }


            /**
             * {@link Weapon} setter.
             *
             * @param weapon The new {@link Weapon}.
             * @return this (for method chaining).
             */
            public Builder setWeapon(Weapon weapon) {
                this.weapon = weapon;
                return this;
            }

            /**
             * {@link Boot} setter.
             *
             * @param boot The new {@link Boot}
             * @return this (for method chaining).
             */
            public Builder setBoot(Boot boot) {
                this.boot = boot;
                return this;
            }

            /**
             * {@link Gauntlet} setter.
             *
             * @param gauntlet The new {@link Gauntlet}.
             * @return this (for method chaining).
             */
            public Builder setGauntlet(Gauntlet gauntlet) {
                this.gauntlet = gauntlet;
                return this;
            }

            /**
             * {@link Helmet} setter.
             *
             * @param helmet The new {@link Helmet}.
             * @return this (for method chaining).
             */
            public Builder setHelmet(Helmet helmet) {
                this.helmet = helmet;
                return this;
            }

            /**
             * {@link Armor} setter.
             *
             * @param armor The new {@link Armor}.
             * @return this (for method chaining).
             */
            public Builder setArmor(Armor armor) {
                this.armor = armor;
                return this;
            }

            /**
             * Clears the builder (i.e sets all values to default ones).
             *
             * @return this (for method chaining).
             */
            public Builder clear() {
                this.weapon = null;
                this.boot = null;
                this.gauntlet = null;
                this.helmet = null;
                this.armor = null;
                return this;
            }

            /**
             * @return The {@link Character.Builder} from which this builder was created.
             */
            public Character.Builder character() {
                characterBuilder.setEquipment(new Equipment(weapon, boot, gauntlet, helmet, armor));
                return characterBuilder;
            }
        }
    }


    // ================================================================================
    // Multipliers
    // ================================================================================

    /**
     * Class representing multipliers of {@link Item} stats.
     * <p>
     * Created by Juan Marcos Bellini on 24/5/17.
     */
    /* package */ static class Multipliers {

        /**
         * Multiplier for strength.
         */
        private final double strength;

        /**
         * Multiplier for agility.
         */
        private final double agility;

        /**
         * Multiplier for proficiency.
         */
        private final double proficiency;

        /**
         * Multiplier for resistance.
         */
        private final double resistance;

        /**
         * Multiplier for life.
         */
        private final double life;


        /**
         * Constructor.
         *
         * @param strength    Multiplier for strength.
         * @param agility     Multiplier for agility.
         * @param proficiency Multiplier for proficiency.
         * @param resistance  Multiplier for resistance.
         * @param life        Multiplier for life.
         */
        private Multipliers(double strength, double agility, double proficiency, double resistance, double life) {
            this.proficiency = proficiency;
            this.agility = agility;
            this.strength = strength;
            this.life = life;
            this.resistance = resistance;
        }


        /**
         * Strength multiplier getter.
         *
         * @return The strength multiplier.
         */
        public double getStrength() {
            return strength;
        }

        /**
         * Agility multiplier getter.
         *
         * @return The agilitymultiplier.
         */
        public double getAgility() {
            return agility;
        }

        /**
         * Proficiency multiplier getter.
         *
         * @return The proficiency multiplier.
         */
        public double getProficiency() {
            return proficiency;
        }

        /**
         * Resistance multiplier getter.
         *
         * @return The resistance multiplier.
         */
        public double getResistance() {
            return resistance;
        }

        /**
         * Life multiplier getter.
         *
         * @return The life multiplier.
         */
        public double getLife() {
            return life;
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Multipliers)) return false;

            Multipliers that = (Multipliers) o;

            return Double.compare(that.proficiency, proficiency) == 0
                    && Double.compare(that.agility, agility) == 0
                    && Double.compare(that.strength, strength) == 0
                    && Double.compare(that.life, life) == 0
                    && Double.compare(that.resistance, resistance) == 0;

        }

        @Override
        public int hashCode() {
            int result;
            long temp;
            temp = Double.doubleToLongBits(proficiency);
            result = (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(agility);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(strength);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(life);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            temp = Double.doubleToLongBits(resistance);
            result = 31 * result + (int) (temp ^ (temp >>> 32));
            return result;
        }


        /**
         * Builder for {@link Multipliers}.
         */
        public static class Builder {

            /**
             * The builder from which this builder was created.
             */
            private final Character.Builder characterBuilder;

            /**
             * Multiplier for strength.
             */
            private double strength;

            /**
             * Multiplier for agility.
             */
            private double agility;

            /**
             * Multiplier for proficiency.
             */
            private double proficiency;

            /**
             * Multiplier for resistance.
             */
            private double resistance;

            /**
             * Multiplier for life.
             */
            private double life;


            /**
             * Constructor.
             *
             * @param characterBuilder The builder from which this builder was created.
             */
            /* package */ Builder(Character.Builder characterBuilder) {
                this.characterBuilder = characterBuilder;
            }


            /**
             * Strength setter.
             *
             * @param strength The new strength.
             * @return this (for method chaining).
             */
            public Builder setStrength(double strength) {
                this.strength = strength;
                return this;
            }

            /**
             * Agility setter.
             *
             * @param agility The new agility.
             * @return this (for method chaining).
             */
            public Builder setAgility(double agility) {
                this.agility = agility;
                return this;
            }

            /**
             * Proficiency setter.
             *
             * @param proficiency The new proficiency.
             * @return this (for method chaining).
             */
            public Builder setProficiency(double proficiency) {
                this.proficiency = proficiency;
                return this;
            }

            /**
             * Resistance setter.
             *
             * @param resistance The new resistance.
             * @return this (for method chaining).
             */
            public Builder setResistance(double resistance) {
                this.resistance = resistance;
                return this;
            }

            /**
             * Life setter.
             *
             * @param life The new life.
             * @return this (for method chaining).
             */
            public Builder setLife(double life) {
                this.life = life;
                return this;
            }

            /**
             * Clears the builder (i.e sets all values to default ones).
             *
             * @return this (for method chaining).
             */
            public Builder clear() {
                this.strength = 0;
                this.agility = 0;
                this.proficiency = 0;
                this.resistance = 0;
                this.life = 0;
                return this;
            }

            /**
             * @return The {@link Character.Builder} from which this builder was created.
             */
            public Character.Builder character() {
                characterBuilder.setMultipliers(new Multipliers(strength, agility, proficiency, resistance, life));
                return characterBuilder;
            }
        }
    }
}
