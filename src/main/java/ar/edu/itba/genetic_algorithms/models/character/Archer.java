package ar.edu.itba.genetic_algorithms.models.character;

/**
 * This class represents an archer {@link Character}.
 */
public class Archer extends Character {
    /**
     * Constructor.
     *
     * @param height      The archer's height.
     * @param equipment   The archer's equipment.
     * @param multipliers The archer's multipliers for the items' stats.
     */
    private Archer(double height, Equipment equipment, Multipliers multipliers) {
        super(height, equipment, multipliers);
    }

    @Override
    public double getFitness() {
        return 0.9 * getAttack() + 0.1 * getDefense();
    }

    /**
     * Builder for {@link Archer}.
     */
    public static class Builder extends Character.Builder<Archer> {

        @Override
        public Archer build() {
            return new Archer(this.getHeight(), this.getEquipment(), this.getMultipliers());
        }

        @Override
        public Character.Builder<Archer> createNewInstance() {
            return new Builder();
        }
    }
}
