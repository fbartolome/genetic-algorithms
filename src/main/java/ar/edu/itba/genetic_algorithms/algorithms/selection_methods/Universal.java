package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

//TODO: probar
public class Universal extends AccumulatedSelectionMethod {

    private double j = 0.4;

    public Universal(double j) {
        this.j = j;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {
        List<Double> randj = new ArrayList<>();
        for(int i = 0; i<k; i++){
            double rj =  (Math.random() + j - 1)/k;
            randj.add(rj);
        }
        HashMap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        for(Double d : randj){
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(d, populationRelativeFitnesses));
        }
        return selectedChromosomes;
    }


}
