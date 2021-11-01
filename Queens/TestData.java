package Queens;

import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class TestData {

    List<Chromosome> chromosomes;
    Chromosome bestPermutation;
    int numberOfGenerations;

    public TestData(List<Chromosome> chromosomes, int numberOfGenerations) {
        this.chromosomes = chromosomes;
        Collections.sort(this.chromosomes);
        this.bestPermutation = chromosomes.get(0);
        this.numberOfGenerations = numberOfGenerations;
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(String.valueOf(this.bestPermutation.getFitness())); // best Fitness
        joiner.add(String.valueOf(numberOfGenerations)); // number Of Generations
        long count = chromosomes.stream().filter(c -> c.getFitness() == 8).count();
        joiner.add(String.valueOf(count)); // Convergiu para
        return joiner.toString();
    }
}
