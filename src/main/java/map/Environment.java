package map;

import vector2d.Vector2d;

import java.util.Collection;
import java.util.List;

public class Environment {
    private final Collection<Field> environment;

    public Environment(Collection<Field> environment) {
        this.environment = environment;
    }

    public List<Vector2d> getPreys() {
        return environment.stream()
                .filter(Field::containsPreys)
                .map(Field::getPosition)
                .toList();
    }

    public List<Vector2d> getPredators() {
        return environment.stream()
                .filter(Field::containsPredators)
                .map(Field::getPosition)
                .toList();
    }

    public List<Vector2d> getGrass() {
        return environment.stream()
                .filter(Field::getContainsGrass)
                .map(Field::getPosition)
                .toList();
    }
}
