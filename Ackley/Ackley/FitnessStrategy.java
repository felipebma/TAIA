package Ackley;

public interface FitnessStrategy {
    public int calculateFitness(double [] arr);

    public int maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(double [] arr) {
            double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            return 0; // TODO
        }

        @Override
        public int maxFitness() {
            return 10000;
        }

    };

    static FitnessStrategy alternativeStrategy = new FitnessStrategy() {
        @Override
        public int calculateFitness(double [] arr) {
            double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            return 0; // TODO
        }

        @Override
        public int maxFitness() {
            return 10000;
        }
    };
}
