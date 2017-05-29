package ar.edu.itba.genetic_algorithms.algorithms.crossover_methods;

import ar.edu.itba.genetic_algorithms.algorithms.api.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.engine.ChromosomePair;

import java.util.Random;

public class TwoPointCrossover implements CrossoverStrategy {

    @Override
    public ChromosomePair crossover(ChromosomePair parents) {
        Random r = new Random();
        Object[] parentGenes1 = parents.getFirst().getGenes();
        Object[] parentGenes2 = parents.getSecond().getGenes();
        int chromosomeLength = parentGenes1.length;
        int locus1 = r.nextInt(chromosomeLength);
        int locus2 = r.nextInt(chromosomeLength);

        if(locus1 > locus2){
            int auxLocus = locus2;
            locus2 = locus1;
            locus1 = auxLocus;
        }

        Object[] offspringGenes1 = new Object[chromosomeLength];
        Object[] offspringGenes2 = new Object[chromosomeLength];

        for(int i = 0; i < chromosomeLength; i++){
            if(i < locus1 || i >= locus2){
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
