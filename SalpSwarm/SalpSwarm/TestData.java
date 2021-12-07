package SalpSwarm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestData {
    Map<Integer, List<Double>> fitnesses = new HashMap<>();
    Double accuracy;

    public TestData() {
    }

    public TestData(Map<Integer, List<Double>> fitnesses, Double accuracy) {
        this.fitnesses = fitnesses;
        this.accuracy = accuracy;
    }

}
