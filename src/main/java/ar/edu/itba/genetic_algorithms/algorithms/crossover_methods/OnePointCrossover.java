package ar.edu.itba.genetic_algorithms.algorithms.crossover_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.engine.ChromosomePair;

import java.util.Random;

public class OnePointCrossover implements CrossoverStrategy {

    @Override
    public ChromosomePair crossover(ChromosomePair parents) {
        Random r = new Random();
        Object[] parentGenes1 = parents.getFirst().getGenes();
        Object[] parentGenes2 = parents.getSecond().getGenes();
        int chromosomeLength = parentGenes1.length;
        int locus = r.nextInt(chromosomeLength);
        Object[] offspringGenes1 = new Object[chromosomeLength];
        Object[] offspringGenes2 = new Object[chromosomeLength];

        for(int i = 0; i < chromosomeLength; i++){
            if(i < locus){
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
