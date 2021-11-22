package Ackley;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.StringJoiner;
import java.util.stream.Collectors;

public class Main {

    private static final Random rnd = new Random();

    public static void main(String[] args) {
        runDefault();
    }

    private static void runDefault() {
        runTest(new TestConfig("default", 20000, (int) 1e6, 0.9, 0.3, FitnessStrategy.normalStrategy,
                StopStrategy.runGivenNumberOfGenerations(100), SelectionStrategy.weightedSampling));
    }

    private static void runTest(TestConfig config) {
        try {
            File output = new File(String.format("../data/%s_result.csv", config.fileName));
            File fitnesses = new File(String.format("../data/%s_fit.csv", config.fileName));
            fitnesses.createNewFile();
            output.createNewFile();
            FileWriter ow = new FileWriter(output, false);
            FileWriter fw = new FileWriter(fitnesses, false);
            StringJoiner fitness = new StringJoiner("\n\n");
            ow.write("Best_Fitness,Generations_Count,Converged_Count");
            for (int i = 0; i < 10; i++) {
                TestData testResults = run(config.populationSize, config.fitnessCounterLimit,
                        config.recombinationProbability, config.mutationProbability, config.fitnessStrategy,
                        config.stopStrategy, config.selectionStrategy);
                ow.write("\n" + testResults.toString());
                StringJoiner sj = new StringJoiner("\n");
                for (List<Double> fit : testResults.fitnesses) {
                    sj.add(fit.toString().replace("[", "").replace("]", "").replaceAll(", ", ","));
                }
                fitness.add(sj.toString());
            }
            fw.write(fitness.toString() + "\n");
            ow.close();
            fw.close();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static TestData run(int populationSize, int fitnessCounterLimit, double recombinationProbability,
            double mutationProbability, FitnessStrategy fitnessStrategy, StopStrategy stopStrategy,
            SelectionStrategy selectionStrategy) {
        List<Chromosome> chromosomes = generatePopulation(populationSize, fitnessStrategy);
        int fitnessCounter = populationSize;
        Collections.sort(chromosomes);
        int numberOfGenerations = 1;
        List<List<Double>> fitnesses = new ArrayList<>();
        fitnesses.add(chromosomes.stream().map(c -> c.getFitness()).collect(Collectors.toList()));

        List<Chromosome> parents, children;
        while (!stopStrategy.finished(chromosomes, fitnessStrategy, fitnessCounter, fitnessCounterLimit,
                numberOfGenerations)) {
            parents = selectionStrategy.getParents(chromosomes, populationSize / 2);
            children = generateChildren(parents, recombinationProbability, fitnessStrategy);
            fitnessCounter += 2;
            for (Chromosome c : chromosomes) {
                if (rnd.nextDouble() < mutationProbability) {
                    c.mutation(mutationProbability);
                    fitnessCounter++;
                }
            }
            insertChildren(chromosomes, children);
            Collections.sort(chromosomes);
            chromosomes = chromosomes.subList(0, populationSize);
            numberOfGenerations++;
            fitnesses.add(chromosomes.stream().map(c -> AckleyUtils.calculateAckleyFunction(c.getRepresentation()))
                    .collect(Collectors.toList()));
        }
        Collections.sort(chromosomes);
        System.out.println("----------------------");
        System.out.println(chromosomes.get(0));
        System.out.println(chromosomes.get(populationSize / 2));
        System.out.println(chromosomes.get(populationSize - 1));
        return new TestData(chromosomes, fitnesses, numberOfGenerations, fitnessStrategy);
    }

    private static void insertChildren(List<Chromosome> chromosomes, List<Chromosome> children) {
        Collections.sort(chromosomes);
        chromosomes.addAll(children);
    }

    private static List<Chromosome> generatePopulation(int populationSize, FitnessStrategy fitnessStrategy) {
        List<Chromosome> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new ChromosomeArr(fitnessStrategy));
            // System.out.println(Arrays.toString(population.get(0).getRepresentation()));
        }
        return population;
    }

    private static List<Chromosome> generateChildren(List<Chromosome> parents, double recombinationProbability,
            FitnessStrategy fitnessStrategy) {
        List<Chromosome> children = new ArrayList<>();
        for (int i = 0; i < parents.size() - 1; i += 2) {
            children.addAll(procreate(parents.get(i), parents.get(i + 1), recombinationProbability, fitnessStrategy));
        }
        return children;
    }

    private static List<Chromosome> procreate(Chromosome parent1, Chromosome parent2, double recombinationProbability,
            FitnessStrategy fitnessStrategy) {
        int splitPos = rnd.nextInt(28) + 1;
        List<Chromosome> children = new ArrayList<>();
        if (rnd.nextDouble() < recombinationProbability) {
            children.add(parent1.cutAndCrossfill(parent2, splitPos));
            children.add(parent2.cutAndCrossfill(parent1, splitPos));
        } else {
            children.add(new ChromosomeArr(parent1.getRepresentation(), fitnessStrategy));
            children.add(new ChromosomeArr(parent2.getRepresentation(), fitnessStrategy));
        }
        return children;
    }
}
