package Ackley;

public interface Chromosome extends Comparable<Chromosome> {

    public Chromosome cutAndCrossfill(Chromosome other, int begin, int end);

    public Chromosome cutAndCrossfill(Chromosome other, int end);

    public int getFitness();

    public void mutation();

    public double[] getRepresentation();
}
