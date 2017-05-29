package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.Chromosome;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.ArrayList;
import java.util.List;


///TODO!!!! https://doc.lagout.org/science/0_Computer%20Science/2_Algorithms/Practical%20Handbook%20of%20GENETIC%20ALGORITHMS%2C%20Volume%20II/ganf5.pdf
public class Boltzmann implements SelectionMethod {

    private double t = 0.3;

    public Boltzmann(double t) {
        this.t = t;
    }

    @Override
    public List<Chromosome> select(Population population, int k) {

        List<Chromosome> selectedChromosomes = new ArrayList<>();


        return selectedChromosomes;
    }
}
