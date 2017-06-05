package ar.edu.itba.genetic_algorithms.models.alleles;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainer;
import ar.edu.itba.genetic_algorithms.models.item.Item;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Represents an item's repository.
 */
public class ItemsRepository implements AlleleContainer {

    /**
     * Stores the items.
     */
    private final Map<Integer, Item> items;


    /**
     * Constructor.
     */
    public ItemsRepository() {
        this.items = new HashMap<>();
    }

    @Override
    public Object getSpecificAllele(int alleleNumber) {
        return items.get(alleleNumber);
    }

    /**
     * Adds an item to the repository.
     *
     * @param id   The id of the {@link Item}.
     * @param item The {@link Item} to be stored.
     */
    public void addItem(int id, Item item) {
        items.put(id, item);
    }

    /**
     * Gets an {@link Item}.
     *
     * @param id The id of the {@link Item}.
     * @return The {@link Item} with the given {@code id}, or {@code null} otherwise.
     */
    public Item getItem(int id) {
        return items.get(id);
    }


    @Override
    public Object getAllele() {
        return getAllele(new Random());
    }

    @Override
    public Object getAllele(Random random) {
        return getItem(random.nextInt(items.size()));
    }

    @Override
    public int getAmountOfAlleles() {
        return items.size();
    }

    @Override
    public List<Integer> getAllelesNumbers() {
        return items.keySet().stream().collect(Collectors.toList());
    }
}
