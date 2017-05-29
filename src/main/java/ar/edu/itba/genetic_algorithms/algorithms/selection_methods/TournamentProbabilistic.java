package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.util.ArrayList;
import java.util.List;

public class TournamentProbabilistic implements SelectionStrategy {

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Chromosome> selectedChromosomes = new ArrayList<>();
        for(int i=0; i<k; i++){
            Individual i1 = population.getIndividuals().get((int) Math.random() * population.getPopulationSize());
            Individual i2 = population.getIndividuals().get((int) Math.random() * population.getPopulationSize());
            double r = Math.random();

            //add the most suitable chromosome
            if(r < 0.75){
                if(i1.getFitness() > i2.getFitness()){
                    selectedChromosomes.add(i1.getChromosome());
                }else{
                    selectedChromosomes.add(i2.getChromosome());
                }
            //add the least suitable chromosome
            }else{
                if(i1.getFitness() > i2.getFitness()){
                    selectedChromosomes.add(i2.getChromosome());
                }else{
                    selectedChromosomes.add(i1.getChromosome());
                }
            }
        }
        return selectedChromosomes;
    }
}
