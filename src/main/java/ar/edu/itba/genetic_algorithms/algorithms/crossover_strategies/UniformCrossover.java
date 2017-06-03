package ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.engine.ChromosomePair;

import java.util.Random;

public class UniformCrossover implements CrossoverStrategy {

    private final double p;

    public UniformCrossover(double p) {
        if(p > 1 || p < 0){
            throw new IllegalArgumentException("Value of p must be between 0 and 1.");
        }
        this.p = p;
    }

    @Override
    public ChromosomePair crossover(ChromosomePair parents) {
        Object[] parentGenes1 = parents.getFirst().getGenes();
        Object[] parentGenes2 = parents.getSecond().getGenes();
        int chromosomeLength = parentGenes1.length;
        Object[] offspringGenes1 = new Object[chromosomeLength];
        Object[] offspringGenes2 = new Object[chromosomeLength];

        for(int i = 0; i < chromosomeLength; i++){
            if(Math.random() > p){
                offspringGenes1[i] = parentGenes1[i];
                offspringGenes2[i] = parentGenes2[i];
            } else {
                offspringGenes1[i] = parentGenes2[i];
                offspringGenes2[i] = parentGenes1[i];
            }
        }

        Chromosome chromosome1 = new Chromosome(offspringGenes1);
        Chromosome chromosome2 = new Chromosome(offspringGenes2);

        return new ChromosomePair(chromosome1,chromosome2);
    }
}
