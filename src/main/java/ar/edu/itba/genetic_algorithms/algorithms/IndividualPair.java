package ar.edu.itba.genetic_algorithms.algorithms;

public class IndividualPair {

    private final Individual first;

    private final Individual second;

    public IndividualPair(Individual first, Individual second){
        this.first = first;
        this.second = second;
    }

    public Individual getFirst() {
        return first;
    }

    public Individual getSecond() {
        return second;
    }
}
