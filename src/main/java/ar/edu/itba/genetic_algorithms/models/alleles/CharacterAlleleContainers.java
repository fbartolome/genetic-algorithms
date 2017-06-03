package ar.edu.itba.genetic_algorithms.models.alleles;

import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainer;
import ar.edu.itba.genetic_algorithms.algorithms.api.AlleleContainerWrapper;

import java.util.Random;

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
     * Private constructor for SINGLETON use.
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
    public Object getRandomAllele(int positionInChromosome) {
        return containers[positionInChromosome].getAllele(random);
    }


    /**
     * Pseudo Height allele container.
     */
    private static class HeightContainer implements AlleleContainer {

        /**
         * The min. height this container will return.
         */
        private final double minHeight;

        /**
         * The max. height this container will return.
         */
        private final double maxHeight;

        /**
         * Constructor.
         *
         * @param minHeight The min. height the container will return.
         * @param maxHeight The max. height the container will return.
         */
        private HeightContainer(double minHeight, double maxHeight) {
            this.minHeight = minHeight;
            this.maxHeight = maxHeight;
        }

        @Override
        public Object getAllele(Random random) {
            return minHeight + (maxHeight - minHeight) * random.nextDouble();
        }

        @Override
        public Object getAllele() {
            return getAllele(new Random());
        }
    }
}
