package ar.edu.itba.genetic_algorithms.models.alleles;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainer;
import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;
import ar.edu.itba.genetic_algorithms.models.character.Character;
import ar.edu.itba.genetic_algorithms.models.item.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * This class holds all {@link AlleleContainer} a {@link Character} may have.
 */
public class CharacterAlleleContainers implements AlleleContainerWrapper {


    /**
     * {@link Random} instance for getting random alleles.
     */
    private final Random random;


    /**
     * Holds all {@link AlleleContainer} to be used by this wrapper.
     */
    private final AlleleContainer[] containers;


    /**
     * Constructor.
     *
     * @param minHeight           The min. height a {@link Character} might have.
     * @param maxHeight           The max. height a {@link Character} might have.
     * @param armorsRepository    The {@link ItemsRepository} holding {@link Armor}s.
     * @param bootsRepository     The {@link ItemsRepository} holding {@link Boot}s.
     * @param gauntletsRepository The {@link ItemsRepository} holding {@link Gauntlet}s.
     * @param helmetsRepository   The {@link ItemsRepository} holding {@link Helmet}s.
     * @param weaponsRepository   The {@link ItemsRepository} holding {@link Weapon}s.
     */
    public CharacterAlleleContainers(double minHeight, double maxHeight,
                                     ItemsRepository armorsRepository, ItemsRepository bootsRepository,
                                     ItemsRepository gauntletsRepository, ItemsRepository helmetsRepository,
                                     ItemsRepository weaponsRepository) {
        random = new Random();
        containers = new AlleleContainer[6];
        containers[0] = new HeightContainer(minHeight, maxHeight);
        containers[1] = armorsRepository;
        containers[2] = bootsRepository;
        containers[3] = gauntletsRepository;
        containers[4] = helmetsRepository;
        containers[5] = weaponsRepository;

    }

    @Override
    public Object getSpecificAllele(int alleleNumber, int positionInChromosome) {
        return containers[positionInChromosome].getSpecificAllele(alleleNumber);
    }

    @Override
    public Object getRandomAllele(int positionInChromosome) {
        return containers[positionInChromosome].getAllele(random);
    }

    @Override
    public int getAmountOfGenes() {
        return containers.length;
    }

    @Override
    public int getAmountOfAlleles(int positionInChromosome) {
        return containers[positionInChromosome].getAmountOfAlleles();
    }

    @Override
    public List<Integer> getAllelesNumbers(int positionInChromosome) {
        return containers[positionInChromosome].getAllelesNumbers();
    }

    /**
     * Pseudo Height allele container.
     */
    private static class HeightContainer implements AlleleContainer {

        private static final int AMOUNT_OF_FIXED_HEIGHTS = 500000; // TODO: parameterize this?


        /**
         * The min. height this container will return.
         */
        private final double minHeight;

        /**
         * The max. height this container will return.
         */
        private final double maxHeight;

        private final Map<Integer, Double> finiteSubsetOfHeights;

        /**
         * Constructor.
         *
         * @param minHeight The min. height the container will return.
         * @param maxHeight The max. height the container will return.
         */
        private HeightContainer(double minHeight, double maxHeight) {
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
            this.finiteSubsetOfHeights = new HashMap<>();
            final double step = (maxHeight - minHeight) / AMOUNT_OF_FIXED_HEIGHTS;
            IntStream.range(0, AMOUNT_OF_FIXED_HEIGHTS)
                    .forEach(number -> finiteSubsetOfHeights.put(number, minHeight + (number * step)));
        }

        @Override
        public Object getSpecificAllele(int alleleNumber) {
            return finiteSubsetOfHeights.get(alleleNumber);
        }

        @Override
        public Object getAllele(Random random) {
            return minHeight + (maxHeight - minHeight) * random.nextDouble();
        }

        @Override
        public Object getAllele() {
            return getAllele(new Random());
        }

        @Override
        public int getAmountOfAlleles() {
            return finiteSubsetOfHeights.size();
        }

        @Override
        public List<Integer> getAllelesNumbers() {
            return finiteSubsetOfHeights.keySet().stream().collect(Collectors.toList());
        }
    }
}
