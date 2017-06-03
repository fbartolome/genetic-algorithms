package ar.edu.itba.genetic_algorithms.algorithms.selection_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;


import java.util.*;


public class Boltzmann extends AccumulatedSelectionMethod {

    private double n = 0.9;

    public Boltzmann(double n) {
        this.n = n;
    }

    public Boltzmann() {
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Double> numerators = new ArrayList<>();
        double denominator = 0;

        for (Individual individual : population.getIndividuals()) {
            numerators.add(Math.exp(individual.getFitness() / T(population.getGeneration())));
            denominator += Math.exp(individual.getFitness() / T(population.getGeneration()));
        }
        denominator /= population.getPopulationSize();
        double accum = 0;
        int index = 0;
        Multimap<Individual, Double> accumulatedExpVal = ArrayListMultimap.create();

        for (Individual i : population.getIndividuals()) {
            accumulatedExpVal.put(i, accum + numerators.get(index) / denominator);
            accum += numerators.get(index) / denominator;
            index++;
        }
        List<Chromosome> selectedChromosomes = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            double rand = Math.random() * accum;
            selectedChromosomes.add(selectChromosomeOnAccumulatedFitnessProbability(rand, accumulatedExpVal));
        }

        return selectedChromosomes;
    }

    private double T(int generation) {
        return (1 / (n * generation)) + 0.1;
    }
}

///https://doc.lagout.org/science/0_Computer%20Science/2_Algorithms/Practical%20Handbook%20of%20GENETIC%20ALGORITHMS%2C%20Volume%20II/ganf5.pdf