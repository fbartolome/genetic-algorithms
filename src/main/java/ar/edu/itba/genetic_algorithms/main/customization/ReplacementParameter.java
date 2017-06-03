package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy1;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy2;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy3;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Parameter for choosing a {@link ReplacementStrategy}.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = ReplacementParameter.Replacement1.class, name = "REPLACEMENT_1"),
        @JsonSubTypes.Type(value = ReplacementParameter.Replacement2.class, name = "REPLACEMENT_2"),
        @JsonSubTypes.Type(value = ReplacementParameter.Replacement3.class, name = "REPLACEMENT_3"),})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "strategy",
        visible = true)
public abstract class ReplacementParameter {

    /**
     * Indicates the strategy to be used.
     */
    @JsonProperty
    private ReplacementType strategy;

    /**
     * @return The {@link ReplacementStrategy} created from the sent parameters.
     */
    @JsonIgnore
    public abstract ReplacementStrategy getStrategy();

    /**
     * @return The strategy enum.
     */
    @JsonIgnore
    /* package */ ReplacementType getStrategyEnum() {
        return strategy;
    }


    /**
     * Parameters for {@link ReplacementStrategy} replacement strategy.
     */
    /* package */ static final class Replacement1 extends ReplacementParameter {

        @Override
        public ReplacementStrategy getStrategy() {
            return getStrategyEnum().getStrategy();
        }
    }

    /**
     * Parameters for {@link ReplacementStrategy2} replacement strategy.
     */
    /* package */ static final class Replacement2 extends ReplacementParameter {

        /**
         * The {@link ReplacementStrategy2} "oldPopulationSelectionStrategy" parameter.
         */
        @JsonProperty
        private SelectionParameter oldPopulationSelection;

        @Override
        public ReplacementStrategy getStrategy() {
            return getStrategyEnum().getStrategy(oldPopulationSelection.getStrategy());
        }
    }

    /**
     * Parameters for {@link ReplacementStrategy3} replacement strategy.
     */
    /* package */ static final class Replacement3 extends ReplacementParameter {

        /**
         * The {@link ReplacementStrategy3} "oldPopulationSelectionStrategy" parameter.
         */
        @JsonProperty
        private SelectionParameter oldPopulationSelection;

        /**
         * The {@link ReplacementStrategy3} "newPopulationSelectionStrategy" parameter.
         */
        @JsonProperty
        private SelectionParameter newPopulationSelection;

        @Override
        public ReplacementStrategy getStrategy() {
            return getStrategyEnum().getStrategy(oldPopulationSelection.getStrategy(),
                    newPopulationSelection.getStrategy());
        }
    }


    /**
     * Enum indicating types of replacements.
     */
    private enum ReplacementType {

        REPLACEMENT_1 {
            @Override
            public ReplacementStrategy getStrategy(Object... parameters) {
                return new ReplacementStrategy1();
            }
        },
        REPLACEMENT_2 {
            @Override
            public ReplacementStrategy getStrategy(Object... parameters) {
                return new ReplacementStrategy2((SelectionStrategy) parameters[0]);
            }
        },
        REPLACEMENT_3 {
            @Override
            public ReplacementStrategy getStrategy(Object... parameters) {
                return new ReplacementStrategy3((SelectionStrategy) parameters[0], (SelectionStrategy) parameters[1]);
            }
        };

        /**
         * Returns the {@link ReplacementStrategy} being represented by the enum value.
         *
         * @param parameters The parameters needed to initialize the returned {@link ReplacementStrategy}.
         * @return The {@link ReplacementStrategy} being represented by the enum value.
         */
        protected abstract ReplacementStrategy getStrategy(Object... parameters);
    }
}
