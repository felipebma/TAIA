package Queens;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static final Random rnd = new Random();

    public static void main(String[] args) {
        runDefault();
        runAlternativeFitness();
    }

    private static void runDefault() {
        runTest(new TestConfig("default", 100, (int) 1e4, 0.6, 0.4, FitnessStrategy.normalStrategy));
    }

    private static void runAlternativeFitness() {
        runTest(new TestConfig("alternativeFitness", 100, (int) 1e4, 0.6, 0.4, FitnessStrategy.alternativeStrategy));
    }

    private static void runTest(TestConfig config) {
        try {
            File file = new File(String.format("../data/%s.out", config.fileName));
            file.createNewFile();
            FileWriter fr = new FileWriter(file, false);
            fr.write("Best_Fitness,Generations_Count,Converged_Count");
            for (int i = 0; i < 30; i++)
                fr.write("\n" + run(config.populationSize, config.fitnessCounterLimit, config.recombinationProbability,
                        config.mutationProbability, config.fitnessStrategy));
            fr.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static String run(int populationSize, int fitnessCounterLimit, double recombinationProbability,
            double mutationProbability, FitnessStrategy fitnessStrategy) {
        List<Chromosome> chromosomes = generatePopulation(populationSize, fitnessStrategy);
        int fitnessCounter = populationSize;
        Collections.sort(chromosomes);
        int numberOfGenerations = 1;
        while (chromosomes.get(0).getFitness() < fitnessStrategy.maxFitness() && fitnessCounter < fitnessCounterLimit) {
            List<Chromosome> parents = getParents(chromosomes);
            List<Chromosome> children = generateChildren(parents, recombinationProbability, fitnessStrategy);
            fitnessCounter += 2;
            for (Chromosome c : chromosomes) {
                if (rnd.nextDouble() < mutationProbability) {
                    c.mutation();
                    fitnessCounter++;
                }
            }
            insertChildren(chromosomes, children);
            Collections.sort(chromosomes);
            numberOfGenerations++;
        }
        System.out.println(chromosomes.get(0));
        return new TestData(chromosomes, numberOfGenerations, fitnessStrategy).toString();
    }

    private static void insertChildren(List<Chromosome> chromosomes, List<Chromosome> children) {
        Collections.sort(chromosomes);
        chromosomes.remove(chromosomes.size() - 1);
        chromosomes.remove(chromosomes.size() - 1);
        chromosomes.addAll(children);
    }

    private static List<Chromosome> generatePopulation(int populationSize, FitnessStrategy fitnessStrategy) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new ChromosomeArr(fitnessStrategy));
        }
        return population;
    }

    private static List<Chromosome> getParents(List<Chromosome> population) {
        Collections.shuffle(population, rnd);
        List<Chromosome> randomFive = population.subList(0, 5);
        Collections.sort(randomFive);
        return randomFive.subList(0, 2);
    }

    private static List<Chromosome> generateChildren(List<Chromosome> parents, double recombinationProbability,
            FitnessStrategy fitnessStrategy) {
        int splitPos = rnd.nextInt(6) + 1;
        List<Chromosome> children = new ArrayList<>();
        if (rnd.nextDouble() < recombinationProbability) {
            children.add(parents.get(0).cutAndCrossfill(parents.get(1), splitPos));
            children.add(parents.get(1).cutAndCrossfill(parents.get(0), splitPos));
        } else {
            children.add(new ChromosomeArr(parents.get(0).getBitRepresentation(), fitnessStrategy));
            children.add(new ChromosomeArr(parents.get(1).getBitRepresentation(), fitnessStrategy));
        }
        return children;
    }
}
