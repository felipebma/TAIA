package Queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;

public class Main {

    private static final Random rnd = new Random();

    public static void main(String[] args) {
        runDefault();
    }

    private static void runDefault() {
        System.out.println(run(100, (int) 1e4, 0.6, 0.4));
    }

    private static String run(int populationSize, int fitnessCounterLimit, double recombinationProbability,
            double mutationProbability) {
        List<Chromosome> chromosomes = generatePopulation(populationSize);
        int fitnessCounter = populationSize;
        Collections.sort(chromosomes);
        int numberOfGenerations = 1;
        while (chromosomes.get(0).getFitness() < 8 && fitnessCounter < fitnessCounterLimit) {
            // System.out.println("Best permutation:\n" + chromosomes.get(0));
            List<Chromosome> parents = getParents(chromosomes);
            List<Chromosome> children = generateChildren(parents, recombinationProbability);
            fitnessCounter += 2;
            for (Chromosome c : chromosomes) {
                if (rnd.nextDouble() < mutationProbability) {
                    c.mutation();
                    fitnessCounter++;
                }
            }
            Collections.sort(chromosomes);
            chromosomes.remove(chromosomes.size() - 1);
            chromosomes.remove(chromosomes.size() - 1);
            chromosomes.addAll(children);
            Collections.sort(chromosomes);
            numberOfGenerations++;
        }
        return formatData(chromosomes, numberOfGenerations);
    }

    private static String formatData(List<Chromosome> chromosomes, int numberOfGenerations) {
        StringJoiner joiner = new StringJoiner("\n");
        joiner.add("Best permutation:\n" + chromosomes.get(0));
        joiner.add("Number of Generations: " + numberOfGenerations);
        long count = chromosomes.stream().filter(c -> c.getFitness() == 8).count();
        joiner.add("Convergiu para: " + count);
        return joiner.toString();
    }

    private static List<Chromosome> generatePopulation(int populationSize) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new ChromosomeArr());
        }
        return population;
    }

    private static List<Chromosome> getParents(List<Chromosome> population) {
        Collections.shuffle(population, rnd);
        List<Chromosome> randomFive = population.subList(0, 5);
        Collections.sort(randomFive);
        return randomFive.subList(0, 2);
    }

    private static List<Chromosome> generateChildren(List<Chromosome> parents, double recombinationProbability) {
        int splitPos = rnd.nextInt(6) + 1;
        List<Chromosome> children = new ArrayList<>();
        if (rnd.nextDouble() < recombinationProbability) {
            children.add(parents.get(0).cutAndCrossfill(parents.get(1), splitPos));
            children.add(parents.get(1).cutAndCrossfill(parents.get(0), splitPos));
        } else {
            children.add(new ChromosomeArr(parents.get(0).getBitRepresentation()));
            children.add(new ChromosomeArr(parents.get(1).getBitRepresentation()));
        }
        return children;
    }
}
