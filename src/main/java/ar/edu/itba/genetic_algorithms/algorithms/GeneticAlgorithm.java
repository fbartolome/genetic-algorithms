package ar.edu.itba.genetic_algorithms.algorithms;

import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;

public class GeneticAlgorithm {

    private final EndingCondition endingCondition;

    public GeneticAlgorithm(EndingCondition endingCondition){
        this.endingCondition = endingCondition;
    }


}
