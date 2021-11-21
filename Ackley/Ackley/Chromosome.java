package Ackley;

public interface Chromosome extends Comparable<Chromosome> {

    //public Chromosome cutAndCrossfill(Chromosome other, int begin, int end);

    public Chromosome cutAndCrossfill(Chromosome other, int splitPos);

    public double getFitness();

    public void mutation(double mutationProbability);

    public double[] getRepresentation();
}
