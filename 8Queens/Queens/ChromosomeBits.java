package Queens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.StringJoiner;

public class ChromosomeBits implements Chromosome {
    Random rnd = new Random();
    char[] queens;
    int fitness;
    FitnessStrategy fitnessStrategy;

    public ChromosomeBits(FitnessStrategy fitnessStrategy) {
        queens = new char[] { '0', '0', '0', '0', '0', '1', '0', '1', '0', '0', '1', '1', '1', '0', '0', '1', '0', '1',
                '1', '1', '0', '1', '1', '1' };
        shuffle(queens);
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    public ChromosomeBits(char[] arr, FitnessStrategy fitnessStrategy) {
        queens = Arrays.copyOf(arr, arr.length);
        fitness();
    }

    public ChromosomeBits(String binaryRepresentation, FitnessStrategy fitnessStrategy) {
        queens = binaryRepresentation.toCharArray();
        this.fitnessStrategy = fitnessStrategy;
        fitness();
    }

    @Override
    public ChromosomeBits cutAndCrossfill(Chromosome other, int begin, int end) {
        char[] newChild = new char[24];
        char[] otherQueens = binaryToArr(other.getBitRepresentation());
        Set<Integer> used = new HashSet<>();
        for (int i = begin; i < end; i++) {
            int pos = 3 * i;
            copyThreeSizedSubArray(newChild, pos, this.queens, pos);
            used.add(toInt(getThreeSizedSubArray(newChild, pos)));
        }
        int count = end - begin, j = end % 8;
        while (count < 8) {
            if (!used.contains(toInt(getThreeSizedSubArray(newChild, j * 3)))) {
                copyThreeSizedSubArray(newChild, ((begin + count) % 8) * 3, otherQueens, j * 3);
                used.add(toInt(getThreeSizedSubArray(otherQueens, j * 3)));
                count++;
            }
            j = (j + 1) % 8;
        }
        return new ChromosomeBits(newChild, this.fitnessStrategy);
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
        this.fitness = this.fitnessStrategy.calculateFitness(getBitRepresentation());
    }

    private void shuffle(char[] arr) {
        for (int i = arr.length / 3 - 1; i > 0; i--) {
            int pos1 = i;
            int pos2 = rnd.nextInt(i + 1) * 3;
            swap(arr, pos1, pos2);
        }
    }

    private void swap(char[] arr, int i, int j) {
        for (int k = 0; k < 3; k++) {
            char aux = arr[i + k];
            arr[i + k] = arr[j + k];
            arr[j + k] = aux;
        }
    }

    private char[] binaryToArr(String bitRepresentation) {
        return bitRepresentation.toCharArray();
    }

    @Override
    public String toString() {
        return String.format("Queens Positions: %s Fitness: %d", this.integerString(), this.fitness);
    }

    @Override
    public int compareTo(Chromosome o) {
        return o.getFitness() - this.getFitness();
    }

    @Override
    public String getBitRepresentation() {
        return String.valueOf(this.queens);
    }

    @Override
    public int getFitness() {
        return this.fitness;
    }

    private char[] getThreeSizedSubArray(char[] newChild, int pos) {
        return new char[] { newChild[pos], newChild[pos + 1], newChild[pos + 2] };
    }

    private void copyThreeSizedSubArray(char[] arr1, int pos1, char[] arr2, int pos2) {
        arr1[pos1] = arr2[pos2];
        arr1[pos1 + 1] = arr2[pos2 + 1];
        arr1[pos1 + 2] = arr2[pos2 + 2];
    }

    /**
     * Converts bit array to integer.
     *
     * @param threeBitArray
     * @return integer representation of the bits in the array
     */
    private Integer toInt(char[] threeBitArray) {
        return (threeBitArray[0] - '0') + (threeBitArray[1] - '0') * 2 + (threeBitArray[2] - '0') * 4;
    }

    private String integerString() {
        StringJoiner res = new StringJoiner(", ");
        for (int i = 0; i < 8; i++) {
            char[] queen = getThreeSizedSubArray(this.queens, i * 3);
            res.add(toInt(queen).toString());
        }
        return "[ " + res + " ]";
    }
}
