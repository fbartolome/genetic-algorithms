package ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.api.IndividualCreator;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import ar.edu.itba.genetic_algorithms.models.character.Archer;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ReplacementStrategy3 implements ReplacementStrategy {

    private final SelectionStrategy oldPopulationSelectionStrategy;

    private final SelectionStrategy newPopulationSelectionStrategy;

    private final IndividualCreator individualCreator;

    public ReplacementStrategy3(SelectionStrategy oldPopulationSelectionStrategy, SelectionStrategy newPopulationSelectionStrategy, IndividualCreator individualCreator){
        this.oldPopulationSelectionStrategy = oldPopulationSelectionStrategy;
        this.newPopulationSelectionStrategy = newPopulationSelectionStrategy;
        this.individualCreator = individualCreator;
    }

    @Override
    public Population replace(Population actualPopulation, List<Individual> offspring) {
        if (offspring.size() > actualPopulation.getPopulationSize()) {
            throw new IllegalArgumentException("This replacement strategy requires " +
                    "a list of offspring whose size is smaller or equal than the size of the population");
        }

        int k = offspring.size();
        List<Individual> newIndividuals = oldPopulationSelectionStrategy.select(actualPopulation, actualPopulation.getPopulationSize() - k).
                stream().map(individualCreator::create).collect(Collectors.toList());
        List<Individual> auxIndividuals = actualPopulation.getIndividuals();
        auxIndividuals.addAll(offspring);
        Population auxPopulation = new Population(auxIndividuals, null);

        newIndividuals.addAll(newPopulationSelectionStrategy.select(auxPopulation, k).stream()
                .map(individualCreator::create).collect(Collectors.toList()));

        return new Population(newIndividuals, actualPopulation);
    }
}
