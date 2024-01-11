package animal;

import map.WorldMap;
import vector2d.Vector2d;

public interface AnimalFactory {
    Predator createPredator(WorldMap worldMap, Vector2d position);
    Prey createPrey(WorldMap worldMap, Vector2d position);
}
