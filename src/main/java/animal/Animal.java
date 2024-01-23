package animal;

import map.Environment;
import observer.IPositionChangeObserver;
import observer.IPositionChangePublisher;
import vector2d.Vector2d;

import java.util.List;

public interface Animal extends IPositionChangePublisher {

    void setAncestryFactor(int factor);

    int getAncestryFactor();

    boolean isDead();

    boolean canMate();

    void takeEnergy(double energy, int day);

    void addChild();

    double getEnergy();

    int getDayOfBirth();

    int getNumberOfChildren();

    int getDayOfDeath();

    double getSpeed();

    AnimalDNA getDNA();

    Vector2d getPosition();

    void move(Environment environment);

    List<Vector2d> getPurview();

    void addObserver(IPositionChangeObserver observer);

    void removeObserver(IPositionChangeObserver observer);

    @Override
    boolean equals(Object o);

    @Override
    int hashCode();

    void updateKnowledge(Environment environment);

    void resetDayReport();
}
