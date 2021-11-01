package Queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final Random rnd = new Random();
    private static final int populationSize = 100;
    private static int fitnessCounter = populationSize;
    private static final int fitnessCounterLimit = (int) 1e4;
    private static final double recombinationProbability = 0.6;
    private static final double mutationProbability = 0.4;

    public static void main(String[] args) {
        List<Chromosome> chromosomes = generatePopulation(populationSize);
        Collections.sort(chromosomes);
        int numberOfGenerations = 1;
        while (chromosomes.get(0).getFitness() < 8 && fitnessCounter < fitnessCounterLimit) {
            System.out.println("Best permutation:\n" + chromosomes.get(0));
            List<Chromosome> parents = getParents(chromosomes);
            List<Chromosome> children = generateChildren(parents);
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
        System.out.println("Best permutation:\n" + chromosomes.get(0));
        System.out.println("Number of generations: " + numberOfGenerations);
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

    private static List<Chromosome> generateChildren(List<Chromosome> parents) {
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
