package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.List;

public class TournamentDeterministic implements SelectionMethod {

    private int m = 3;

    public TournamentDeterministic(int m) {
        this.m = m;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Chromosome> selectedChromosome = new ArrayList<>();
        List<Individual> individuals = population.getIndividuals();

        for(int j= 0; j<k; j++){

            List<Individual> individualCandidates = new ArrayList<>();
            for(int i = 0; i<m; i++){
                int index = (int) (Math.random() * population.getPopulationSize());
                individualCandidates.add(individuals.get(index));
            }
            selectedChromosome.add(bestChromosome(individualCandidates));
        }
        return selectedChromosome;
    }

    private Chromosome bestChromosome(List<Individual> individualList){

        Individual bestIndividual  = individualList.get(0);

        for(Individual individual : individualList){
            if(individual.getFitness() > bestIndividual.getFitness()){
                bestIndividual = individual;
            }
        }
        return bestIndividual.getChromosome();
    }

}
