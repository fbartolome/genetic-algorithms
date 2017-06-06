package ar.edu.itba.genetic_algorithms.models.character;

/**
 * This class represents a guard {@link Character}.
 */
public class Guard extends Character {
    /**
     * Constructor.
     *
     * @param height      The guard's height.
     * @param equipment   The guard's equipment.
     * @param multipliers The guard's multipliers for the items' stats.
     */
    private Guard(double height, Equipment equipment, Multipliers multipliers) {
        super(height, equipment, multipliers);
    }

    @Override
    public double getFitness() {
        return 0.1 * getAttack() + 0.9 * getDefense();
    }

    /**
     * Builder for {@link Guard}.
     */
    public static class Builder extends Character.Builder<Guard> {

        @Override
        public Guard build() {
            return new Guard(this.getHeight(), this.getEquipment(), this.getMultipliers());
        }

        @Override
        public Character.Builder<Guard> createNewInstance() {
            return new Builder();
        }
    }
}
