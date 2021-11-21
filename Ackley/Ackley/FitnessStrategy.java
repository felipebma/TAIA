package Ackley;

public interface FitnessStrategy {
    public Double calculateFitness(Double[] arr);

    public Double maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public Double calculateFitness(Double[] arr) {
            Double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            // This will never be 0, and will be 1 when ackleyValue == 0
        System.out.println("f(xis) = " + ackleyValue);
        System.out.println("fitness = " + Math.exp(-ackleyValue));
        return Math.exp(-ackleyValue);
        }

        @Override
        public Double maxFitness() {
            return 1.0;
        }

    };

    static FitnessStrategy alternativeStrategy = new FitnessStrategy() {
        @Override
        public Double calculateFitness(Double[] arr) {
            Double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            return -ackleyValue; // TODO: se precisar
        }

        @Override
        public Double maxFitness() {
            return 10000.0;
        }
    };
}
