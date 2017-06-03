package ar.edu.itba.genetic_algorithms.main;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.algorithms.crossover_strategies.CrossoverStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.end_conditions.EndingCondition;
import ar.edu.itba.genetic_algorithms.algorithms.engine.GeneticAlgorithmEngine;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;
import ar.edu.itba.genetic_algorithms.algorithms.mutation_strategies.MutationStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.replacement_strategies.ReplacementStrategy;
import ar.edu.itba.genetic_algorithms.algorithms.selection_strategies.SelectionStrategy;
import ar.edu.itba.genetic_algorithms.main.customization.Parameters;
import ar.edu.itba.genetic_algorithms.main.io.ItemsFileReader;
import ar.edu.itba.genetic_algorithms.models.alleles.CharacterAlleleContainers;
import ar.edu.itba.genetic_algorithms.models.alleles.ItemsRepository;
import ar.edu.itba.genetic_algorithms.models.character.Character;

import java.io.IOException;

/**
 * Entry point.
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Starting...");
        System.out.print("Reading parameters...\t");
        Parameters parameters = Parameters.fromFile(args[0]);
        System.out.println("[DONE]");
        System.out.print("Initializing system...\t");
        Initializer initializer = new Initializer(parameters);
        System.out.println("[DONE]");
        GeneticAlgorithmEngine engine = initializer.getGeneticAlgorithmEngine();
        System.out.println("Starting engine...\t");
        final long startingTime = System.currentTimeMillis();
        engine.evolve();
        System.out.println("Finished");
        System.out.println();
        System.out.println("Elapsed time: " + (System.currentTimeMillis() - startingTime) / 1000 + "secs.");
        System.out.println("Population average fitness is: " + engine.getActualAverageFitness() + ".");

    }

    /**
     * Class being in charge of initializing the system, based on {@link Parameters}.
     */
    private final static class Initializer {

        /**
         * The initialized {@link AlleleContainerWrapper}
         */
        private final AlleleContainerWrapper alleleContainers;
        /**
         * The initialized "k" parameter used for selection.
         */
        private final int k;
        /**
         * The initialized "pm" parameter used for mutation.
         */
        private final double pm;
        /**
         * The initialized selection strategy.
         */
        private final SelectionStrategy selectionStrategy;
        /**
         * The initialized crossover strategy.
         */
        private final CrossoverStrategy crossoverStrategy;
        /**
         * The initialized mutation strategy.
         */
        private final MutationStrategy mutationStrategy;
        /**
         * The initialized replacement strategy.
         */
        private final ReplacementStrategy replacementStrategy;
        /**
         * The initialized ending condition strategy.
         */
        private final EndingCondition endingCondition;
        /**
         * The initialized initial population.
         */
        private final Population initialPopulation;


        /**
         * Constructor.
         *
         * @param parameters The parameters from which system is initialized.
         * @throws IOException If some IO error occurs when reading items files.
         */
        private Initializer(Parameters parameters) throws IOException {

            // Characters multipliers
            Character.createMultipliersInstance(parameters.getCharacter().getStrengthMultiplier(),
                    parameters.getCharacter().getAgilityMultiplier(),
                    parameters.getCharacter().getProficiencyMultiplier(),
                    parameters.getCharacter().getResistanceMultiplier(), parameters.getCharacter().getLifeMultiplier());


            // Items initialization
            final ItemsRepository armors = new ItemsRepository();
            final ItemsRepository boots = new ItemsRepository();
            final ItemsRepository gauntlets = new ItemsRepository();
            final ItemsRepository helmets = new ItemsRepository();
            final ItemsRepository weapons = new ItemsRepository();
            new ItemsFileReader(parameters.getItems().getArmorsFilePath(), armors, ItemsFileReader.ItemType.ARMOR)
                    .parse();
            new ItemsFileReader(parameters.getItems().getArmorsFilePath(), boots, ItemsFileReader.ItemType.BOOT)
                    .parse();
            new ItemsFileReader(parameters.getItems().getArmorsFilePath(), gauntlets, ItemsFileReader.ItemType.GAUNTLET)
                    .parse();
            new ItemsFileReader(parameters.getItems().getArmorsFilePath(), helmets, ItemsFileReader.ItemType.HELMET)
                    .parse();
            new ItemsFileReader(parameters.getItems().getArmorsFilePath(), weapons, ItemsFileReader.ItemType.WEAPON)
                    .parse();
            this.alleleContainers = new CharacterAlleleContainers(Character.MIN_HEIGHT, Character.MAX_HEIGHT,
                    armors, boots, gauntlets, helmets, weapons);

            // Engine stuff
            this.selectionStrategy = parameters.getStrategies().getSelection().getStrategy();
            this.k = parameters.getStrategies().getSelection().getK();
            this.crossoverStrategy = parameters.getStrategies().getCrossover().getStrategy();
            this.mutationStrategy = parameters.getStrategies().getMutation().getStrategy();
            this.pm = parameters.getStrategies().getMutation().getPm();
            this.replacementStrategy = parameters.getStrategies().getReplacement().getStrategy();
            this.endingCondition = parameters.getStrategies().getEnding().getStrategy();

            // Initial population
            this.initialPopulation = parameters.getInitialPopulation()
                    .generateInitialPopulation(parameters.getCharacter().getCharacterBuilder(), this.alleleContainers);
        }

        /**
         * @return The {@link GeneticAlgorithmEngine} created from initialized parameters.
         */
        private GeneticAlgorithmEngine getGeneticAlgorithmEngine() {
            return new GeneticAlgorithmEngine(this.initialPopulation, this.endingCondition, this.selectionStrategy,
                    this.k, this.crossoverStrategy, this.mutationStrategy, this.pm, this.replacementStrategy,
                    this.alleleContainers);
        }

    }
}
