package SalpSwarm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {

    static Scanner in = new Scanner(System.in);
    static int maxIterations = 1500;
    static int numberOfSalps = 50;
    static int inputSize;
    static int hiddenSize;
    static int outputSize;

    public static void main(String[] args) {
        // run20(new Haberman());
        run20(new Cancer());
    }

    public static void run20(ExecutionDetails details) {
        Double accuracySum = 0.0;
        for (int i = 0; i < 20; i++) {
            System.out.print(i + "-");
            accuracySum += run(details);
        }
        System.out.println(details.toString());
        System.out.println("accuracy mean: " + accuracySum / 20.0);
    }

    private static Double run(ExecutionDetails details) {
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
            salps.get(0).follow(bestSalp, i, maxIterations);
            bestFitness = salps.get(0).fitness(trainData);
            bestSalp = salps.get(0);
            for (int j = 1; j < salps.size(); j++) {
                salps.get(j).follow(salps.get(j - 1), i, maxIterations);
                double fitness = salps.get(j).fitness(trainData);
                if (fitness < bestFitness) {
                    bestFitness = fitness;
                    bestSalp = salps.get(j);
                }
            }
            for (Salp salp : salps) {
                salp.fixDimensions();
            }
            // System.out.println("best fitness: " + bestFitness);
        }
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
        return correct / testData.size();
    }
}
