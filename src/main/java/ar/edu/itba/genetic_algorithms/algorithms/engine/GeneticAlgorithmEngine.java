package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.CrossoverStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementMethod;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import ar.edu.itba.genetic_algorithms.models.character.Archer;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithmEngine {

    private Population population;

    private final EndingCondition endingCondition;

    private final SelectionStrategy selectionStrategy;

    private final int k;

    private final CrossoverStrategy crossoverStrategy;

    private final MutationStrategy mutationStrategy;

    private final double pm;

    private final ReplacementMethod replacementMethod;

    public GeneticAlgorithmEngine(Population initialPopulation, EndingCondition endingCondition,
                                  SelectionStrategy selectionStrategy, int k,
                                  CrossoverStrategy crossoverStrategy, MutationStrategy mutationStrategy,
                                  double pm, ReplacementMethod replacementMethod) {
        this.population = initialPopulation;
        this.endingCondition = endingCondition;
        this.selectionStrategy = selectionStrategy;
        this.k = k;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.pm = pm;
        this.replacementMethod = replacementMethod;
    }

    public void evolve() {


        while (!endingCondition.isSatisfied(population)) {

            System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());

            List<Chromosome> selectedChromosomes = selectionStrategy.select(population, k);
            List<ChromosomePair> selectedChromosomePairs = new LinkedList<>();
            for (int i = 0; i < selectedChromosomes.size(); i += 2) {
                selectedChromosomePairs.add(new ChromosomePair(selectedChromosomes.get(i), selectedChromosomes.get(i + 1)));
            }

            List<Chromosome> offspringChromosomes = new LinkedList<>();
            for (ChromosomePair pair : selectedChromosomePairs) {
                ChromosomePair offspring = crossoverStrategy.crossover(pair);
                offspringChromosomes.add(offspring.getFirst());
                offspringChromosomes.add(offspring.getSecond());
            }

            //TODO: mutation

            List<Individual> offspringIndividuals = chromosomesToIndividuals(offspringChromosomes);
            List<Individual> newIndividuals = replacementMethod.replace(population, offspringIndividuals);

            population = new Population(newIndividuals, population);
        }

        System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());

    }

    private List<Individual> chromosomesToIndividuals(List<Chromosome> chromosomes) {
        List<Individual> individuals = new LinkedList<>();
        for (Chromosome chromosome : chromosomes) {
            //TODO: create individuals correctly
            Individual individual = new Archer.Builder().create(chromosome);
            individuals.add(individual);
        }
        return individuals;
    }


}
