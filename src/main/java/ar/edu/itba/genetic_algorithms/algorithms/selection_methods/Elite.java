package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.List;

public class Elite implements SelectionMethod{

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
