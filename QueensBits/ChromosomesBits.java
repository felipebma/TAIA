package QueensBits;

import java.util.*;

public class ChromosomesBits implements Comparable<ChromosomesBits> {
    Random rnd = new Random();
    int[][] queens;
    int fitness;

    public ChromosomesBits() {// TODO: usar string no lugar de int[][]
        queens = new int[][]{
                {0, 0, 0},
                {0, 0, 1},
                {0, 1, 0},
                {0, 1, 1},
                {1, 0, 0},
                {1, 0, 1},
                {1, 1, 0},
                {1, 1, 1}
        };
        shuffle(queens);
        fitness();
    }

    public ChromosomesBits(int[][] arr) {
        queens = Arrays.copyOf(arr, arr.length);
        fitness();
    }

    public ChromosomesBits cutAndCrossfill(ChromosomesBits other, int begin, int end) {
        int[][] newChild = new int[8][3];
        Set<Integer> used = new HashSet<>();
        for (int i = begin; i < end; i++) {
            newChild[i] = this.queens[i];
            used.add(toInt(newChild[i]));
        }
        int count = end - begin, j = end % 8;
        while (count < 8) {
            if (!used.contains(toInt(other.queens[j]))) {
                newChild[(begin + count) % 8] = other.queens[j];
                used.add(toInt(other.queens[j]));
                count++;
            }
            j = (j + 1) % 8;
        }
        return new ChromosomesBits(newChild);
    }

    /**
     * Converts bit array to integer.
     *
     * @param threeBitArray
     * @return integer representation of the bits in the array
     */
    private Integer toInt(int[] threeBitArray) {
        return threeBitArray[0] + threeBitArray[1] * 2 + threeBitArray[2] * 4;
    }

    public ChromosomesBits cutAndCrossfill(ChromosomesBits other, int end) {
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
        fitness = 8;
        for (int i = 0; i < 8; i++) {
            int count = 0;
            for (int j = 0; j < 8; j++) {
                if (Math.abs(toInt(queens[i]) - toInt(queens[j])) == Math.abs(i - j)) {
                    count++;
                }
            }
            if (count > 1) {
                fitness--;
            }
        }
    }

    private void shuffle(int[][] arr) {
        for (int i = arr.length - 1; i > 0; i--) {
            int pos = rnd.nextInt(i + 1);
            swap(arr, i, pos);
        }
    }

    private void swap(int[][] arr, int i, int j) {
        int[] aux = arr[i];
        arr[i] = arr[j];
        arr[j] = aux;
    }

    @Override
    public String toString() { // TODO: test this
        return String.format("Queens Positions: %s Fitness: %d", this.integerString(), this.fitness);
    }

    private String integerString() {
        StringJoiner res = new StringJoiner(", ");
        for (int i = 0; i < 8; i++) {
            int[] queen = this.queens[i];
            res.add(toInt(queen).toString());
        }
        return "[ " + res + " ]";
    }

    @Override
    public int compareTo(ChromosomesBits o) {
        return o.fitness - fitness;
    }
}
