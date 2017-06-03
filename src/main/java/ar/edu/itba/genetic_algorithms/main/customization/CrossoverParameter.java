package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Parameter for choosing a {@link CrossoverParameter}.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = CrossoverParameter.NoParametersCrossover.class, name = "ONE_POINT"),
        @JsonSubTypes.Type(value = CrossoverParameter.NoParametersCrossover.class, name = "TWO_POINT"),
        @JsonSubTypes.Type(value = CrossoverParameter.UniformCrossoverParameters.class, name = "UNIFORM"),
        @JsonSubTypes.Type(value = CrossoverParameter.NoParametersCrossover.class, name = "ANNULAR"),})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "strategy",
        visible = true)
public abstract class CrossoverParameter {

    /**
     * Indicates the strategy to be used.
     */
    @JsonProperty
    private CrossoverType strategy;

    /**
     * @return The {@link CrossoverStrategy} created from the sent parameters.
     */
    @JsonIgnore
    public abstract CrossoverStrategy getStrategy();

    /**
     * @return The strategy enum.
     */
    @JsonIgnore
    /* package */ CrossoverType getStrategyEnum() {
        return strategy;
    }

    /**
     * Parameters for choosing any {@link CrossoverStrategy} (all of them do not need parameters)
     * (i.e {@link OnePointCrossover}, {@link TwoPointCrossover}, and {@link UniformCrossover}).
     */
    /* package */ static final class NoParametersCrossover extends CrossoverParameter {
        // Implementation for those strategies without parameters.

        @Override
        public CrossoverStrategy getStrategy() {
            return getStrategyEnum().getStrategy();
        }
    }

    /**
     * Parameters for {@link UniformCrossover} strategy.
     */
    /* package */ static final class UniformCrossoverParameters extends CrossoverParameter {

        /**
         * The {@link UniformCrossover} "p" parameter.
         */
        @JsonProperty
        private Double p;

        @Override
        public CrossoverStrategy getStrategy() {
            return getStrategyEnum().getStrategy(p);
        }
    }


    /**
     * Enum indicating types of crossovers.
     */
    private enum CrossoverType {

        ONE_POINT {
            @Override
            public CrossoverStrategy getStrategy(Object... parameters) {
                return new OnePointCrossover();
            }
        },
        TWO_POINT {
            @Override
            public CrossoverStrategy getStrategy(Object... parameters) {
                return new TwoPointCrossover();
            }
        },
        UNIFORM {
            @Override
            public CrossoverStrategy getStrategy(Object... parameters) {
                return new UniformCrossover((double) parameters[0]);
            }
        },
        ANNULAR {
            @Override
            protected CrossoverStrategy getStrategy(Object... parameters) {
                return new AnnularCrossover();
            }
        };

        /**
         * Returns the {@link CrossoverStrategy} being represented by the enum value.
         *
         * @param parameters The parameters needed to initialize the returned {@link CrossoverStrategy}.
         * @return The {@link CrossoverStrategy} being represented by the enum value.
         */
        protected abstract CrossoverStrategy getStrategy(Object... parameters);
    }
}
