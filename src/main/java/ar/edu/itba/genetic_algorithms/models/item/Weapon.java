package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Represents a {@link Weapon} {@link Item}.
 */
public class Weapon extends Item {
    /**
     * Constructor.
     *
     * @param proficiency Item's proficiency.
     * @param agility     Item's agility.
     * @param strength    Item's strength.
     * @param life        Item's life.
     * @param resistance  Item's resistance.
     */
    private Weapon(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }

    /**
     * Builds the {@link Weapon}.
     */
    public static class Builder extends Item.Builder<Weapon> {

        @Override
        public Weapon build() {
            return new Weapon(this.getProficiency(), this.getAgility(), this.getStrength(),
                    this.getLife(), this.getResistance());
        }
    }
}
