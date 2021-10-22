package Queens;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

class Cromossomo {

    Random rnd = new Random();
    int[] queens;
    int fitness;

    public Cromossomo() {
        queens = new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
        shuffle(queens);
        fitness();
    }

    public Cromossomo(int[] arr) {
        queens = Arrays.copyOf(arr, arr.length);
        fitness();
    }

    public Cromossomo cutAndCrossfill(Cromossomo other, int begin, int end) {
        int[] newChild = new int[8];
        Set<Integer> used = new HashSet<>();
        for (int i = begin; i < end; i++) {
            newChild[i] = this.queens[i];
            used.add(newChild[i]);
        }
        int count = end - begin, j = end % 8;
        while (count < 8) {
            if (!used.contains(other.queens[j])) {
                newChild[(begin + count) % 8] = other.queens[j];
                used.add(other.queens[j]);
                count++;
            }
            j = (j + 1) % 8;
        }
        return new Cromossomo(newChild);
    }

    public Cromossomo cutAndCrossfill(Cromossomo other, int end) {
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
                if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) {
                    count++;
                }
            }
            if (count > 1) {
                fitness--;
            }
        }
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

    @Override
    public String toString() {
        return String.format("Queens Positions: %s Fitness: %d", Arrays.toString(this.queens), this.fitness);
    }
}
