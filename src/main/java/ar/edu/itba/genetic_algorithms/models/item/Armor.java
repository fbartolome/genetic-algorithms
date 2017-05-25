package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents an {@link Armor} {@link Item}.
 */
public class Armor extends Item {
    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    private Armor(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }

    /**
     * Builds the {@link Armor}.
     */
    public static class Builder extends Item.Builder<Armor> {

        @Override
        public Armor build() {
            return new Armor(this.getProficiency(), this.getAgility(), this.getStrength(),
                    this.getLife(), this.getResistance());
        }
    }
}
