package ar.edu.itba.genetic_algorithms.algorithms.engine;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;

import java.util.*;

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

    public Population(List<Individual> individuals, Population previousPopulation){
        this.individuals = individuals;
        this.previousPopulation = previousPopulation;
        if(previousPopulation == null){
            this.generation = 1;
        } else {
            this.generation = previousPopulation.getGeneration() + 1;
        }
        this.populationSize = individuals.size();
    }


    public double sumFitness(){
        int accum = 0;
        for(Individual i : individuals){
            accum += i.getFitness();
        }
        return accum;
    }

    public double avgFitness(){
        return sumFitness()/populationSize;
    }

    public HashMap<Individual, Double> getRelativeFitnesses(){
        HashMap<Individual, Double> relativeFitnesses =  new HashMap<Individual, Double>();
        double totalFitness = sumFitness();

        for(Individual i : individuals){
            relativeFitnesses.put(i, (i.getFitness()/totalFitness));
        }
        return relativeFitnesses;
    }

    public HashMap<Individual, Double> getAccumulatedRelativeFitnesses(){
        HashMap<Individual, Double> relativeFitnesses = getRelativeFitnesses();
        HashMap<Individual, Double> accumulatedRelativeFitnesses = new HashMap<Individual, Double>();
        double accum = 0;
        for(Map.Entry<Individual, Double> e : relativeFitnesses.entrySet()){
            accumulatedRelativeFitnesses.put(e.getKey(), e.getValue() + accum);
            accum += e.getValue();
        }
        return accumulatedRelativeFitnesses;
    }

    public Individual bestIndividual(){
        //TODO: change if list structure changes.
        Individual best = null;
        for(Individual individual : individuals){
            if(best == null || best.getFitness() < individual.getFitness())
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

    public List<Individual> getSortedIndividualsFromWorstToBest(){
        //TODO: checkear si lo estoy ordenando bien
        List<Individual> sortedIndividuals = new ArrayList<>(individuals);
        sortedIndividuals.sort(
                (Individual i1, Individual i2) -> (new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()))));
        return sortedIndividuals;
    }

    public List<Individual> getSortedIndividualsFromBestToWorst(){
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
}
