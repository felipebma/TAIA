package SalpSwarm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Cancer {

    static Scanner in = new Scanner(System.in);
    static int maxIterations = 10000;
    static int numberOfSalps = 50;
    static int inputSize = 9;
    static int hiddenSize = 8;
    static int outputSize = 2;

    public static void main(String[] args) {
        List<Data> data = getData();
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
            for(Salp salp : salps){
                salp.fixDimensions();
            }
            System.out.println("best fitness: " + bestFitness);
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
        System.out.println("correct: " + correct);
        System.out.println("total: " + testData.size());
        System.out.println("accuracy: " + correct / testData.size());
    }

    private static List<Data> getData() {
        List<Data> data = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            List<Double> input = new ArrayList<>();
            List<Double> output = new ArrayList<>(Arrays.asList(0.0, 0.0));
            for (int i = 1; i < 10; i++) {
                input.add(line[i].equals("?") ? 0 : Double.parseDouble(line[i]));
            }
            output.set((Integer.parseInt(line[10]) - 2) / 2, 1.0);
            data.add(new Data(input, output));
        }
        return data;
    }

    // private static void testNN() {
    // NeuralNetwork nn = new NeuralNetwork(2, 2, 2);

    // nn.addInputHiddenWeight(0, 0, 2);
    // nn.addInputHiddenWeight(0, 1, 0);
    // nn.addInputHiddenWeight(1, 0, 0);
    // nn.addInputHiddenWeight(1, 1, 1);

    // nn.addHiddenOuputWeight(0, 0, 1);
    // nn.addHiddenOuputWeight(0, 1, 0);
    // nn.addHiddenOuputWeight(1, 0, 1);
    // nn.addHiddenOuputWeight(1, 1, 1);

    // System.out.println(nn.process(Arrays.asList(1.0, 1.0)));
    // System.out.println(nn.output.stream().map(x ->
    // x.value).collect(Collectors.toList()));

    // }
}
