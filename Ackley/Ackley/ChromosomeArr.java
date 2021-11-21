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
            Double randomValue = AckleyUtils.randomDoubleInRange(-15.0, 15.0);
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

    //@Override
    //public Chromosome cutAndCrossfill(Chromosome other, int begin, int end) {
        //Double[] newChild = new Double[30];
        //Double[] otherRepresentation = other.getRepresentation();
        //// Set<Integer> used = new HashSet<>();
        //// for (int i = begin; i < end; i++) {
        //// newChild[i] = this.representation[i];
        //// used.add(newChild[i]);
        //// }
        //// int count = end - begin, j = end % 8;
        //// while (count < 8) {
        //// if (!used.contains(otherRepresentation[j])) {
        //// newChild[(begin + count) % 8] = otherRepresentation[j];
        //// used.add(otherRepresentation[j]);
        //// count++;
        //// }
        //// j = (j + 1) % 8;
        //// }


        //return new ChromosomeArr(newChild, this.fitnessStrategy);
    //}

    @Override
    public Chromosome cutAndCrossfill(Chromosome other, int splitPos) {
        Double[] newChild = new Double[30];
        Double[] otherRepresentation = other.getRepresentation();
        for(int i = 0; i < splitPos; i++){
            newChild[i] = this.representation[i];
        }
        for(int i = splitPos; i < 30; i++){
            newChild[i] = otherRepresentation[i];
        }

        return new ChromosomeArr(newChild, this.fitnessStrategy);
    }

    public void mutation(Double mutationProbability) {
        for (int i = 0; i < 30; i++) {
            if (rnd.nextDouble() < mutationProbability) {
                representation[i] = AckleyUtils.randomDoubleInRange(-15.0, 15.0);
            }
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
        String representationString = "";
        String ends [] = {", ", "\n"};
        for(int i = 0; i < 30; i++){
            int pos = (i >= 29) ? 1 : 0;
            representationString += this.representation[i] + ends[pos];
        }
        return String.format("\trepresentation: %s\n\tFitness: %.3f",
                representationString, this.fitness);
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
