package SalpSwarm;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static int maxIterations = 1500;
    static int numberOfSalps = 50;
    static int inputSize;
    static int hiddenSize;
    static int outputSize;

    public static void main(String[] args) {
        run20(new Haberman());
        // run20(new Cancer());
        // run20(new Liver());
        // run20(new Thyroid());
    }

    // public static void run20(ExecutionDetails details) {
    // Double accuracySum = 0.0;
    // for (int i = 0; i < 20; i++) {
    // System.out.print(i + "-");
    // accuracySum += run(details).accuracy;
    // }
    // System.out.println(details.toString());
    // System.out.println("accuracy mean: " + accuracySum / 20.0);
    // }

    private static void run20(ExecutionDetails details) {
        try {
            File output = new File(String.format("../data/%s_result.csv", details.getClass().getName()));
            File fitnesses = new File(String.format("../data/%s_fit.csv", details.getClass().getName()));
            fitnesses.createNewFile();
            output.createNewFile();
            FileWriter ow = new FileWriter(output, false);
            FileWriter fw = new FileWriter(fitnesses, false);
            Map<Integer, List<Double>> fitnessesMap = new HashMap<>();
            List<Double> accuracies = new ArrayList<>();
            for (int i = 0; i < 20; i++) {
                System.out.print(i + "-");
                TestData testResults = run(details);
                accuracies.add(testResults.accuracy);
                for (int it : testResults.fitnesses.keySet()) {
                    fitnessesMap.putIfAbsent(it, new ArrayList<>());
                    fitnessesMap.get(it).addAll(testResults.fitnesses.get(it));
                }
            }
            StringJoiner fitness = new StringJoiner("\n");
            for (int i = 0; i < fitnessesMap.size(); i++) {
                fitness.add(fitnessesMap.get(i).toString().replace("[", "").replace("]", "").replaceAll(", ", ","));
            }
            ow.write(accuracies.toString().replace("[", "").replace("]", "").replaceAll(", ", ","));
            double accuracyMean = accuracies.stream().reduce(0.0, (a, b) -> a + b) / accuracies.size();
            ow.write("\n" + accuracyMean);
            System.out.println("accuracy mean: " + accuracyMean);
            fw.write(fitness.toString());
            ow.close();
            fw.close();
            System.gc();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static TestData run(ExecutionDetails details) {
        List<Data> data = details.getData();
        inputSize = details.inputSize;
        hiddenSize = details.hiddenSize;
        outputSize = details.outputSize;
        Collections.shuffle(data, new Random());
        int cutPosition = (int) (0.8 * data.size());
        List<Data> trainData = data.subList(0, cutPosition);
        List<Data> testData = data.subList(cutPosition, data.size());
        List<Salp> salps = new ArrayList<>();
        double bestFitness = Integer.MAX_VALUE;
        Salp bestSalp = null;
        TestData res = new TestData();
        for (int i = 0; i < numberOfSalps; i++) {
            salps.add(new Salp(inputSize, hiddenSize, outputSize, -1, 1));
            double fitness = salps.get(i).fitness(trainData);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestSalp = salps.get(i);
            }
        }
        salps.get(0).isLeader();
        for (int i = 0; i < maxIterations; i++) {
            List<Double> fitnesses = new ArrayList<>();
            salps.get(0).follow(bestSalp, i, maxIterations);
            bestFitness = salps.get(0).fitness(trainData);
            fitnesses.add(bestFitness);
            bestSalp = salps.get(0);
            for (int j = 1; j < salps.size(); j++) {
                salps.get(j).follow(salps.get(j - 1), i, maxIterations);
                double fitness = salps.get(j).fitness(trainData);
                fitnesses.add(fitness);
                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    bestSalp = salps.get(j);
                }
            }
            for (Salp salp : salps) {
                salp.fixDimensions();
            }
            if (i % 15 == 0) {
                System.out.print(".");
            }
            res.fitnesses.put(i, fitnesses);
            // System.out.println("best fitness: " + bestFitness);
        }
        System.out.println();
        NeuralNetwork nn = bestSalp.getNeuralNetwork();
        double correct = 0.0;
        for (Data test : testData) {
            List<Double> result = nn.process(test.input);
            int r = 0;
            for (int i = 1; i < result.size(); i++) {
                if (result.get(i) > result.get(r)) {
                    r = i;
                }
            }
            if (test.output.get(r) == 1) {
                correct++;
            }
        }
        // System.out.println("correct: " + correct);
        // System.out.println("total: " + testData.size());
        System.out.println("accuracy: " + correct / testData.size());
        res.accuracy = correct / testData.size();
        return res;
    }
}
