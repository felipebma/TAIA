package SalpSwarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Cancer extends ExecutionDetails {

    public Cancer() {
        try {
            in = new Scanner(new File("../data/cancer.data"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.inputSize = 9;
        this.hiddenSize = 8;
        this.outputSize = 2;
    }

    public List<Data> getData() {
        List<Data> data = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(",");
            List<Double> input = new ArrayList<>();
            List<Double> output = new ArrayList<>(Arrays.asList(0.0, 0.0));
            for (int i = 1; i < 10; i++) {
                input.add(line[i].equals("?") ? 0 : Double.parseDouble(line[i]));
            }
            output.set((Integer.parseInt(line[10]) - 2) / 2, 1.0);
            data.add(new Data(input, output));
        }
        return data;
    }
}
