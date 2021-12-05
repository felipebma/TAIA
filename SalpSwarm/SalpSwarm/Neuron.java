package SalpSwarm;

import java.util.HashMap;
import java.util.Map;

class Neuron {
    Map<Neuron, Double> weight = new HashMap<>();
    Double bias = 0.0;
    Double value = 0.0;
}
