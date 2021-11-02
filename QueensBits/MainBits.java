package QueensBits;
// TODO: this should be executed by the other Main.java

// import java.util.ArrayList;
// import java.util.Collections;
// import java.util.List;
// import java.util.Random;

// import Queens.ChromosomesBits;

// public class MainBits {
//     private static final Random rnd = new Random();
//     private static final int populationSize = 100;
//     private static int fitnessCounter = populationSize;
//     private static final int fitnessCounterLimit = (int) 1e4;
//     private static final double recombinationProbability = 0.6;
//     private static final double mutationProbability = 0.4;

//     public static void main(String[] args) {
//         List<ChromosomesBits> chromosomes = generatePopulation(populationSize);
//         Collections.sort(chromosomes);
//         int numberOfGenerations = 1;
//         while (chromosomes.get(0).fitness < 8 && fitnessCounter < fitnessCounterLimit) {
//             System.out.println("Best permutation: " + chromosomes.get(0));
//             List<ChromosomesBits> parents = getParents(chromosomes);
//             List<ChromosomesBits> children = generateChildren(parents);
//             fitnessCounter += 2;
//             for (ChromosomesBits c : chromosomes) {
//                 if (rnd.nextDouble() < mutationProbability) {
//                     c.mutation();
//                     fitnessCounter++;
//                 }
//             }
//             Collections.sort(chromosomes);
//             chromosomes.remove(chromosomes.size() - 1);
//             chromosomes.remove(chromosomes.size() - 1);
//             chromosomes.addAll(children);
//             Collections.sort(chromosomes);
//             numberOfGenerations++;
//         }
//         System.out.println("Best permutation: " + chromosomes.get(0));
//         System.out.println("Number of generations: " + numberOfGenerations);
//     }

//     private static List<ChromosomesBits> generatePopulation(int populationSize) {
//         List<ChromosomesBits> population = new ArrayList<>();
//         for (int i = 0; i < populationSize; i++) {
//             population.add(new ChromosomesBits());
//         }
//         return population;
//     }

//     private static List<ChromosomesBits> getParents(List<ChromosomesBits> population) {
//         Collections.shuffle(population, rnd);
//         List<ChromosomesBits> randomFive = population.subList(0, 5);
//         Collections.sort(randomFive);
//         return randomFive.subList(0, 2);
//     }

//     private static List<ChromosomesBits> generateChildren(List<ChromosomesBits> parents) {
//         int splitPos = rnd.nextInt(6) + 1;
//         List<ChromosomesBits> children = new ArrayList<>();
//         if (rnd.nextDouble() < recombinationProbability) {
//             children.add(parents.get(0).cutAndCrossfill(parents.get(1), splitPos));
//             children.add(parents.get(1).cutAndCrossfill(parents.get(0), splitPos));
//         } else {
//             children.add(new ChromosomesBits(parents.get(0).queens));
//             children.add(new ChromosomesBits(parents.get(1).queens));
//         }
//         return children;
//     }
// }
