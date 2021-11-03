package Queens;
import java.util.List;

public interface StopStrategy {
    public boolean finished(List<Chromosome> chromosomes, FitnessStrategy fitnessStrategy, int fitnessCounter,
    int fitnessCounterLimit, int numberOfGenerations);

    static StopStrategy findSolution() {

        return (chromosomes, fitnessStrategy, fitnessCounter, fitnessCounterLimit, numberOfGenerations) -> {
           return  !(chromosomes.get(0).getFitness() < fitnessStrategy.maxFitness()
                && fitnessCounter < fitnessCounterLimit);
        };
        
    };

    static StopStrategy runAllGenerations() {

        return (chromosomes, fitnessStrategy, fitnessCounter, fitnessCounterLimit, numberOfGenerations) -> {
           return  !(numberOfGenerations < 100);
        };
        
    };
}
