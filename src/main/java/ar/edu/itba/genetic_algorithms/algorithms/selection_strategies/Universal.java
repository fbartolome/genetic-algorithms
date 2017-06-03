package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

//TODO: probar
public class Universal extends AccumulatedSelectionMethod {

    @Override
    public List<Chromosome> select(Population population, int k) {
        List<Double> randj = new ArrayList<>();

        for(int j = 1; j<=k; j++){

            double rj =  (Math.random() + j - 1)/k;
            randj.add(rj);
          //  System.out.println(rj);

        }

        Multimap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        for(Double d : randj){
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(d, populationRelativeFitnesses));
        }
        return selectedChromosomes;
    }


}
