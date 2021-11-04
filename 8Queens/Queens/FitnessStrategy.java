package Queens;

public interface FitnessStrategy {
    public int calculateFitness(String queens);

    public int maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(String queens) {
            int fitness = 8;
            for (int i = 0; i < 8; i++) {
                int count = 0;
                for (int j = 0; j < 8; j++) {
                    if (Math.abs(getPos(queens, i) - getPos(queens, j)) == Math.abs(i - j)) {
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

        private int getPos(String queens, int pos) {
            return Integer.parseInt(queens.substring(pos * 3, pos * 3 + 3), 2);
        }
    };

    static FitnessStrategy alternativeStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(String queens) {
            int fitness = 28;
            for (int i = 0; i < 8; i++) {
                for (int j = i + 1; j < 8; j++) {
                    if (Math.abs(getPos(queens, i) - getPos(queens, j)) == Math.abs(i - j)) {
                        fitness--;
                    }
                }
            }
            return fitness;
        }

        @Override
        public int maxFitness() {
            return 28;
        }

        private int getPos(String queens, int pos) {
            return Integer.parseInt(queens.substring(pos * 3, pos * 3 + 3), 2);
        }
    };
}
