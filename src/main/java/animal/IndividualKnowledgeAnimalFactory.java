package animal;

import map.WorldMap;
import vector2d.Vector2d;

public class IndividualKnowledgeAnimalFactory implements AnimalFactory {
    @Override
    public Predator createPredator(WorldMap worldMap, Vector2d position) {
        return new Predator(worldMap, position);
    }

    @Override
    public Prey createPrey(WorldMap worldMap, Vector2d position) {
        return new Prey(worldMap, position);
    }
}
