package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents a {@link Gauntlet} {@link Item}.
 */
public class Gauntlet extends Item {
    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    private Gauntlet(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }

    /**
     * Builds the {@link Gauntlet}.
     */
    public static class Builder extends Item.Builder<Gauntlet> {

        @Override
        public Gauntlet build() {
            return new Gauntlet(this.getProficiency(), this.getAgility(), this.getStrength(),
                    this.getLife(), this.getResistance());
        }
    }
}
