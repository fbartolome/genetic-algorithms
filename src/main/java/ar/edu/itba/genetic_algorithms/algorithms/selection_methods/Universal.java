package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//TODO: probar
public class Universal implements SelectionMethod {

    //todo: parametrizar y probar
    private double j = 0.4;

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
