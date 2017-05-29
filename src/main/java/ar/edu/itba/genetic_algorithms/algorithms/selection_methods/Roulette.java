package ar.edu.itba.genetic_algorithms.algorithms.selection_methods;

import ar.edu.itba.genetic_algorithms.algorithms.ChromosomePair;
import ar.edu.itba.genetic_algorithms.algorithms.Population;

import java.util.List;

public class Roulette implements SelectionMethod {

    @Override
    public List<ChromosomePair> select(Population population, int k) {
        return null;
    }

    //TODO: adapt
//    ROULETTE{
//
//        //TODO elegir un k y parametrizar
//        int k = 5;
//
//        public List<Individual> select(Population population){
//
//            List<Double> rand = new ArrayList<Double>();
//            for(int i = 0; i<k; i++){
//                rand.add(i, Math.random());
//            }
//
//            HashMap<Individual, Double> populationRelativeFitnesses = population.getAccumulatedRelativeFitnesses();
//            List<Individual> selectedIndividuals = new ArrayList<Individual>();
//
//            for(Double d : rand){
//                selectedIndividuals.add(selectIndividual(d, populationRelativeFitnesses));
//            }
//
//            return selectedIndividuals;
//
//        }
//
//    private Individual selectIndividual(Double rand, HashMap<Individual, Double> individualList){
//
//        for(Map.Entry<Individual, Double> e : individualList.entrySet()){
//            if(e.getValue() > rand){
//                return e.getKey();
//            }
//        }
//        return null;
//
//    }
//
//},

}
