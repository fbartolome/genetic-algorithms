package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Parameter for choosing an {@link EndingCondition}.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = EndingConditionParameter.ContentEnd.class, name = "CONTENT"),
        @JsonSubTypes.Type(value = EndingConditionParameter.MaxGenerationsEnd.class, name = "MAX_GENERATIONS"),
        @JsonSubTypes.Type(value = EndingConditionParameter.OptimumEnd.class, name = "OPTIMUM"),
        @JsonSubTypes.Type(value = EndingConditionParameter.StructureEnd.class, name = "STRUCTURE"),})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "strategy",
        visible = true)
public abstract class EndingConditionParameter {

    /**
     * Indicates the strategy to be used.
     */
    @JsonProperty
    private EndingConditionType strategy;

    /**
     * @return The {@link EndingCondition} created from the sent parameters.
     */
    @JsonIgnore
    public abstract EndingCondition getStrategy();

    /**
     * @return The strategy enum.
     */
    @JsonIgnore
    /* package */ EndingConditionType getStrategyEnum() {
        return strategy;
    }


    /**
     * Parameters for {@link Content} ending condition strategy.
     */
    /* package */ static final class ContentEnd extends EndingConditionParameter {

        /**
         * The {@link Content} "generations" parameter.
         */
        @JsonProperty
        private Integer generations;

        @Override
        public EndingCondition getStrategy() {
            return getStrategyEnum().getStrategy(generations);
        }
    }

    /**
     * Parameters for {@link MaxNumberGenerations} ending condition strategy.
     */
    /* package */ static final class MaxGenerationsEnd extends EndingConditionParameter {

        /**
         * The {@link MaxNumberGenerations} "maxGenerations" parameter.
         */
        @JsonProperty
        private Integer maxGenerations;

        @Override
        public EndingCondition getStrategy() {
            return getStrategyEnum().getStrategy(maxGenerations);
        }
    }

    /**
     * Parameters for {@link Optimum} ending condition strategy.
     */
    /* package */ static final class OptimumEnd extends EndingConditionParameter {

        /**
         * The {@link Optimum} "optimumFitness" parameter.
         */
        @JsonProperty
        private Double optimumFitness;

        @Override
        public EndingCondition getStrategy() {
            return getStrategyEnum().getStrategy(optimumFitness);
        }
    }

    /**
     * Parameters for {@link Structure} ending condition strategy.
     */
    /* package */ static final class StructureEnd extends EndingConditionParameter {

        /**
         * The {@link Structure} "tolerance" parameter.
         */
        @JsonProperty
        private Double tolerance;

        @Override
        public EndingCondition getStrategy() {
            return getStrategyEnum().getStrategy(tolerance);
        }
    }


    /**
     * Enum indicating types of end conditions.
     */
    private enum EndingConditionType {

        CONTENT {
            @Override
            protected EndingCondition getStrategy(Object... parameters) {
                return new Content((int) parameters[0]);
            }
        },
        MAX_GENERATIONS {
            @Override
            protected EndingCondition getStrategy(Object... parameters) {
                return new MaxNumberGenerations((int) parameters[0]);
            }
        },
        OPTIMUM {
            @Override
            protected EndingCondition getStrategy(Object... parameters) {
                return new Optimum((double) parameters[0]);
            }
        },
        STRUCTURE {
            @Override
            protected EndingCondition getStrategy(Object... parameters) {
                return new Structure((double) parameters[0]);
            }
        };

        /**
         * Returns the {@link EndingCondition} being represented by the enum value.
         *
         * @param parameters The parameters needed to initialize the returned {@link EndingCondition}.
         * @return The {@link EndingCondition} being represented by the enum value.
         */
        protected abstract EndingCondition getStrategy(Object... parameters);
    }
}
