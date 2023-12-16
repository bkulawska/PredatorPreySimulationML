package qlearning;

public record StateActionPair(AnimalState animalState, Action action) {

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        StateActionPair otherPair = (StateActionPair) other;
        return this.animalState().equals(otherPair.animalState())
                && this.action() == otherPair.action();
    }
}
