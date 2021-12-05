package SalpSwarm;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Salp {
    List<List<Double>> hiddenWeights = new ArrayList<>();
    List<Double> hiddenBias = new ArrayList<>();
    List<List<Double>> outputWeights = new ArrayList<>();
    List<Double> outputBias = new ArrayList<>();
    Double min, max;
    Random rnd = new Random();
    int input, hidden, output;
    boolean isLeader;

    public Salp(int input, int hidden, int ouput, double min, double max) {
        this.min = min;
        this.max = max;
        this.input = input;
        this.hidden = hidden;
        this.output = ouput;
        for (int i = 0; i < hidden; i++) {
            this.hiddenBias.add(min + rnd.nextDouble() * (max - min));
            this.hiddenWeights.add(new ArrayList<>());
            for (int j = 0; j < input; j++) {
                this.hiddenWeights.get(i).add(min + rnd.nextDouble() * (max - min));
            }
        }
        for (int i = 0; i < output; i++) {
            this.outputBias.add(min + rnd.nextDouble() * (max - min));
            this.outputWeights.add(new ArrayList<>());
            for (int j = 0; j < hidden; j++) {
                this.outputWeights.get(i).add(min + rnd.nextDouble() * (max - min));
            }
        }
    }

    public NeuralNetwork getNeuralNetwork() {
        NeuralNetwork nn = new NeuralNetwork(input, hidden, output);
        for (int i = 0; i < hidden; i++) {
            nn.setHiddenBias(i, this.hiddenBias.get(i));
            for (int j = 0; j < input; j++) {
                nn.addInputHiddenWeight(j, i, this.hiddenWeights.get(i).get(j));
            }
        }
        for (int i = 0; i < output; i++) {
            nn.setOutputBias(i, this.outputBias.get(i));
            for (int j = 0; j < hidden; j++) {
                nn.addHiddenOuputWeight(j, i, this.outputWeights.get(i).get(j));
            }
        }
        return nn;
    }

    public double fitness(List<Data> trainData) {
        NeuralNetwork nn = this.getNeuralNetwork();
        double fitness = 0.0;
        for (Data data : trainData) {
            List<Double> output = nn.process(data.input);
            for (int i = 0; i < output.size(); i++) {
                fitness += Math.pow(output.get(i) - data.output.get(i), 2);
            }
        }
        return fitness;
    }

    public void follow(Salp other, int currIteration, int maxIteration) {
        updateValuesList(currIteration, maxIteration, this.hiddenBias, other.hiddenBias);
        for (int i = 0; i < this.hiddenWeights.size(); i++) {
            updateValuesList(currIteration, maxIteration, this.hiddenWeights.get(i), other.hiddenWeights.get(i));
        }
        updateValuesList(currIteration, maxIteration, this.outputBias, other.outputBias);
        for (int i = 0; i < this.outputWeights.size(); i++) {
            updateValuesList(currIteration, maxIteration, this.outputWeights.get(i), other.outputWeights.get(i));
        }
    }

    private void updateValuesList(int currIteration, int maxIteration, List<Double> listA, List<Double> listB) {
        double c1 = 2 * Math.exp(-1.0 * Math.pow((4.0 * currIteration) / maxIteration, 2));
        for (int i = 0; i < listA.size(); i++) {
            if (this.isLeader) {
                double c2 = rnd.nextDouble(), c3 = rnd.nextDouble();
                double newValue = listB.get(i) + (c3 >= 0.5 ? c1 : -c1) * ((max - min) * c2 + min);
                listA.set(i, newValue);
            } else {
                listA.set(i, (listA.get(i) + listB.get(i)) / 2);
            }
        }
    }

    public void isLeader() {
        this.isLeader = true;
    }

	public void fixDimensions() {
        for(int i = 0; i < hiddenBias.size(); i++){
            hiddenBias.set(i, Math.max(min, Math.min(max, hiddenBias.get(i))));
        }
        for(int i = 0; i < hiddenWeights.size(); i++){
            for(int j = 0; j < hiddenWeights.get(i).size(); j++){
                hiddenWeights.get(i).set(j, Math.max(min, Math.min(max, hiddenWeights.get(i).get(j))));
            }
        }
        for(int i = 0; i < outputBias.size(); i++){
            outputBias.set(i, Math.max(min, Math.min(max, outputBias.get(i))));
        }
        for(int i = 0; i < outputWeights.size(); i++){
            for(int j = 0; j < outputWeights.get(i).size(); j++){
                outputWeights.get(i).set(j, Math.max(min, Math.min(max, outputWeights.get(i).get(j))));
            }
        }
	}
}
