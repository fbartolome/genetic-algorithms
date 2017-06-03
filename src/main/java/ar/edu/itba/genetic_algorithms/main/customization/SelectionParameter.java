package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.Arrays;

/**
 * Parameter for choosing a {@link SelectionStrategy}.
 */
@JsonSubTypes({@JsonSubTypes.Type(value = SelectionParameter.BoltzmannSelection.class, name = "BOLTZMANN"),
        @JsonSubTypes.Type(value = SelectionParameter.NoParametersSelection.class, name = "ELITE"),
        @JsonSubTypes.Type(value = SelectionParameter.HybridSelection.class, name = "HYBRID"),
        @JsonSubTypes.Type(value = SelectionParameter.NoParametersSelection.class, name = "RANKING"),
        @JsonSubTypes.Type(value = SelectionParameter.NoParametersSelection.class, name = "ROULETTE"),
        @JsonSubTypes.Type(value = SelectionParameter.TournamentDeterministicSelection.class,
                name = "TOURNAMENT_DETERMINISTIC"),
        @JsonSubTypes.Type(value = SelectionParameter.NoParametersSelection.class, name = "TOURNAMENT_PROBABILISTIC"),
        @JsonSubTypes.Type(value = SelectionParameter.UniversalSelection.class, name = "UNIVERSAL"),})
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "strategy",
        visible = true)
public abstract class SelectionParameter {

    /**
     * Indicates the strategy to be used.
     */
    @JsonProperty
    private SelectionType strategy;

    /**
     * The "k" parameter.
     */
    @JsonProperty
    private int k;

    /**
     * @return The {@link SelectionStrategy} created from the sent parameters.
     */
    @JsonIgnore
    public abstract SelectionStrategy getStrategy();

    /**
     * @return The "k" parameter.
     */
    public int getK() {
        return k;
    }

    /**
     * @return The strategy enum.
     */
    @JsonIgnore
    /* package */ SelectionType getStrategyEnum() {
        return strategy;
    }

    /**
     * Parameters for choosing any of those {@link SelectionStrategy} with no parameters
     * (i.e {@link Elite}, {@link Ranking}, {@link Roulette}, and {@link TournamentProbabilistic}).
     */
    /* package */ static final class NoParametersSelection extends SelectionParameter {
        // Implementation for those strategies without parameters.

        @Override
        public SelectionStrategy getStrategy() {
            SelectionType type = getStrategyEnum();
            if (type == SelectionType.BOLTZMANN
                    || type == SelectionType.HYBRID
                    || type == SelectionType.TOURNAMENT_DETERMINISTIC
                    || type == SelectionType.UNIVERSAL) {
                throw new IllegalArgumentException("The " + type + " strategy requires one or more parameters");
            }
            return type.getStrategy();
        }
    }

    /**
     * Parameters for {@link Boltzmann} selection strategy.
     */
    /* package */ static final class BoltzmannSelection extends SelectionParameter {

        /**
         * The {@link Boltzmann} "n" parameter.
         */
        @JsonProperty
        private Double n;

        @Override
        public SelectionStrategy getStrategy() {
            return getStrategyEnum().getStrategy(n);
        }
    }

    /**
     * Parameters for {@link TournamentDeterministic} selection strategy.
     */
    /* package */ static final class TournamentDeterministicSelection extends SelectionParameter {

        /**
         * The {@link TournamentDeterministic} "m" parameter.
         */
        @JsonProperty
        private Integer m;

        @Override
        public SelectionStrategy getStrategy() {
            return getStrategyEnum().getStrategy(m);
        }
    }

    /**
     * Parameters for {@link Universal} selection strategy.
     */
    /* package */ static final class UniversalSelection extends SelectionParameter {

        /**
         * The {@link Universal} "j" parameter.
         */
        @JsonProperty
        private Double j;

        @Override
        public SelectionStrategy getStrategy() {
            return getStrategyEnum().getStrategy(j);
        }
    }

    /**
     * Parameters for {@link Hybrid} selection strategy.
     */
    /* package */ static final class HybridSelection extends SelectionParameter {

        /**
         * An array of {@link SelectionAndPercentageWrapper} holding data for initializing
         * an {@link Hybrid} selection strategy.
         */
        @JsonProperty
        private SelectionAndPercentageWrapper[] methods;

        @Override
        public SelectionStrategy getStrategy() {
            return getStrategyEnum().getStrategy(Arrays.stream(methods)
                    .map(wrapper -> new Hybrid.SelectionStrategyAndPercentageWrapper(wrapper.getMethod().getStrategy(),
                            wrapper.getPercentage()))
                    .toArray());
        }

        /**
         * Class wrapping a {@link SelectionParameter} and a percentage.
         * No constructor and no setter as it is initialized by Jackson.
         * Getters are private as data is only accessed within the {@link SelectionParameter} class.
         */
        private static final class SelectionAndPercentageWrapper {

            /**
             * The chosen selection method.
             */
            @JsonProperty
            private SelectionParameter method;

            /**
             * The percentage applied to the chosen method.
             */
            @JsonProperty
            private Double percentage;

            /**
             * @return The chosen selection method.
             */
            private SelectionParameter getMethod() {
                return method;
            }

            /**
             * @return The percentage applied to the chosen method.
             */
            private Double getPercentage() {
                return percentage;
            }
        }
    }


    /**
     * Enum indicating types of selections.
     */
    private enum SelectionType {

        BOLTZMANN {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new Boltzmann((double) parameters[0]);
            }
        },
        ELITE {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new Elite();
            }
        },
        HYBRID {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new Hybrid(Arrays.copyOf(parameters, parameters.length,
                        Hybrid.SelectionStrategyAndPercentageWrapper[].class));
            }
        },
        RANKING {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new Ranking();
            }
        },
        ROULETTE {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new Roulette();
            }
        },
        TOURNAMENT_DETERMINISTIC {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new TournamentDeterministic((int) parameters[0]);
            }
        },
        TOURNAMENT_PROBABILISTIC {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
                return new TournamentProbabilistic();
            }
        },
        UNIVERSAL {
            @Override
            public SelectionStrategy getStrategy(Object... parameters) {
//                return new Universal((double) parameters[0]);
                return new Universal(); // TODO: check no parameters
            }
        };

        /**
         * Returns the {@link SelectionStrategy} being represented by the enum value.
         *
         * @param parameters The parameters needed to initialize the returned {@link SelectionStrategy}.
         * @return The {@link SelectionStrategy} being represented by the enum value.
         */
        protected abstract SelectionStrategy getStrategy(Object... parameters);
    }
}
