package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.IndividualPair;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.LinkedList;
import java.util.List;

public class Elite implements SelectionMethod{

    @Override
    public List<IndividualPair> select(Population population) {
        List<Individual> individuals = population.getIndividuals();
        individuals.sort(
                (Individual i1, Individual i2) -> (new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()))));

        List<IndividualPair> selectedIndividuals = new LinkedList<>();
        selectedIndividuals.add(new IndividualPair(individuals.get(0), individuals.get(1)));
        selectedIndividuals.add(new IndividualPair(individuals.get(2), individuals.get(3)));
        return selectedIndividuals;
    }
}
