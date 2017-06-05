package ar.edu.itba.genetic_algorithms.main.io;

import ar.edu.itba.genetic_algorithms.algorithms.engine.GeneticAlgorithmEngine;
import ar.edu.itba.genetic_algorithms.algorithms.engine.Population;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Stack;
import java.util.function.Function;

/**
 * This class is in charge of creating a Matlab/Octave script in which a Matlab/Octave array is initialized
 * with a {@link GeneticAlgorithmEngine} output after evolving an initial {@link Population}.
 * This script may be used for creating graphs in Matlab/Octave.
 */
public class MatlabScriptWriter {

    /**
     * Creates a Matlab/Octave script in which an array is defined and initialized with the given {@code engine}
     * output after evolving an initial {@link Population}.
     *
     * @param path   The path where the script must be saved.
     * @param engine The {@link GeneticAlgorithmEngine} that evolved the initial {@link Population}.
     * @throws IOException If any I/O error occurs while saving the script.
     * @implNote This method might create directories if the given {@code path} includes non-existing directories.
     */
    public static void createMatlabScript(String path, GeneticAlgorithmEngine engine) throws IOException {

        //=========================
        // File handling
        //=========================
        File file = new File(path);
        File parent = file.getParentFile();
        if (parent != null) {
            //noinspection ResultOfMethodCallIgnored
            parent.mkdirs();
        }
        //noinspection ResultOfMethodCallIgnored
        file.createNewFile();
        PrintWriter writer = new PrintWriter(file, "UTF-8");
        writer.flush();

        //=========================
        // Store variables
        //=========================
        StringBuilder bld = new StringBuilder();
        bld.append("avg = [");
        createArrayRecursive(bld, engine.getPopulation(), Population::avgFitness);
        bld.append("];");
        writer.println(bld.toString()); // Write "avg" matlab/octave variable into file
        writer.flush(); // Flush into file

        bld.setLength(0); // Re use same builder.
        bld.append("best = [");
        createArrayRecursive(bld, engine.getPopulation(), Population::bestFitness);
        bld.append("];");
        writer.println(bld.toString()); // Write "best" matlab/octave variable into file
        writer.flush(); // Flush into file

        bld.setLength(0); // Re use same builder.
        bld.append("median_ = [");
        createArrayRecursive(bld, engine.getPopulation(), Population::medianFitness);
        bld.append("];");
        writer.println(bld.toString()); // Write "median_" matlab/octave variable into file
        writer.flush(); // Flush into file

        bld.setLength(0); // Re use same builder.
        bld.append("worst = [");
        createArrayRecursive(bld, engine.getPopulation(), Population::worstFitness);
        bld.append("];");
        writer.println(bld.toString()); // Write "worst" matlab/octave variable into file
        writer.flush(); // Flush into file

        bld.setLength(0); // Re use same builder.
        bld.append("finalBestFitness = ");
        bld.append(engine.getPopulation().bestFitness());
        writer.println(bld.toString()); // Write "finalBestFitness" matlab/octave variable into file
        writer.flush(); // Flush into file

        bld.setLength(0); // Re use same builder.
        bld.append("finalAvgFitness = ");
        bld.append(engine.getPopulation().avgFitness());
        writer.println(bld.toString()); // Write "finalAvgFitness" matlab/octave variable into file
        writer.flush(); // Flush into file

        writer.println("plot(avg,'k');");
        writer.println("hold on");
        writer.println("plot(best,'r');");
        writer.println("hold on");
        writer.println("plot(median_,'g');");
        writer.println("hold on");
        writer.println("plot(worst,'b');");
        writer.println("xlabel('Generation');");
        writer.println("ylabel('Fitness');");
        writer.println("legend('average fitness','best fitness','median fitness','worst fitness');");
        writer.flush();

        //=========================
        // Close writer
        //=========================
        writer.close();
    }

    /**
     * Creates the array content in a recursive way, storing values got from the given {@code valueSupplier}
     * into the given {@code builder}.
     *
     * @param builder       The {@link StringBuilder} storing data.
     * @param population    The {@link Population} from which data is got.
     * @param valueSupplier The {@link Function} transforming {@link Population} data into a {@link Double} value.
     */
    private static void createArrayRecursive(StringBuilder builder, Population population,
                                             Function<Population, Double> valueSupplier) {
        final Stack<Population> populations = new Stack<>();
        while (population != null) {
            populations.push(population);
            population = population.getPreviousPopulation();
        }
        while (!populations.isEmpty()) {
            population = populations.pop();
            builder.append(valueSupplier.apply(population)).append(", ");
        }
    }
}
