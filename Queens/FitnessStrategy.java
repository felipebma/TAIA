package Queens;

public interface FitnessStrategy {
    public int calculateFitness(int[] queens);

    public int maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(int[] queens) {
            int fitness = 8;
            for (int i = 0; i < 8; i++) {
                int count = 0;
                for (int j = 0; j < 8; j++) {
                    if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) {
                        count++;
                    }
                }
                if (count > 1) {
                    fitness--;
                }
            }
            return fitness;
        }

        @Override
        public int maxFitness() {
            return 8;
        }
    };

    static FitnessStrategy alternativeStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(int[] queens) {
            int fitness = 28;
            for (int i = 0; i < 8; i++) {
                int count = 0;
                for (int j = i + 1; j < 8; j++) {
                    if (Math.abs(queens[i] - queens[j]) == Math.abs(i - j)) {
                        count++;
                    }
                }
                fitness -= count;
            }
            return fitness;
        }

        @Override
        public int maxFitness() {
            return 28;
        }
    };
}
