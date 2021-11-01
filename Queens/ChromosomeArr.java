package Queens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class ChromosomeArr implements Chromosome {

    Random rnd = new Random();
    int[] queens;
    int fitness;
    FitnessStrategy fitnessStrategy;

    public ChromosomeArr(FitnessStrategy fitnessStrategy) {
        queens = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
        shuffle(queens);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    public ChromosomeArr(int[] arr, FitnessStrategy fitnessStrategy) {
        queens = Arrays.copyOf(arr, arr.length);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    public ChromosomeArr(String binaryRepresentation, FitnessStrategy fitnessStrategy) {
        queens = binaryToArr(binaryRepresentation);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    @Override
    public Chromosome cutAndCrossfill(Chromosome other, int begin, int end) {
        int[] newChild = new int[8];
        int[] otherQueens = binaryToArr(other.getBitRepresentation());
        Set<Integer> used = new HashSet<>();
        for (int i = begin; i < end; i++) {
            newChild[i] = this.queens[i];
            used.add(newChild[i]);
        }
        int count = end - begin, j = end % 8;
        while (count < 8) {
            if (!used.contains(otherQueens[j])) {
                newChild[(begin + count) % 8] = otherQueens[j];
                used.add(otherQueens[j]);
                count++;
            }
            j = (j + 1) % 8;
        }
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
        swap(queens, i, j);
        fitness();
    }

    private void fitness() {
        this.fitness = this.fitnessStrategy.calculateFitness(queens);
    }

    private void shuffle(int[] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            swap(arr, i, pos);
        }
    }

    private void swap(int[] arr, int i, int j) {
        int aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }

    private int[] binaryToArr(String binaryRepresentation) {
        int[] arr = new int[8];
        for (int i = 0; i < 8; i++) {
            arr[i] = Integer.parseInt(binaryRepresentation.substring(i * 3, i * 3 + 3), 2);
        }
        return arr;
    }

    @Override
    public String toString() {
        return String.format("\tQueens Positions: %s\n\tFitness: %d\n\tBitRepresentation: %s",
                Arrays.toString(this.queens), this.fitness, this.getBitRepresentation());
    }

    @Override
    public int compareTo(Chromosome o) {
        return o.getFitness() - fitness;
    }

    @Override
    public String getBitRepresentation() {
        StringBuilder sb = new StringBuilder();
        for (int i : this.queens) {
            sb.append(String.format("%3s", Integer.toBinaryString(i)).replaceAll(" ", "0"));
        }
        return sb.toString();
    }

    @Override
    public int getFitness() {
        return this.fitness;
    }
}
