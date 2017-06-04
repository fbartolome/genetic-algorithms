package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;

public class Ranking extends AccumulatedSelectionMethod {

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Individual> individuals = population.getSortedIndividualsFromWorstToBest();
        Multimap<Individual, Double> accumProbabilities = ArrayListMultimap.create();
        int sum = sumation(individuals.size());
        double prevValue = 0;
        for (int i = 0; i < individuals.size(); i++) {
            prevValue =  prevValue + ((double)(i+1) / sum);
            accumProbabilities.put(individuals.get(i), prevValue);
        }

        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            double rand = Math.random();
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(rand, accumProbabilities));
        }

        return selectedChromosomes;
    }


    private int sumation(int num){
        int accum = 0;
        for(int i=1; i<=num; i++){
            accum+=i;
        }
        return accum;

    }

}

