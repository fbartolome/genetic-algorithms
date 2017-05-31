package ar.edu.itba.genetic_algorithms.algorithms.end_conditions;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

public class Structure implements EndingCondition {

    private final double tolerance;

    public Structure(double tolerance) {
        if(tolerance > 1 || tolerance < 0)
            throw new IllegalArgumentException("Tolerance should be between 0 and 1.");
        this.tolerance = tolerance;
    }

    @Override
    public boolean isSatisfied(Population population) {
        if(population.getPreviousPopulation() == null)
            return false;

        int sameIndividuals = 0;
        for(Individual newIndividual : population.getIndividuals()){
            boolean found = false;
            for(int i = 0; i < population.getPreviousPopulation().getIndividuals().size() && !found; i++){
                Individual oldIndividual = population.getPreviousPopulation().getIndividuals().get(i);
                if(newIndividual.equals(oldIndividual)){
                    found = true;
                    sameIndividuals++;
                }
            }
        }

        return tolerance < sameIndividuals/population.getPopulationSize();
    }
}
