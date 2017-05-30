package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;

public class HybridSelectionStrategy implements SelectionStrategy {

    private final SelectionStrategy selectionStrategy1;

    private final double percentage1;

    private final SelectionStrategy selectionStrategy2;

    private final double percentage2;

    public HybridSelectionStrategy(SelectionStrategy selectionStrategy1, double percentage1, SelectionStrategy selectionStrategy2, double percentage2){
        if(percentage1 < 0 || percentage1 > 1 || percentage2 < 0 || percentage2 > 1){
            throw new IllegalArgumentException("Percentages should be between 0 and 1.");
        } else if(percentage1 + percentage2 != 1){
            throw new IllegalArgumentException("Percentages should add 1.");
        }
        this.selectionStrategy1 = selectionStrategy1;
        this.percentage1 = percentage1;
        this.selectionStrategy2 = selectionStrategy2;
        this.percentage2 = percentage2;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {
        List<Chromosome> selected = selectionStrategy1.select(population,(int)(k*percentage1));
        selected.addAll(selectionStrategy2.select(population,(int)(k*percentage2)));
        return selected;
    }
}
