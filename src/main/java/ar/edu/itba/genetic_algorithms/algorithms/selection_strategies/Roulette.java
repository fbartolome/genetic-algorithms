package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;


public class Roulette extends AccumulatedSelectionMethod {

    public List<Chromosome> select(Population population, int k) {
        List<Double> rand = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            rand.add(i, Math.random());
        }
        Multimap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for (Double d : rand) {
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(d, populationRelativeFitnesses));
        }
        return selectedChromosomes;
    }


}
