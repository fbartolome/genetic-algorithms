package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.List;

public class Elite implements SelectionMethod{


    //TODO borrar cuando se haya adaptado el otro
    @Override
    public List<Chromosome> select(Population population, int k) {
        return null;
    }

//    @Override
//    public List<ChromosomePair> select(Population population, int k) {
//        List<Individual> individuals = population.getIndividuals();
//        individuals.sort(
//                (Individual i1, Individual i2) -> -(new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()))));
//
//        List<ChromosomePair> selectedIndividuals = new LinkedList<>();
//        //TODO: make variable the number of selected chromosomes
//        selectedIndividuals.add(new ChromosomePair(individuals.get(0).getChromosome(), individuals.get(1).getChromosome()));
//        selectedIndividuals.add(new ChromosomePair(individuals.get(2).getChromosome(), individuals.get(3).getChromosome()));
//        return selectedIndividuals;
//    }

}
