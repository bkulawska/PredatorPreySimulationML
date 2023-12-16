package qlearning;

import animal.Animal;
import map.Environment;
import vector2d.Vector2d;

import java.util.Set;

public class AnimalState {

    private final boolean isPredator;
    private final Set<Vector2d> predators;
    private final Set<Vector2d> preys;
    private final Set<Vector2d> grass;

    public AnimalState(Environment environment) {
        this.isPredator = environment.isPredator();
        this.predators = environment.getPredators();
        this.preys = environment.getPreys();
        this.grass = environment.getGrass();
    }

    public Set<Vector2d> getPredators() {
        return predators;
    }

    public Set<Vector2d> getPreys() {
        return preys;
    }

    public Set<Vector2d> getGrass() {
        return grass;
    }

    public boolean isPredator() {
        return isPredator;
    }

    @Override
    public boolean equals(Object other){
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        AnimalState otherAnimalState = (AnimalState) other;
        return this.getGrass().equals(otherAnimalState.getGrass())
                && this.getPredators().equals(otherAnimalState.getPredators())
                && this.getPreys().equals(otherAnimalState.getPreys())
                && this.isPredator() == otherAnimalState.isPredator();
    }
}
