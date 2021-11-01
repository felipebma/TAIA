package Queens;

public class TestConfig {
    String fileName;
    int populationSize;
    int fitnessCounterLimit;
    double recombinationProbability;
    double mutationProbability;
    FitnessStrategy fitnessStrategy;

    public TestConfig(String fileName, int populationSize, int fitnessCounterLimit, double recombinationProbability,
            double mutationProbability, FitnessStrategy fitnessStrategy) {
        this.fileName = fileName;
        this.populationSize = populationSize;
        this.fitnessCounterLimit = fitnessCounterLimit;
        this.recombinationProbability = recombinationProbability;
        this.mutationProbability = mutationProbability;
        this.fitnessStrategy = fitnessStrategy;
    }
}
