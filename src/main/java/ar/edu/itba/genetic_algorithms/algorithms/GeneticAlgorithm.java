package ar.edu.itba.genetic_algorithms.algorithms;

import ar.edu.itba.genetic_algorithms.algorithms.crossover_methods.CrossoverMethod;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_methods.ReplacementMethod;
import ar.edu.itba.genetic_algorithms.algorithms.selection_methods.SelectionMethod;
import ar.edu.itba.genetic_algorithms.models.character.Archer;

import java.util.LinkedList;
import java.util.List;

public class GeneticAlgorithm {

    private Population population;

    private final EndingCondition endingCondition;

    private final SelectionMethod selectionMethod;

    private final int k;

    private final CrossoverMethod crossoverMethod;

    private final ReplacementMethod replacementMethod;

    public GeneticAlgorithm(Population initialPopulation, EndingCondition endingCondition, SelectionMethod selectionMethod, int k, CrossoverMethod crossoverMethod, ReplacementMethod replacementMethod){
        this.population = initialPopulation;
        this.endingCondition = endingCondition;
        this.selectionMethod = selectionMethod;
        this.k = k;
        this.crossoverMethod = crossoverMethod;
        this.replacementMethod = replacementMethod;
    }

    public void evolve(){

        while(!endingCondition.isSatified(population)){

            System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());

            List<ChromosomePair> selectedChromosomes = selectionMethod.select(population, k);

            List<Chromosome> offspringChromosomes = new LinkedList<>();
            for(ChromosomePair pair : selectedChromosomes){
                ChromosomePair offsprings = crossoverMethod.crossover(pair);
                offspringChromosomes.add(offsprings.getFirst());
                offspringChromosomes.add(offsprings.getSecond());
            }

            //TODO: mutation

            List<Individual> offspringIndividuals = chromosomesToIndividuals(offspringChromosomes);
            List<Individual> newIndividuals = replacementMethod.replace(population, offspringIndividuals);

            population = new Population(newIndividuals, population.getGeneration() + 1);
        }

        System.out.println("Generation " + population.getGeneration() + "\n\tAverage fitness: " + population.avgFitness());

    }

    private List<Individual> chromosomesToIndividuals(List<Chromosome> chromosomes){
        List<Individual> individuals = new LinkedList<>();
        for(Chromosome chromosome : chromosomes){
            //TODO: create individuals correctly
            Individual individual = new Archer.Builder().create(chromosome);
            individuals.add(individual);
        }
        return individuals;
    }




}
