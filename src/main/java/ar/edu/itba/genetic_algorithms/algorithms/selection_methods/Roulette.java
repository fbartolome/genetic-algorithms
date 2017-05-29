package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO probar
public class Roulette extends AccumulatedSelectionMethod{

    public List<Chromosome> select(Population population, int k){
        List<Double> rand = new ArrayList<>();
        for(int i = 0; i<k; i++){
            rand.add(i, Math.random());
        }
        HashMap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for(Double d : rand){
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(d, populationRelativeFitnesses));
        }
        return selectedChromosomes;
    }



}
