package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.Population;

public class MaxNumberGenerations implements EndingCondition {

    private final int maxGenerations;

    public MaxNumberGenerations(int maxGenerations){
        this.maxGenerations = maxGenerations;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return population.getGeneration() >= maxGenerations;
    }
}
