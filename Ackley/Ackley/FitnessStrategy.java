package Ackley;

public interface FitnessStrategy {
    public Double calculateFitness(Double[] arr);

    public Double maxFitness();

    static FitnessStrategy normalStrategy = new FitnessStrategy() {
        @Override
        public Double calculateFitness(Double[] arr) {
            Double ackleyValue = AckleyUtils.calculateAckleyFunction(arr);
            // System.out.println("ackley = " + ackleyValue);
            // System.out.println("fitness = " + Math.exp(-ackleyValue));
            // This will never be 0, and will be 1 when ackleyValue == 0
            return Math.exp(-ackleyValue);
        }

        @Override
        public Double maxFitness() {
            return 1.0;
        }

    };
}
