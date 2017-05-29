package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Ranking extends AccumulatedSelectionMethod {
//TODO CHECK

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Individual> individuals = population.getSortedIndividualsFromWorstToBest();
        HashMap<Individual, Double> accumProbabilities = new HashMap<>();
        double prevValue = 0;
        for (int i = 0; i < individuals.size(); i++) {
            accumProbabilities.put(individuals.get(i), prevValue + ((double) i / individuals.size()));
            prevValue = (double) i / individuals.size();
        }

        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            double rand = Math.random();
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(rand, accumProbabilities));
        }
        return selectedChromosomes;
    }


}

