package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Roulette implements SelectionMethod {

    public List<Chromosome> select(Population population, int k){
        List<Double> rand = new ArrayList<Double>();
        for(int i = 0; i<k; i++){
            rand.add(i, Math.random());
        }
        HashMap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        List<Chromosome> selectedChromosomes = new ArrayList<Chromosome>();

        for(Double d : rand){
            selectedChromosomes.add(selectChromosome(d, populationRelativeFitnesses));
        }
        return selectedChromosomes;
    }

    private Chromosome selectChromosome(Double rand, HashMap<Individual, Double> individualList){
        for(Map.Entry<Individual, Double> e : individualList.entrySet()){
            if(e.getValue() > rand){
                return e.getKey().getChromosome();
            }
        }
        return null;
    }

}
