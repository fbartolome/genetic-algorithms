package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;

import java.util.HashMap;
import java.util.Map;

public abstract class AccumulatedSelectionMethod implements SelectionMethod{

    protected Chromosome selectChromosomeOnAccumulatedFitnessProbability(Double rand, HashMap<Individual, Double> individualList){
        for(Map.Entry<Individual, Double> e : individualList.entrySet()){
            if(e.getValue() > rand){
                return e.getKey().getChromosome();
            }
        }
        return null;
    }
}
