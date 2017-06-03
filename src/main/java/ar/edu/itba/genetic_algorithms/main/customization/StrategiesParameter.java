package ar.edu.itba.genetic_algorithms.main.customization;

import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.CrossoverStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Parameter for choosing strategies (i.e {@link SelectionStrategy}, {@link CrossoverStrategy},
 * {@link MutationStrategy}, {@link ReplacementStrategy}, and {@link EndingCondition}).
 */
public class StrategiesParameter {

    /**
     * The {@link SelectionStrategy} parameter.
     */
    @JsonProperty
    private SelectionParameter selection;

    /**
     * The {@link CrossoverStrategy} parameter.
     */
    @JsonProperty
    private CrossoverParameter crossover;

    /**
     * The {@link MutationStrategy} parameter.
     */
    @JsonProperty
    private MutationParameter mutation;

    /**
     * The {@link ReplacementStrategy} parameter.
     */
    @JsonProperty
    private ReplacementParameter replacement;

    /**
     * The {@link EndingCondition} parameter.
     */
    @JsonProperty
    private EndingConditionParameter ending;


    /**
     * @return The {@link SelectionStrategy} parameter.
     */
    public SelectionParameter getSelection() {
        return selection;
    }

    /**
     * @return The {@link CrossoverStrategy} parameter.
     */
    public CrossoverParameter getCrossover() {
        return crossover;
    }

    /**
     * @return The {@link MutationStrategy} parameter.
     */
    public MutationParameter getMutation() {
        return mutation;
    }

    /**
     * @return The {@link ReplacementStrategy} parameter.
     */
    public ReplacementParameter getReplacement() {
        return replacement;
    }

    /**
     * @return The {@link EndingCondition} parameter.
     */
    public EndingConditionParameter getEnding() {
        return ending;
    }
}
