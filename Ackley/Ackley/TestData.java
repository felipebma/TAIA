package Ackley;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class TestData {

    List<Chromosome> chromosomes;
    Chromosome bestPermutation;
    int numberOfGenerations;
    FitnessStrategy fitnessStrategy;
    List<List<Integer>> fitnesses;

    public TestData(List<Chromosome> chromosomes, List<List<Integer>> fitnesses, int numberOfGenerations,
            FitnessStrategy fitnessStrategy) {
        this.chromosomes = chromosomes;
        this.fitnesses = fitnesses;
        Collections.sort(this.chromosomes);
        this.bestPermutation = chromosomes.get(0);
        this.numberOfGenerations = numberOfGenerations;
        this.fitnessStrategy = fitnessStrategy;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(String.valueOf(this.bestPermutation.getFitness())); // best Fitness
        joiner.add(String.valueOf(numberOfGenerations)); // number Of Generations
        long count = chromosomes.stream().filter(c -> c.getFitness() == fitnessStrategy.maxFitness()).count();
        joiner.add(String.valueOf(count)); // Convergiu para
        return joiner.toString();
    }
}
