package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.ArrayList;
import java.util.List;

public class Elite implements SelectionStrategy {

    @Override
    public List<Chromosome> select(Population population, int k) {
        List<Individual> individuals = population.getSortedIndividualsFromBestToWorst();
        List<Chromosome> selectedChromosomes = new ArrayList<>();
        for(int i = 0; i < k; i++){
            selectedChromosomes.add(individuals.get(i).getChromosome());
        }
        return selectedChromosomes;
    }

}
