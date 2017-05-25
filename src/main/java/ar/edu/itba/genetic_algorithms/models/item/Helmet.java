package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents a {@link Helmet} {@link Item}.
 */
public class Helmet extends Item {
    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    private Helmet(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }

    /**
     * Builds the {@link Helmet}.
     */
    public static class Builder extends Item.Builder<Helmet> {

        @Override
        public Helmet build() {
            return new Helmet(this.getProficiency(), this.getAgility(), this.getStrength(),
                    this.getLife(), this.getResistance());
        }
    }
}
