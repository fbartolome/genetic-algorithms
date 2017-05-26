package ar.edu.itba.genetic_algorithms.models.character;

/**
 * This class represents a warrior {@link Character}.
 */
public class Warrior extends Character {
    /**
     * Constructor.
     *
     * @param height      The warrior's height.
     * @param equipment   The warrior's equipment.
     * @param multipliers The warrior's multipliers for the items' stats.
     */
    private Warrior(double height, Equipment equipment, Multipliers multipliers) {
        super(height, equipment, multipliers);
    }

    @Override
    public double getFitness() {
        return 0.6 * getAttack() + 0.4 * getDefense();
    }
    
    /**
     * Builder for {@link Warrior}.
     */
    public static class Builder extends Character.Builder<Warrior> {

        @Override
        public Warrior build() {
            return new Warrior(this.getHeight(), this.getEquipment(), this.getMultipliers());
        }
    }

}
