package qlearning;

import java.util.Objects;

public record StateActionPair(AnimalState animalState, Action action) {

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        StateActionPair otherPair = (StateActionPair) other;
        return this.animalState().equals(otherPair.animalState())
                && this.action() == otherPair.action();
    }

    @Override
    public int hashCode() {
        return Objects.hash(animalState.isPredator(), animalState.getPredators(), animalState.getPreys(),
                animalState().getGrass(), action);
    }
}
