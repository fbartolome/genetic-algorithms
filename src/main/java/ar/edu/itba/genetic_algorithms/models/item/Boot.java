package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents a {@link Boot} {@link Item}.
 */
public class Boot extends Item {
    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    private Boot(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }

    /**
     * Builds the {@link Boot}.
     */
    public static class Builder extends Item.Builder<Boot> {

        @Override
        public Boot build() {
            return new Boot(this.getProficiency(), this.getAgility(), this.getStrength(),
                    this.getLife(), this.getResistance());
        }
    }
}
