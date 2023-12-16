package animal;

import map.Environment;
import map.WorldMap;
import qlearning.AnimalState;
import vector2d.Vector2d;

import java.util.Collections;

public class Prey extends AnimalImpl{
    private static double initialEnergy;
    private static double matingEnergy;

    public Prey(Prey parent1, Prey parent2, int dayOfBirth, Vector2d position){
        super(parent1, parent2, dayOfBirth, position);
        DNA = new PreyDNA(parent1, parent2);
        this.previousState = new AnimalState(new Environment(Collections.emptyList(), this, false));
    }

    public Prey(WorldMap map, Vector2d position) {
        super(map, position);
        energy = initialEnergy;
        DNA = new PreyDNA();
    }

    @Override
    public boolean canMate() {
        return energy >= matingEnergy;
    }

    @Override
    public PreyDNA getDNA() {
        return (PreyDNA) DNA;
    }

    public void eatGrass(double energy) {
        this.energy += energy;
    }

    public static void setInitialEnergy(double energy) {
        initialEnergy = energy;
    }

    public static void setMinimalMatingEnergy(double energy) {
        matingEnergy = energy;
    }

    public double kill(int day) {
        this.isAlive = false;
        double energy = this.energy;
        this.energy = 0;
        this.dayOfDeath = day;
        return energy;
    }
}
