package qlearning;

import map.Environment;
import vector2d.Vector2d;

import java.util.List;

public class AnimalState {

    private final boolean isPredator;
    private final List<Vector2d> predators;
    private final List<Vector2d> preys;
    private final List<Vector2d> grass;

    public AnimalState(Environment environment) {
        this.isPredator = environment.isPredator();
        this.predators = environment.getPredators();
        this.preys = environment.getPreys();
        this.grass = environment.getGrass();
    }

    public List<Vector2d> getPredators() {
        return predators;
    }

    public List<Vector2d> getPreys() {
        return preys;
    }

    public List<Vector2d> getGrass() {
        return grass;
    }

    public boolean isPredator() {
        return isPredator;
    }

}
