package ar.edu.itba.genetic_algorithms.algorithms;

public class ChromosomePair {

    private final Chromosome first;

    private final Chromosome second;

    public ChromosomePair(Chromosome first, Chromosome second){
        this.first = first;
        this.second = second;
    }

    public Chromosome getFirst() {
        return first;
    }

    public Chromosome getSecond() {
        return second;
    }
}
