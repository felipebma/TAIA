package Ackley;

import java.util.Arrays;
import java.util.Random;

class ChromosomeArr implements Chromosome {

    Random rnd = new Random();
    double[] representation;
    int fitness;
    FitnessStrategy fitnessStrategy;

    public ChromosomeArr(FitnessStrategy fitnessStrategy) {
        representation = new double[] { 0, 1, 2, 3, 4, 5, 6, 7 }; // TODO: initialize this with 30 random numbers
                                                                  // between -15 and 15
        shuffle(representation);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    public ChromosomeArr(double[] arr, FitnessStrategy fitnessStrategy) {
        representation = Arrays.copyOf(arr, arr.length);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    @Override
    public Chromosome cutAndCrossfill(Chromosome other, int begin, int end) {
        double[] newChild = new double[8];
        double[] otherRepresentation = other.getRepresentation();
        // TODO: implement this again
        // Set<Integer> used = new HashSet<>();
        // for (int i = begin; i < end; i++) {
        // newChild[i] = this.representation[i];
        // used.add(newChild[i]);
        // }
        // int count = end - begin, j = end % 8;
        // while (count < 8) {
        // if (!used.contains(otherRepresentation[j])) {
        // newChild[(begin + count) % 8] = otherRepresentation[j];
        // used.add(otherRepresentation[j]);
        // count++;
        // }
        // j = (j + 1) % 8;
        // }
        return new ChromosomeArr(newChild, this.fitnessStrategy);
    }

    @Override
    public Chromosome cutAndCrossfill(Chromosome other, int end) {
        return cutAndCrossfill(other, 0, end);
    }

    public void mutation() {
        int i = rnd.nextInt(8), j = i;
        while (j == i) {
            j = rnd.nextInt(8);
        }
        swap(representation, i, j);
        fitness();
    }

    private void fitness() {
        this.fitness = this.fitnessStrategy.calculateFitness(this.representation);
    }

    private void shuffle(double[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            swap(arr, i, pos);
        }
    }

    private void swap(double[] arr, int i, int j) {
        double aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }

    @Override
    public String toString() {
        return String.format("\tQueens Positions: %s\n\tFitness: %d\n\tBitRepresentation: %s",
                Arrays.toString(this.representation), this.fitness, this.getRepresentation());
    }

    @Override
    public int compareTo(Chromosome o) {
        return o.getFitness() - fitness;
    }

    @Override
    public int getFitness() {
        return this.fitness;
    }

    @Override
    public double[] getRepresentation() {
        return this.representation;
    }
}
