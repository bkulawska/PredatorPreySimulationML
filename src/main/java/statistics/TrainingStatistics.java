package statistics;

import org.json.simple.JSONValue;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TrainingStatistics {
    private ArrayList<Integer> stats = new ArrayList<>();

    public void saveEpoch(int daysSurvived) {
        this.stats.add(daysSurvived);
    }

    public void saveAsJson(String filePath) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(JSONValue.toJSONString(stats));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
