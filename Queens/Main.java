package Queens;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {

    private static Random rnd = new Random();
    private static int populationSize = 100;
    private static int fitnessCounter = populationSize;
    private static int fitnessCounterLimit = (int) 1e4;
    private static double recombinationProbability = 0.6;
    private static double mutationProbability = 0.4;

    public static void main(String[] args) {
        List<Cromossomo> cromossomos = generatePopulation(populationSize);
        cromossomos.sort((a, b) -> b.fitness - a.fitness);
        int numberOfGenerations = 1;
        while (cromossomos.get(0).fitness < 8 && fitnessCounter < fitnessCounterLimit) {
            System.out.println("Best permutation: " + cromossomos.get(0));
            List<Cromossomo> parents = getParents(cromossomos);
            List<Cromossomo> children = generateChildren(parents);
            fitnessCounter += 2;
            for (Cromossomo c : cromossomos) {
                if (rnd.nextDouble() < mutationProbability) {
                    c.mutation();
                    fitnessCounter++;
                }
            }
            cromossomos.sort((a, b) -> b.fitness - a.fitness);
            cromossomos.remove(cromossomos.size() - 1);
            cromossomos.remove(cromossomos.size() - 1);
            cromossomos.addAll(children);
            cromossomos.sort((a, b) -> b.fitness - a.fitness);
            numberOfGenerations++;
        }
        System.out.println("Best permutation: " + cromossomos.get(0));
        System.out.println("Number of generations: " + numberOfGenerations);
    }

    private static List<Cromossomo> generatePopulation(int populationSize) {
        List<Cromossomo> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            population.add(new Cromossomo());
        }
        return population;
    }

    private static List<Cromossomo> getParents(List<Cromossomo> population) {
        Collections.shuffle(population, rnd);
        List<Cromossomo> randomFive = population.subList(0, 5);
        randomFive.sort((a, b) -> b.fitness - a.fitness);
        return randomFive.subList(0, 2);
    }

    private static List<Cromossomo> generateChildren(List<Cromossomo> parents) {
        int splitPos = rnd.nextInt(6) + 1;
        List<Cromossomo> children = new ArrayList<>();
        if (rnd.nextDouble() < recombinationProbability) {
            children.add(parents.get(0).cutAndCrossfill(parents.get(1), splitPos));
            children.add(parents.get(1).cutAndCrossfill(parents.get(0), splitPos));
        } else {
            children.add(new Cromossomo(parents.get(0).queens));
            children.add(new Cromossomo(parents.get(1).queens));
        }
        return children;
    }
}
