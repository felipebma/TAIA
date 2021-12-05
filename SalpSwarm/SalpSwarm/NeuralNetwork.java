package SalpSwarm;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NeuralNetwork {
    List<Neuron> input = new ArrayList<>();
    List<Neuron> hidden = new ArrayList<>();
    List<Neuron> output = new ArrayList<>();

    public NeuralNetwork(int input, int hidden, int output) {
        for (int i = 0; i < input; i++) {
            this.input.add(new Neuron());
        }
        for (int i = 0; i < hidden; i++) {
            this.hidden.add(new Neuron());
        }
        for (int i = 0; i < output; i++) {
            this.output.add(new Neuron());
        }
    }

    public void addInputHiddenWeight(int inputId, int hiddenId, double weight) {
        this.hidden.get(hiddenId).weight.put(this.input.get(inputId), weight);
    }

    public void setHiddenBias(int hiddenId, double bias) {
        this.hidden.get(hiddenId).bias = bias;
    }

    public void addHiddenOuputWeight(int hiddenId, int outputId, double weight) {
        this.output.get(outputId).weight.put(this.hidden.get(hiddenId), weight);
    }

    public void setOutputBias(int outputId, double bias) {
        this.output.get(outputId).bias = bias;
    }

    public List<Double> process(List<Double> input) {
        for (int i = 0; i < input.size(); i++) {
            this.input.get(i).value = input.get(i);
        }
        for (int i = 0; i < hidden.size(); i++) {
            Neuron h = this.hidden.get(i);
            h.value = h.bias;
            for (Neuron n : h.weight.keySet()) {
                h.value += n.value * h.weight.get(n);
            }
        }
        for (int i = 0; i < output.size(); i++) {
            Neuron o = this.output.get(i);
            o.value = o.bias;
            for (Neuron n : o.weight.keySet()) {
                o.value += n.value * o.weight.get(n);
            }
        }
        Double total = this.output.stream().map(n -> n.value).reduce(0.0, (sub, x) -> sub + x);
        return this.output.stream().map(n -> n.value / total).collect(Collectors.toList());
    }

}
