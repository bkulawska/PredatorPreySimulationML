package map;

import animal.Animal;
import vector2d.Vector2d;

import java.util.Collection;
import java.util.List;

public class Environment {
    private final Collection<Field> environment;
    private Animal animal;
    private final boolean isPredator;

    public Environment(Collection<Field> environment, Animal animal, boolean isPredator) {
        this.environment = environment;
        this.animal = animal;
        this.isPredator = isPredator;
    }

    public List<Vector2d> getPreys() {
        return environment.stream()
                .filter(Field::containsPreys)
                .map(Field::getPosition)
                .map(this::mapVector)
                .toList();
    }

    public List<Vector2d> getPredators() {
        return environment.stream()
                .filter(Field::containsPredators)
                .map(Field::getPosition)
                .map(this::mapVector)
                .toList();
    }

    public List<Vector2d> getGrass() {
        return environment.stream()
                .filter(Field::getContainsGrass)
                .map(Field::getPosition)
                .map(this::mapVector)
                .toList();
    }

    public boolean isPredator() {
        return isPredator;
    }

    private Vector2d mapVector(Vector2d vector) {
        Vector2d animalPosition = animal.getPosition();
        return new Vector2d(animalPosition.x - vector.x, animalPosition.y - vector.y);
    }
}
