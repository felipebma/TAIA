package Ackley;

public interface Chromosome extends Comparable<Chromosome> {

    public Chromosome cutAndCrossfill(Chromosome other, int splitPos);

    public Double getFitness();

    public void mutation(Double mutationProbability);

    public Double[] getRepresentation();
}
