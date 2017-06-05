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
import ar.edu.itba.genetic_algorithms.main.io.MatlabScriptWriter;
import ar.edu.itba.genetic_algorithms.main.io.ResultsPrinter;
import ar.edu.itba.genetic_algorithms.models.alleles.CharacterAlleleContainers;
import ar.edu.itba.genetic_algorithms.models.alleles.ItemsRepository;
import ar.edu.itba.genetic_algorithms.models.character.Character;
import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Entry point.
 */
public class Main {

    @Parameter(names = {"-h", "--help"}, description = "Prints usage", help = true)
    private boolean usage = false;

    @Parameter(names = {"-c", "--config",}, description = "Path to configuration file.")
    private String configFilePath = "config.json";

    @Parameter(names = {"-m", "--matlab", "-o", "--octave"}, description = "States whether a Matlab/Octave " +
            "results file must be saved.")
    private boolean generateMatlab = false;

    @Parameter(names = {"-mF", "--matlab-file", "-oF", "--octave-file"},
            description = "Path to the Matlab/Octave results file.")
    private String matlabScriptFile = "";

    public static void main(String[] args) {

        // TODO: use logger?

        // Initialize main
        Main main = new Main();
        JCommander jCommander = new JCommander(main);
        jCommander.setProgramName("java -jar <path-to-jar>");
        jCommander.parse(args);

        if (main.usage) {
            jCommander.usage();
            return;
        }
        try {
            main.execute();
        } catch (IOException e) {
            System.err.println("Problems were encountered while executing system.");
            System.err.println("Aborting.");
            System.exit(1);
        }
    }

    private void execute() throws IOException {
        System.out.println("Starting...");
        System.out.print("Reading parameters...\t");
        Parameters parameters;
        try {
            parameters = Parameters.fromFile(configFilePath);
        } catch (IOException e) {
            System.err.println("Could not open configuration file.");
            throw e;
        }
        System.out.println("[DONE]");
        System.out.print("Initializing system...\t");
        Initializer initializer;
        try {
            initializer = new Initializer(parameters);
        } catch (IOException e) {
            System.err.println("Could not initialize system.");
            throw e;
        }
        System.out.println("[DONE]");
        System.out.print("Initializing engine...\t");
        GeneticAlgorithmEngine engine = initializer.getGeneticAlgorithmEngine();
        System.out.println("[DONE]");
        System.out.print("Running engine...\t\t");
        final long startingTime = System.currentTimeMillis();
        engine.evolve();
        final long finishTime = System.currentTimeMillis() - startingTime;
        System.out.println("[DONE]");
        System.out.println();
        ResultsPrinter.printResults(engine.getPopulation());
        System.out.println("\n\n\n");
        System.out.println("Population evolution took " + (finishTime / 1000) + " secs.");

        if (generateMatlab || (matlabScriptFile != null && !matlabScriptFile.isEmpty())) {
            if (matlabScriptFile == null || matlabScriptFile.isEmpty()) {
                System.err.println("Warning: Attempted to generate Matlab/Octave file " +
                        "without indicating output location");
                System.err.println("Warning: Output will be saved in current working directory with \"output.m\" name");
                matlabScriptFile = "./output.m";
            }

            try {
                MatlabScriptWriter.createMatlabScript(matlabScriptFile, engine);
            } catch (IOException e) {
                System.err.println("Could not save matlab file.");
            }
        }

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

            // Engine stuff
            this.selectionStrategy = parameters.getStrategies().getSelection().getStrategy();
            this.k = parameters.getStrategies().getSelection().getK();
            this.crossoverStrategy = parameters.getStrategies().getCrossover().getStrategy();
            this.mutationStrategy = parameters.getStrategies().getMutation().getStrategy();
            this.pm = parameters.getStrategies().getMutation().getPm();
            this.replacementStrategy = parameters.getStrategies().getReplacement().getStrategy();
            this.endingCondition = parameters.getStrategies().getEnding().getStrategy();


            // Items initialization
            ItemsInitializer armorsInitialization = new ItemsInitializer(parameters.getItems().getArmorsFilePath(),
                    ItemsFileReader.ItemType.ARMOR);
            ItemsInitializer bootsInitialization = new ItemsInitializer(parameters.getItems().getBootsFilePath(),
                    ItemsFileReader.ItemType.BOOT);
            ItemsInitializer gauntletsInitialization = new ItemsInitializer(parameters.getItems()
                    .getGauntletsFilePath(), ItemsFileReader.ItemType.GAUNTLET);
            ItemsInitializer helmetsInitialization = new ItemsInitializer(parameters.getItems().getHelmetsFilePath(),
                    ItemsFileReader.ItemType.HELMET);
            ItemsInitializer weaponsInitialization = new ItemsInitializer(parameters.getItems().getWeaponsFilePath(),
                    ItemsFileReader.ItemType.WEAPON);

            ExecutorService executorService = Executors.newFixedThreadPool(5);
            executorService.execute(armorsInitialization);
            executorService.execute(bootsInitialization);
            executorService.execute(gauntletsInitialization);
            executorService.execute(helmetsInitialization);
            executorService.execute(weaponsInitialization);
            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.DAYS);
            } catch (InterruptedException ignored) {
                // Do nothing...
            }

            // Check if all threads finished loading data correctly.
            if (!armorsInitialization.isFinishedWell()) {
                throw armorsInitialization.getE();
            }
            if (!bootsInitialization.isFinishedWell()) {
                throw bootsInitialization.getE();
            }
            if (!gauntletsInitialization.isFinishedWell()) {
                throw gauntletsInitialization.getE();
            }
            if (!helmetsInitialization.isFinishedWell()) {
                throw helmetsInitialization.getE();
            }
            if (!weaponsInitialization.isFinishedWell()) {
                throw weaponsInitialization.getE();
            }


            this.alleleContainers = new CharacterAlleleContainers(Character.MIN_HEIGHT, Character.MAX_HEIGHT,
                    armorsInitialization.getRepository(), bootsInitialization.getRepository(),
                    gauntletsInitialization.getRepository(), helmetsInitialization.getRepository(),
                    weaponsInitialization.getRepository());
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


        /**
         * {@link Runnable} task for creating an {@link ItemsRepository} from a file.
         */
        private static final class ItemsInitializer implements Runnable {

            /**
             * The path to the file.
             */
            private final String filePath;

            /**
             * The produced {@link ItemsRepository}.
             */
            private final ItemsRepository repository;

            /**
             * The type of item.
             */
            private final ItemsFileReader.ItemType itemType;

            /**
             * Indicates whether the task finished well (i.e without errors).
             */
            private boolean finishedWell;

            /**
             * {@link IOException} that might occur when reading the file.
             */
            private IOException e;


            /**
             * Constructor.
             *
             * @param filePath The path to the file.
             * @param itemType The type of item.
             */
            private ItemsInitializer(String filePath, ItemsFileReader.ItemType itemType) {

                this.filePath = filePath;
                this.repository = new ItemsRepository();
                this.itemType = itemType;
                this.finishedWell = false;
                this.e = null;
            }

            @Override
            public void run() {
                try {
                    new ItemsFileReader(filePath, repository, itemType)
                            .parse();
                } catch (IOException e) {
                    this.e = e;
                }
                finishedWell = true;
            }

            /**
             * @return The {@link IOException} that might occur when reading the file.
             */
            private IOException getE() {
                return e;
            }

            /**
             * @return Whether the task finished well (i.e without errors).
             */
            private boolean isFinishedWell() {
                return finishedWell;
            }

            /**
             * @return The produced {@link ItemsRepository}.
             */
            private ItemsRepository getRepository() {
                return repository;
            }
        }

    }
}
