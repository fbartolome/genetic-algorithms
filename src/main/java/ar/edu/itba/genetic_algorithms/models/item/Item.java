package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents an item.
 */
public abstract class Item {


    /**
     * Item's proficiency.
     */
    private final double proficiency;

    /**
     * Item's proficiency.
     */
    private final double agility;

    /**
     * Item's proficiency.
     */
    private final double strength;

    /**
     * Item's proficiency.
     */
    private final double life;

    /**
     * Item's proficiency.
     */
    private final double resistance;

    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    protected Item(double proficiency, double agility, double strength, double life, double resistance) {
        this.proficiency = proficiency;
        this.agility = agility;
        this.strength = strength;
        this.life = life;
        this.resistance = resistance;
    }


    /**
     * @return The item's proficiency value.
     */
    public double getProficiency() {
        return proficiency;
    }

    /**
     * @return The item's agility value.
     */
    public double getAgility() {
        return agility;
    }

    /**
     * @return The item's strength value.
     */
    public double getStrength() {
        return strength;
    }

    /**
     * @return The item's life value.
     */
    public double getLife() {
        return life;
    }

    /**
     * @return The item's resistance value.
     */
    public double getResistance() {
        return resistance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        return Double.compare(item.proficiency, proficiency) == 0
                && Double.compare(item.agility, agility) == 0
                && Double.compare(item.strength, strength) == 0
                && Double.compare(item.life, life) == 0
                && Double.compare(item.resistance, resistance) == 0;

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
     * Item's builder.
     *
     * @param <T> The type of item.
     */
    public static abstract class Builder<T extends Item> {

        /**
         * Item's proficiency.
         */
        private double proficiency;

        /**
         * Item's proficiency.
         */
        private double agility;

        /**
         * Item's proficiency.
         */
        private double strength;

        /**
         * Item's proficiency.
         */
        private double life;

        /**
         * Item's proficiency.
         */
        private double resistance;


        /**
         * Proficiency getter.
         *
         * @return The proficiency set.
         */
        /* package */ double getProficiency() {
            return proficiency;
        }

        /**
         * Agility getter.
         *
         * @return The agility set.
         */
        /* package */ double getAgility() {
            return agility;
        }

        /**
         * Strength getter.
         *
         * @return The strength set.
         */
        /* package */ double getStrength() {
            return strength;
        }

        /**
         * Life getter.
         *
         * @return The life set.
         */
        /* package */ double getLife() {
            return life;
        }

        /**
         * Resistance getter.
         *
         * @return The resistance set.
         */
        /* package */ double getResistance() {
            return resistance;
        }

        /**
         * Sets the proficiency for the item.
         *
         * @param proficiency The proficiency for the item.
         * @return this (for method chaining).
         */
        public Builder setProficiency(double proficiency) {
            this.proficiency = proficiency;
            return this;
        }

        /**
         * Sets the agility for the item.
         *
         * @param agility The agility for the item.
         * @return this (for method chaining).
         */
        public Builder setAgility(double agility) {
            this.agility = agility;
            return this;
        }

        /**
         * Sets the strength for the item.
         *
         * @param strength The strength for the item.
         * @return this (for method chaining).
         */
        public Builder setStrength(double strength) {
            this.strength = strength;
            return this;
        }

        /**
         * Sets the life for the item.
         *
         * @param life The life for the item.
         * @return this (for method chaining).
         */
        public Builder setLife(double life) {
            this.life = life;
            return this;
        }

        /**
         * Sets the resistance for the item.
         *
         * @param resistance The resistance for the item.
         * @return this (for method chaining).
         */
        public Builder setResistance(double resistance) {
            this.resistance = resistance;
            return this;
        }

        /**
         * Clears the builder.
         *
         * @return this (for method chaining).
         */
        public Builder clear() {
            this.proficiency = 0;
            this.agility = 0;
            this.strength = 0;
            this.life = 0;
            this.resistance = 0;
            return this;
        }

        /**
         * Builds the item.
         *
         * @return The built item.
         */
        public abstract T build();


    }

}
