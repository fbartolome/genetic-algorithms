package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.CrossoverStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy;
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

    private final ReplacementStrategy replacementStrategy;

    private final AlleleContainerWrapper alleleContainerWrapper;

    public GeneticAlgorithmEngine(Population initialPopulation, EndingCondition endingCondition,
                                  SelectionStrategy selectionStrategy, int k,
                                  CrossoverStrategy crossoverStrategy, MutationStrategy mutationStrategy,
                                  double pm, ReplacementStrategy replacementStrategy, AlleleContainerWrapper alleleContainerWrapper) {
        this.population = initialPopulation;
        this.endingCondition = endingCondition;
        this.selectionStrategy = selectionStrategy;
        this.k = k;
        this.crossoverStrategy = crossoverStrategy;
        this.mutationStrategy = mutationStrategy;
        this.pm = pm;
        this.replacementStrategy = replacementStrategy;
        this.alleleContainerWrapper = alleleContainerWrapper;
    }

    public Population evolve() {


        while (!endingCondition.isSatisfied(population)) {

            System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());

            List<Chromosome> selectedChromosomes = selectionStrategy.select(population, k);

            List<Chromosome> offspringChromosomes = new LinkedList<>();
            for (int i = 0; i < selectedChromosomes.size(); i += 2) {
                ChromosomePair offspring = crossoverStrategy.crossover(new ChromosomePair(selectedChromosomes.get(i), selectedChromosomes.get(i + 1)));
                offspringChromosomes.add(offspring.getFirst());
                offspringChromosomes.add(offspring.getSecond());
            }

            for (Chromosome chromosome : offspringChromosomes) {
                if (Math.random() < pm) {
                    mutationStrategy.mutate(chromosome, alleleContainerWrapper);
                }
            }

            List<Individual> offspringIndividuals = chromosomesToIndividuals(offspringChromosomes);
//            List<Individual> newIndividuals = replacementStrategy.replace(population, offspringIndividuals);
//
//            population = new Population(newIndividuals, population);
            population = replacementStrategy.replace(population, offspringIndividuals);
        }

        System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());
        return population;
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
