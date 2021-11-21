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
        // runDefault();
        // runAlternativeFitness();
        // runAlternativeFitnessRoleta();
        // runLowerMutation();
        runLowerMutationRoleta();
        runLowerMutationRoletaSmallPopulation();
    }

    private static void runDefault() {
        runTest(new TestConfig("default", 100, (int) 1e4, 0.9, 0.4, FitnessStrategy.normalStrategy,
                StopStrategy.runAllGenerations(), SelectionStrategy.get2OutOf5Random));
    }

    private static void runAlternativeFitness() {
        runTest(new TestConfig("alternativeFitness", 100, (int) 1e4, 0.9, 0.4, FitnessStrategy.alternativeStrategy,
                StopStrategy.runAllGenerations(), SelectionStrategy.get2OutOf5Random));
    }

    private static void runAlternativeFitnessRoleta() {
        runTest(new TestConfig("alternativeFitnessRoleta", 100, (int) 1e4, 0.9, 0.4,
                FitnessStrategy.alternativeStrategy, StopStrategy.runAllGenerations(),
                SelectionStrategy.wheightedRandom));
    }

    private static void runLowerMutation() {
        runTest(new TestConfig("lowerMutation", 100, (int) 1e4, 0.9, 0.1, FitnessStrategy.alternativeStrategy,
                StopStrategy.runAllGenerations(), SelectionStrategy.get2OutOf5Random));
    }

    private static void runLowerMutationRoleta() {
        runTest(new TestConfig("lowerMutationRoleta", 100, (int) 1e4, 0.9, 0.1, FitnessStrategy.alternativeStrategy,
                StopStrategy.runAllGenerations(), SelectionStrategy.wheightedRandom));
    }

    private static void runLowerMutationRoletaSmallPopulation() {
        runTest(new TestConfig("runLowerMutationRoletaSmallPopulation", 20, (int) 1e4, 0.9, 0.1,
                FitnessStrategy.alternativeStrategy,
                StopStrategy.runAllGenerations(), SelectionStrategy.wheightedRandom));
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
            for (int i = 0; i < 30; i++) {
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
        while (!stopStrategy.finished(chromosomes, fitnessStrategy, fitnessCounter, fitnessCounterLimit,
                numberOfGenerations)) {
            List<Chromosome> parents = selectionStrategy.getParents(chromosomes);
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
            fitnesses.add(chromosomes.stream().map(c -> c.getFitness()).collect(Collectors.toList()));
        }
        System.out.println(chromosomes.get(0));
        return new TestData(chromosomes, fitnesses, numberOfGenerations, fitnessStrategy);
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

    private static List<Chromosome> generateChildren(List<Chromosome> parents, double recombinationProbability,
            FitnessStrategy fitnessStrategy) {
        int splitPos = rnd.nextInt(6) + 1;
        List<Chromosome> children = new ArrayList<>();
        if (rnd.nextDouble() < recombinationProbability) {
            children.add(parents.get(0).cutAndCrossfill(parents.get(1), splitPos));
            children.add(parents.get(1).cutAndCrossfill(parents.get(0), splitPos));
        } else {
            children.add(new ChromosomeArr(parents.get(0).getRepresentation(), fitnessStrategy));
            children.add(new ChromosomeArr(parents.get(1).getRepresentation(), fitnessStrategy));
        }
        return children;
    }
}
