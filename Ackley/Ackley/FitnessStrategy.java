package Ackley;

public interface FitnessStrategy {
    public double calculateFitness(double[] arr);

    public double maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public double calculateFitness(double[] arr) {
            double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            // This will never be 0, and will be 1 when ackleyValue == 0
            return Math.exp(-ackleyValue);
        }

        @Override
        public double maxFitness() {
            return 1.0;
        }

    };

    static FitnessStrategy alternativeStrategy = new FitnessStrategy() {
        @Override
        public double calculateFitness(double[] arr) {
            double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            return -ackleyValue; // TODO: se precisar
        }

        @Override
        public double maxFitness() {
            return 10000;
        }
    };
}
