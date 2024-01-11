import animal.AnimalFactory;
import animal.SharedKnowledgeAnimalFactory;
import simulation.Engine;
import utilities.FileParser;

public class Train {
    public static void main(String[] args) {
        try {
            var fileParser = new FileParser();
            train(100000, fileParser);
        } catch (Exception e) {
            throw new RuntimeException("Error while loading configuration", e);
        }
    }

    public static void train(int epochs, FileParser fileParser) {
        var animalFactory = new SharedKnowledgeAnimalFactory();
        for (int i = 0; i < epochs; i++) {
            var daysSurvived = epoch(fileParser, animalFactory);
            System.out.println(String.format("Epoch: %s, days survived: %s", i, daysSurvived));
        }
    }

    public static int epoch(FileParser fileParser, AnimalFactory animalFactory) {
        var engine = new Engine(fileParser, animalFactory);
        var i = 0;
        while (engine.areBothSpeciesAlive()) {
            engine.nextDay();
            i++;
        }
        return i;
    }
}
