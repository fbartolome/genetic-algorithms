package ar.edu.itba.genetic_algorithms.main.io;

import ar.edu.itba.genetic_algorithms.algorithms.api.Individual;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

/**
 * This class is in charge of printing results in {@code stdout}.
 */
public class ResultsPrinter {

    /**
     * Prints results in {@code stdout}.
     *
     * @param population The final {@link Population} after evolution occurred.
     */
    public static void printResults(Population population) {
        if (population == null) {
            return;
        }
        printResults(population.getPreviousPopulation());
        final Individual bestIndividual = population.bestIndividual();
        System.out.println("Generation number: " + population.getGeneration());
        System.out.println("\tSize: " + population.getPopulationSize());
        System.out.println("\tAverage fitness: " + population.avgFitness());
        System.out.println("\tBest Individual's fitness: " + population.bestFitness());
        System.out.println("\tMedian Individual's fitness: " + population.medianFitness());
        System.out.println("\tWorst Individual's fitness: " + population.worstFitness());
        System.out.println("\tBest Individual's chromosome: ");
        System.out.println("\t\t" + bestIndividual.getChromosomeStringRepresentation()
                .replace(" - Armor", "\n\t\tArmor")
                .replace(" - Boot", "\n\t\tBoot")
                .replace(" - Gauntlet", "\n\t\tGauntlet")
                .replace(" - Helmet", "\n\t\tHelmet")
                .replace(" - Weapon", "\n\t\tWeapon"));
        System.out.println("--------------------------------------------------------------------------------------" +
                "-------------------------------------------------------------------------------------------------");
        System.out.println();
    }
}
