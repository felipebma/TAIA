package Ackley;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public interface SelectionStrategy {
    public List<Chromosome> getParents(List<Chromosome> chromosomes);

    static SelectionStrategy get2OutOf5Random = new SelectionStrategy() {

        @Override
        public List<Chromosome> getParents(List<Chromosome> chromosomes) {
            Random rnd = new Random();
            Collections.shuffle(chromosomes, rnd);
            List<Chromosome> randomFive = chromosomes.subList(0, 5);
            Collections.sort(randomFive);
            return randomFive.subList(0, 2);
        }
    };

    static SelectionStrategy wheightedRandom = new SelectionStrategy() {

        @Override
        public List<Chromosome> getParents(List<Chromosome> chromosomes) {
            List<Double> cumulatedFitness = new ArrayList<>();
            Double fitnessSum = chromosomes.get(0).getFitness();
            cumulatedFitness.add(fitnessSum);
            for (int i = 1; i < chromosomes.size(); i++) {
                cumulatedFitness.add(chromosomes.get(i).getFitness() + cumulatedFitness.get(i - 1));
                fitnessSum += chromosomes.get(i).getFitness();
            }

            List<Chromosome> twoWheightedRandom = new ArrayList<>();

            // get first parent
            Double randomFitness = AckleyUtils.randomDoubleInRange(0.0, fitnessSum);
            int pos = getLowerBound(cumulatedFitness, randomFitness);
            twoWheightedRandom.add(chromosomes.get(pos));

            // get second parent (must be different from first)
            int pos2 = pos;
            while (pos2 == pos) {
                randomFitness = AckleyUtils.randomDoubleInRange(0.0, fitnessSum);
                pos2 = getLowerBound(cumulatedFitness, randomFitness);
            }
            twoWheightedRandom.add(chromosomes.get(pos));

            return twoWheightedRandom;
        }

        private int getLowerBound(List<Double> cumulatedFitness, Double randomFitness) {
            int l = 0, r = cumulatedFitness.size() - 1;
            while (l < r) {
                int m = l + (r - l) / 2;
                if (cumulatedFitness.get(m) >= randomFitness) {
                    r = m;
                } else {
                    l = m + 1;
                }
            }
            return r;
        }
    };
}
