package Ackley;

class AckleyUtils {

    public static double calculateAckleyFunction(double [] arr){

        int n = arr.length; // must be 30
        int c1 = 20;
        double c2 = 0.2, c3 = 2 * Math.PI;


        return -c1 * Math.exp(-c2*Math.sqrt((double)squareSum(arr)/(double)n))
               -Math.exp(cosineSum(arr, c3) / (double)n) + c1 + 1;
    }

    private static double squareSum(double [] arr){
        double res = 0;
        for(int i = 0; i < arr.length; i++)
            res += arr[i]*arr[i];
        return res;
    }

    private static double cosineSum(double [] arr, double c3){
        double res = 0;
        for(int i = 0; i < arr.length; i++)
            res += c3*arr[i];
        return res;
    }
}
