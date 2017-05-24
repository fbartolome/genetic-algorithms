package ar.edu.itba.genetic_algorithms.models.item;

/**
 * Created by jbellini on 24/5/17.
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
    protected Weapon(double proficiency, double agility, double strength, double life, double resistance) {
        super(proficiency, agility, strength, life, resistance);
    }
}
