package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


///TODO!!!! https://doc.lagout.org/science/0_Computer%20Science/2_Algorithms/Practical%20Handbook%20of%20GENETIC%20ALGORITHMS%2C%20Volume%20II/ganf5.pdf
public class Boltzmann extends AccumulatedSelectionMethod {

    private double t = 0.3;

    private double a = 0.4;

    public Boltzmann(double t) {
        this.t = t;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Double> numerators = new ArrayList<>();
        double denominator = 0;

        for (Individual individual : population.getIndividuals()) {
            numerators.add(Math.exp(individual.getFitness() / T(population.getGeneration())));
            denominator += Math.exp(individual.getFitness() / T(population.getGeneration()));
        }

        double accum = 0;
        int index = 0;
        HashMap<Individual, Double> accumulatedExpVal = new HashMap<>();

        for (Individual i : population.getIndividuals()) {
            accumulatedExpVal.put(i, accum + numerators.get(index) / denominator);
            accum += numerators.get(index) / denominator;
            index++;
        }
        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            double rand = Math.random();
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(rand, accumulatedExpVal));
        }

        return selectedChromosomes;
    }

    //TODO
    private double T(int generation) {
        return 1 / a * generation;
    }
}
