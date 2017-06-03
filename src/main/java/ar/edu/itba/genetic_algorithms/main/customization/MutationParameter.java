package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.UniformMutationStrategy;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Parameter for choosing a {@link MutationStrategy}.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = MutationParameter.NoParametersMutation.class, name = "UNIFORM"),})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "strategy",
        visible = true)
public abstract class MutationParameter {

    /**
     * Indicates the strategy to be used.
     */
    @JsonProperty
    private MutationType strategy;

    /**
     * The "pm" parameter.
     */
    @JsonProperty
    private double pm;

    /**
     * @return The {@link MutationStrategy} created from the sent parameters.
     */
    @JsonIgnore
    public abstract MutationStrategy getStrategy();

    /**
     * @return The "pm" parameter.
     */
    public double getPm() {
        return pm;
    }

    /**
     * @return The strategy enum.
     */
    @JsonIgnore
    /* package */ MutationType getStrategyEnum() {
        return strategy;
    }

    /**
     * Parameters for choosing any {@link MutationStrategy} (all of them do not need parameters)
     * (i.e {@link UniformMutationStrategy}).
     */
    /* package */ static final class NoParametersMutation extends MutationParameter {
        // Implementation for those strategies without parameters.

        @Override
        public MutationStrategy getStrategy() {
            return getStrategyEnum().getStrategy();
        }
    }


    /**
     * Enum indicating types of mutations.
     */
    private enum MutationType {

        UNIFORM {
            @Override
            public MutationStrategy getStrategy(Object... parameters) {
                return new UniformMutationStrategy();
            }
        };

        /**
         * Returns the {@link MutationStrategy} being represented by the enum value.
         *
         * @param parameters The parameters needed to initialize the returned {@link MutationStrategy}.
         * @return The {@link MutationStrategy} being represented by the enum value.
         */
        protected abstract MutationStrategy getStrategy(Object... parameters);
    }
}
