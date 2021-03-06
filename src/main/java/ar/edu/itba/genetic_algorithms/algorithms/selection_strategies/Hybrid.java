package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implements the Hybrid Selection method
 * (i.e holds several {@link SelectionStrategy}, applying them in a given percentage)
 */
public class Hybrid implements SelectionStrategy {

    /**
     * Holds a list of {@link SelectionStrategy} and percentages for each.
     */
    private final List<SelectionStrategyAndPercentageWrapper> strategies;

    /**
     * Constructor.
     *
     * @param strategies The {@link SelectionStrategy} and percentages to use.
     */
    public Hybrid(List<SelectionStrategyAndPercentageWrapper> strategies) {
        if (Double.compare(1.0,
                strategies.stream().mapToDouble(SelectionStrategyAndPercentageWrapper::getPercentage).sum()) != 0) {
            throw new IllegalArgumentException("Percentages sum must be 1.");
        }
        strategies.forEach(strategy -> {
            if (strategy.getSelectionStrategy() instanceof Hybrid) {
                throw new IllegalArgumentException("Can not use hybrid selection strategy in the hybrid selection.");
            }
        });
        this.strategies = strategies;
    }

    /**
     * Constructor.
     *
     * @param strategies The {@link SelectionStrategy} and percentages to use.
     */
    public Hybrid(SelectionStrategyAndPercentageWrapper... strategies) {
        this(Arrays.stream(strategies).collect(Collectors.toList()));
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        final int sum = strategies.parallelStream().mapToInt(each -> (int) Math.round(k * each.getPercentage())).sum();
        if (sum != k) {
            Optional<SelectionStrategyAndPercentageWrapper> wrapper =
                    strategies.stream()
                            .max((o1, o2) -> Double.compare(o1.getPercentage(), o2.getPercentage()));
            if (wrapper.isPresent()) {
                wrapper.get().percentage = ((Math.round(k * wrapper.get().getPercentage())) + (double) (k - sum)) / k;
            }
        }

        return strategies.stream()
                .parallel()
                .map(each -> each.getSelectionStrategy()
                        .select(population, (int) Math.round(k * each.getPercentage())))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    /**
     * Class that wraps a {@link SelectionStrategy} and a percentage of selection over the chosen strategy.
     */
    public static final class SelectionStrategyAndPercentageWrapper {

        /**
         * The wrapped {@link SelectionStrategy}.
         */
        private final SelectionStrategy selectionStrategy;

        /**
         * The wrapped percentage.
         */
        private double percentage;

        /**
         * Constructor.
         *
         * @param selectionStrategy The wrapped {@link SelectionStrategy}.
         * @param percentage        The wrapped percentage.
         */
        public SelectionStrategyAndPercentageWrapper(SelectionStrategy selectionStrategy, double percentage) {
            this.selectionStrategy = selectionStrategy;
            this.percentage = percentage;
        }

        /**
         * @return The wrapped {@link SelectionStrategy}.
         */
        private SelectionStrategy getSelectionStrategy() {
            return selectionStrategy;
        }

        /**
         * @return The wrapped percentage.
         */
        private double getPercentage() {
            return percentage;
        }
    }
}
