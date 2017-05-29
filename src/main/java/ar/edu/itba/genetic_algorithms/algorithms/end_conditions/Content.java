package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.Population;

public class Content implements EndingCondition {

    private final int generations;

    public Content(int generations){
        this.generations = generations;
    }

    @Override
    public boolean isSatisfied(Population population) {
        return sameFitnessCount(population, population.bestIndividual().getFitness()) >= generations;
    }

    private int sameFitnessCount(Population population, double fitness){
        if(population == null){
            return -1;
        } else if (population.bestIndividual().getFitness() != fitness){
            return 0;
        } else {
            return 1 + sameFitnessCount(population.getPreviousPopulation(), fitness);
        }
    }
}
