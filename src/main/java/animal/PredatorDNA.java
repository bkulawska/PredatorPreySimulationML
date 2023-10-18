package animal;

import java.util.List;

public class PredatorDNA extends AnimalDNAImpl{
    static double energyMultiplier1;
    static double energyMultiplier2;
    static double energyMultiplier3;
    static double speedMultiplier1;
    static double speedMultiplier2;
    static double speedMultiplier3;
    static double mutationChance;
    private Double speed = null;
    private Double energyConsumption = null;

    public PredatorDNA(Predator parent1, Predator parent2){
        super(parent1.getDNA().getDNAToMultiply(), parent2.getDNA().getDNAToMultiply());
    }

    public PredatorDNA() {
        super();
    }

    @Override
    public List<Integer> getDNAToMultiply() {
        return super.getDNAToMultiply(mutationChance);
    }

    @Override
    public double getSpeed() {
        if(speed == null) {
            speed = super.calculateValue(speedMultiplier1, speedMultiplier2, speedMultiplier3)/8;
        }
        return speed;
    }

    @Override
    public double getEnergyConsumption() {
        if(energyConsumption == null) {
            energyConsumption = super.calculateValue(energyMultiplier1, energyMultiplier2, energyMultiplier3)/4.5;
        }
        return energyConsumption;
    }

    public static void setSpeedMultipliers(double multiplier1, double multiplier2, double multiplier3) {
        speedMultiplier1 = multiplier1;
        speedMultiplier2 = multiplier2;
        speedMultiplier3 = multiplier3;
    }

    public static void setEnergyMultipliers(double multiplier1, double multiplier2, double multiplier3) {
        energyMultiplier1 = multiplier1;
        energyMultiplier2 = multiplier2;
        energyMultiplier3 = multiplier3;
    }

    public static void setMutationChance(double mutation) {
        mutationChance = mutation;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
