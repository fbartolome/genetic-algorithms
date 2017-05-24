package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Created by jbellini on 24/5/17.
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
}
