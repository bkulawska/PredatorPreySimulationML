package animal;

import map.Environment;
import map.WorldMap;
import qlearning.AnimalState;
import vector2d.Vector2d;

import java.util.Collections;

public class Predator extends AnimalImpl{
    private static double initialEnergy;
    private static double matingEnergy;

    public Predator(Predator parent1, Predator parent2, int dayOfBirth, Vector2d position){
        super(parent1, parent2, dayOfBirth, position);
        DNA = new PredatorDNA(parent1, parent2);
        this.previousState = new AnimalState(new Environment(Collections.emptyList(), this, true));
    }

    public Predator(WorldMap map, Vector2d position) {
        super(map, position);
        energy = initialEnergy;
        DNA = new PredatorDNA();
    }

    @Override
    public boolean canMate() {
        return energy > matingEnergy;
    }

    @Override
    public PredatorDNA getDNA() {
        return (PredatorDNA) DNA;
    }

    public static void setInitialEnergy(double energy) {
        initialEnergy = energy;
    }

    public static void setMinimalMatingEnergy(double energy) {
        matingEnergy = energy;
    }

    public void eatPrey(double energy) {
        this.energy += energy;
    }
}
