package ar.edu.itba.genetic_algorithms.models.character;

/**
 * This class represents an assassin {@link Character}.
 */
public class Assassin extends Character {
    /**
     * Constructor.
     *
     * @param height      The assassin's height.
     * @param equipment   The assassin's equipment.
     * @param multipliers The assassin's multipliers for the items' stats.
     */
    private Assassin(double height, Equipment equipment, Multipliers multipliers) {
        super(height, equipment, multipliers);
    }

    @Override
    public double getFitness() {
        return 0.7 * getAttack() + 0.3 * getDefense();
    }

    /**
     * Builder for {@link Assassin}.
     */
    public static class Builder extends Character.Builder<Assassin> {

        @Override
        public Assassin build() {
            return new Assassin(this.getHeight(), this.getEquipment(), this.getMultipliers());
        }
    }
}
