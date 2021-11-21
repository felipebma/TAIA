package Ackley;

import java.util.Random;

class AckleyUtils {

    public static Double randomDoubleInRange(Double rangeMin, Double rangeMax) {
        Random r = new Random();
        return rangeMin + (rangeMax - rangeMin) * r.nextDouble();
    }

    public static Double calculateAckleyFunction(Double[] arr) {

        Double n = (double) arr.length; // must be 30
        Double c1 = 20.0, c2 = 0.2, c3 = 2.0 * Math.PI;

        return -c1 * Math.exp(-c2 * Math.sqrt(squareSum(arr) / n))
                - Math.exp(cosineSum(arr, c3) / n) + c1 + 1;
    }

    private static Double squareSum(Double[] arr) {
        Double res = 0.0;
        for (int i = 0; i < arr.length; i++)
            res += arr[i] * arr[i];
        return res;
    }

    private static Double cosineSum(Double[] arr, Double c3) {
        Double res = 0.0;
        for (int i = 0; i < arr.length; i++)
            res += Math.cos(c3 * arr[i]);
        return res;
    }
}
