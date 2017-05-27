package ar.edu.itba.genetic_algorithms.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Population {

    /**
     * Individuals of the population.
     */
    private List<Individual> individuals;

    /**
     * Number of population's generation.
     */
    private final int generation;

    /**
     * Size of the population.
     */
    private final int populationSize;

    public Population(List<Individual> individuals, int generation){
        this.individuals = individuals;
        this.generation = generation;
        this.populationSize = individuals.size();
    }


    public double getSumFitness(){
        int accum = 0;
        for(Individual i : individuals){
            accum += i.getFitness();
        }
        return accum;
    }

    public HashMap<Individual, Double> getRelativeFitnesses(){
        HashMap<Individual, Double> relativeFitnesses =  new HashMap<Individual, Double>();
        double totalFitness = getSumFitness();

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

    public List<Individual> getIndividuals() {
        return individuals;
    }

    public int getGeneration() {
        return generation;
    }

    public int getPopulationSize() {
        return populationSize;
    }
}
