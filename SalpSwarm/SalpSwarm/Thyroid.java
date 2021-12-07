
package SalpSwarm;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Thyroid extends ExecutionDetails {

    public Thyroid() {
        try {
            in = new Scanner(new File("../data/thyroid.data"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.inputSize = 21;
        this.hiddenSize = 15;
        this.outputSize = 3;
    }

    public List<Data> getData() {
        if (this.data != null) {
            return this.data;
        }
        List<Data> data = new ArrayList<>();
        while (in.hasNextLine()) {
            String[] line = in.nextLine().split(" ");
            List<Double> input = new ArrayList<>();
            List<Double> output = new ArrayList<>(Arrays.asList(0.0, 0.0, 0.0));
            for (int i = 0; i < inputSize; i++) {
                input.add(Double.parseDouble(line[i]));
            }
            output.set(Integer.parseInt(line[21]) - 1, 1.0);
            data.add(new Data(input, output));
        }
        this.data = data;
        return data;
    }

	@Override
	public String toString() {
		return "Thyroid";
	}
}
