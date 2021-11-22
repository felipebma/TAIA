package Ackley;

import java.util.Arrays;
import java.util.Random;

class ChromosomeArr implements Chromosome {

    Random rnd = new Random();
    Double[] representation;
    Double fitness;
    FitnessStrategy fitnessStrategy;

    public ChromosomeArr(FitnessStrategy fitnessStrategy) {
        representation = new Double[30];

        for (int i = 0; i < 30; i++) {
            Double randomValue = AckleyUtils.randomDoubleInRange(-15.0, 15.0, rnd);
            representation[i] = randomValue;
        }
        shuffle(representation);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    public ChromosomeArr(Double[] arr, FitnessStrategy fitnessStrategy) {
        representation = Arrays.copyOf(arr, arr.length);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    @Override
    public Chromosome cutAndCrossfill(Chromosome other, int splitPos) {
        Double[] newChild = new Double[30];
        Double[] otherRepresentation = other.getRepresentation();
        for (int i = 0; i < splitPos; i++) {
            newChild[i] = this.representation[i];
        }
        for (int i = splitPos; i < 30; i++) {
            newChild[i] = otherRepresentation[i];
        }
        return new ChromosomeArr(newChild, this.fitnessStrategy);
    }

    public void mutation(Double mutationProbability) {
        for (int i = 0; i < 30; i++) {
                representation[i] = AckleyUtils.randomDoubleInRange(Math.max(representation[i] - 3.0, -15.0),
                        Math.min(representation[i] + 3.0, 15.0), rnd);
        }
        fitness();
    }

    private void fitness() {
        this.fitness = this.fitnessStrategy.calculateFitness(this.representation);
    }

    private void shuffle(Double[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            swap(arr, i, pos);
        }
    }

    private void swap(Double[] arr, int i, int j) {
        Double aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }

    @Override
    public String toString() {
        return String.format("\trepresentation: %s\n\tAckley: %.10f\n\tFitness: %.10f",
                Arrays.toString(this.representation), AckleyUtils.calculateAckleyFunction(this.representation), this.fitness);
    }

    @Override
    public int compareTo(Chromosome o) {
        Double comp = o.getFitness() - fitness;
        if (comp < 0.0)
            return -1;
        else if (comp > 0.0)
            return 1;
        else
            return 0;
    }

    @Override
    public Double getFitness() {
        return this.fitness;
    }

    @Override
    public Double[] getRepresentation() {
        return this.representation;
    }
}
