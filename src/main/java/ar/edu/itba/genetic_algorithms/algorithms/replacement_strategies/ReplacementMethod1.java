package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.List;

//TODO: not actual replacement method
public class ReplacementMethod1 implements ReplacementMethod{

    @Override
    public List<Individual> replace(Population actualPopulation, List<Individual> offsprings) {
        int offspringsSize = offsprings.size();
        List<Individual> individuals = actualPopulation.getIndividuals();
        for(int i = 0; i < offspringsSize; i++){
            individuals.remove(0);
            individuals.add(offsprings.get(i));
        }
        return individuals;
    }
}
