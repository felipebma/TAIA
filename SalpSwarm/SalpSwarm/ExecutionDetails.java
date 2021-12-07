package SalpSwarm;

import java.util.List;
import java.util.Scanner;

public abstract class ExecutionDetails {

    protected Scanner in;
    int inputSize, hiddenSize, outputSize;
    List<Data> data;

    abstract List<Data> getData();
}
