package ar.edu.itba.genetic_algorithms.algorithms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Population {

    private List<Individual> individualList;


    public double getSumFitness(){
        int accum = 0;
        for(Individual i : individualList){
            accum += i.getFitness();
        }
        return accum;
    }

    public HashMap<Individual, Double> getRelativeFitnesses(){
        HashMap<Individual, Double> relativeFitnesses =  new HashMap<Individual, Double>();
        double totalFitness = getSumFitness();

        for(Individual i : individualList){
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

    public List<Individual> getIndividualList() {
        return individualList;
    }




}
