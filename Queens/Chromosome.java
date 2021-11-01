package Queens;

public interface Chromosome extends Comparable<Chromosome> {

    public Chromosome cutAndCrossfill(Chromosome other, int begin, int end);

    public Chromosome cutAndCrossfill(Chromosome other, int end);

    public String getBitRepresentation();

    public int getFitness();

    public void mutation();
}
