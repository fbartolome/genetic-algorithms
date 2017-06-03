package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.api.IndividualCreator;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Population {

    /**
     * Individuals of the population.
     */
    private List<Individual> individuals;

    /**
     * Previous generation.
     */
    private Population previousPopulation;

    /**
     * Number of population's generation.
     */
    private final int generation;

    /**
     * Size of the population.
     */
    private final int populationSize;

    /**
     * The {@link IndividualCreator} used for creating the {@link Individual}s of this population.
     */
    private final IndividualCreator creator;

    public Population(List<Individual> individuals, Population previousPopulation, IndividualCreator creator) {
        this.individuals = individuals;
        this.previousPopulation = previousPopulation;
        if (previousPopulation == null) {
            this.generation = 1;
        } else {
            this.generation = previousPopulation.getGeneration() + 1;
        }
        this.populationSize = individuals.size();
        this.creator = creator;
    }

    public Population(List<Individual> individuals, Population previousPopulation) {
        this(individuals, previousPopulation, previousPopulation.getCreator());
    }


    public double sumFitness() {
        double accum = 0;
        for (Individual i : individuals) {
            accum += i.getFitness();
        }
        return accum;
    }

    public double avgFitness() {
        return sumFitness() / populationSize;
    }

    public Multimap<Individual, Double> getRelativeFitnesses() {
        Multimap<Individual, Double> relativeFitnesses = ArrayListMultimap.create();
        double totalFitness = sumFitness();
        for (Individual i : individuals) {
            relativeFitnesses.put(i, (i.getFitness() / totalFitness));
        }
        return relativeFitnesses;
    }

    public Multimap<Individual, Double> getAccumulatedRelativeFitnesses() {
        Multimap<Individual, Double> relativeFitnesses = getRelativeFitnesses();
        Multimap<Individual, Double> accumulatedRelativeFitnesses = ArrayListMultimap.create();
        double accum = 0;
        for (Map.Entry<Individual, Double> e : relativeFitnesses.entries()) {
            accum += e.getValue();
            accumulatedRelativeFitnesses.put(e.getKey(), accum);
        }
        return accumulatedRelativeFitnesses;
    }

    public Individual bestIndividual() {
        //TODO: change if list structure changes.
        Individual best = null;
        for (Individual individual : individuals) {
            if (best == null || best.getFitness() < individual.getFitness())
                best = individual;
        }
        return best;
    }

    public Population getPreviousPopulation() {
        return previousPopulation;
    }

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public List<Individual> getSortedIndividualsFromWorstToBest() {
        //TODO: checkear si lo estoy ordenando bien
        List<Individual> sortedIndividuals = new ArrayList<>(individuals);
        sortedIndividuals.sort(
                (Individual i1, Individual i2) -> (new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()))));
        return sortedIndividuals;
    }

    public List<Individual> getSortedIndividualsFromBestToWorst() {
        List<Individual> sortedIndividuals = new ArrayList<>(individuals);
        sortedIndividuals.sort(
                (Individual i1, Individual i2) -> -(new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()))));
        return sortedIndividuals;
    }

    public int getGeneration() {
        return generation;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public IndividualCreator getCreator() {
        return creator;
    }
}
