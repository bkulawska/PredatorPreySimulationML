import animal.AnimalFactory;
import animal.SharedKnowledgeAnimalFactory;
import simulation.Engine;
import statistics.TrainingStatistics;
import utilities.FileParser;

public class Train {
    public static void main(String[] args) {
        try {
            var fileParser = new FileParser();
            var trainingStatistics = new TrainingStatistics();
            train(10000, fileParser, trainingStatistics);
            trainingStatistics.saveAsJson("training/training1.json");
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration", e);
        }
    }

    public static void train(int epochs, FileParser fileParser, TrainingStatistics trainingStatistics) {
        var animalFactory = new SharedKnowledgeAnimalFactory();
        for (int i = 0; i < epochs; i++) {
            var daysSurvived = epoch(fileParser, animalFactory);
            System.out.printf("Epoch: %s, days survived: %s%n", i, daysSurvived);
            trainingStatistics.saveEpoch(daysSurvived);
        }
    }

    public static int epoch(FileParser fileParser, AnimalFactory animalFactory) {
        var engine = new Engine(fileParser, animalFactory);
        var i = 0;
        while (engine.getAlivePredatorsCount() > 0 && engine.getAlivePreysCount() > 0) {
            System.out.printf("Predators: %s, Preys: %s%n", engine.getAlivePredatorsCount(), engine.getAlivePreysCount());
            engine.nextDay();
            i++;
        }
        return i;
    }
}
