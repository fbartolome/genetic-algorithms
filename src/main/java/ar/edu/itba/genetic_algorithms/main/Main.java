package ar.edu.itba.genetic_algorithms.main;

import ar.edu.itba.genetic_algorithms.main.io.ItemsFileReader;
import ar.edu.itba.genetic_algorithms.models.alleles.CharacterAlleleContainers;
import ar.edu.itba.genetic_algorithms.models.alleles.ItemsRepository;
import ar.edu.itba.genetic_algorithms.models.character.Character;

import java.io.IOException;

/**
 * Hello world!
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Hello World!");


        ItemsRepository armors = new ItemsRepository();
        ItemsRepository boots = new ItemsRepository();
        ItemsRepository gauntlets = new ItemsRepository();
        ItemsRepository helmets = new ItemsRepository();
        ItemsRepository weapons = new ItemsRepository();

        ItemsFileReader armorsReader = new ItemsFileReader("./src/resources/testData/armors.tsv", armors, ItemsFileReader.ItemType.ARMOR);
        ItemsFileReader bootsReader = new ItemsFileReader("./src/resources/testData/boots.tsv", boots, ItemsFileReader.ItemType.BOOT);
        ItemsFileReader gauntletsReader = new ItemsFileReader("./src/resources/testData/gauntlets.tsv", gauntlets, ItemsFileReader.ItemType.GAUNTLET);
        ItemsFileReader helmetsReader = new ItemsFileReader("./src/resources/testData/helmets.tsv", helmets, ItemsFileReader.ItemType.HELMET);
        ItemsFileReader weaponsReader = new ItemsFileReader("./src/resources/testData/weapons.tsv", weapons, ItemsFileReader.ItemType.WEAPON);

        armorsReader.parse();
        bootsReader.parse();
        gauntletsReader.parse();
        helmetsReader.parse();
        weaponsReader.parse();

        CharacterAlleleContainers container = new CharacterAlleleContainers(Character.MIN_HEIGHT, Character.MAX_HEIGHT,
                armors, boots, gauntlets, helmets, weapons);

        Character.createMultipliersInstance(0.8, 1.1, 1.1, 0.9, 0.7);


    }
}
