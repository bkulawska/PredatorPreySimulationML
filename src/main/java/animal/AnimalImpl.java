package animal;

import map.Environment;
import map.WorldMap;
import observer.IPositionChangeObserver;
import qlearning.Action;
import qlearning.AnimalState;
import qlearning.KnowledgeBase;
import vector2d.Vector2d;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public abstract class AnimalImpl implements Animal{
    AnimalDNA DNA;
    private final WorldMap map;
    private Vector2d position;
    double energy;
    private final int dayOfBirth;
    private int numberOfChildren = 0;
    int dayOfDeath;
    boolean isAlive = true;
    private final LinkedList<IPositionChangeObserver> subscribers = new LinkedList<>();
    private Integer ancestryFactor = -1;
    KnowledgeBase knowledgeBase;
    AnimalState previousState;
    Action previousAction;

    public AnimalImpl(AnimalImpl parent1, AnimalImpl parent2, int dayOfBirth, Vector2d position) {
        this.ancestryFactor = Math.max(parent1.getAncestryFactor(), parent2.getAncestryFactor());
        parent1.addChild();
        parent2.addChild();
        this.dayOfBirth = dayOfBirth;
        this.map = parent1.map;
        this.position = position;
        this.map.place(this);
        this.energy = parent1.giveEnergyToAChild() + parent2.giveEnergyToAChild();
        this.knowledgeBase = new KnowledgeBase();
    }

    public AnimalImpl(WorldMap map, Vector2d position) {
        this.dayOfBirth = 0;
        this.map = map;
        this.position = position;
        this.map.place(this);
        this.knowledgeBase = new KnowledgeBase();
    }

    @Override
    public void setAncestryFactor(int factor) {
        ancestryFactor = factor;
    }

    @Override
    public int getAncestryFactor() {
        return ancestryFactor;
    }

    @Override
    public boolean isDead() {
        return !isAlive;
    }

    @Override
    public void takeEnergy(double energy, int day) {
        this.energy -= energy * this.DNA.getEnergyConsumption();
        if(this.energy < 0){
            this.isAlive = false;
            this.dayOfDeath = day;
        }
    }

    @Override
    public void addChild() {
        numberOfChildren += 1;
    }

    @Override
    public double getEnergy() {
        return energy;
    }

    @Override
    public int getDayOfBirth() {
        return dayOfBirth;
    }

    @Override
    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    @Override
    public int getDayOfDeath() {
        return dayOfDeath;
    }

    @Override
    public AnimalDNA getDNA() {
        return DNA;
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    @Override
    public void move(Environment environment) {
        Vector2d positionTmp = map.adjustPosition(selectMove(environment));
        positionChanged(position, positionTmp);
        position = positionTmp;
    }

    @Override
    public double getSpeed() {
        return this.DNA.getSpeed();
    }

    @Override
    public void addObserver(IPositionChangeObserver observer) {
        subscribers.add(observer);
    }

    @Override
    public void removeObserver(IPositionChangeObserver observer) {
        subscribers.remove(observer);
    }

    @Override
    public List<Vector2d> getPurview() {
        return position.getMooreNeighborhood().stream()
                .map(map::adjustPosition)
                .distinct()
                .toList();
    }

    protected Vector2d selectMove(Environment environment) {
        this.previousState = new AnimalState(environment);
        Action action = shouldTryExperiment() ? Action.getRandomAction() : knowledgeBase.getBestAction(this.previousState);
        this.previousAction = action;
        return action.getNewPosition(getPosition());
    }

    @Override
    public void updateKnowledge(Environment environment) {
        AnimalState currentState = new AnimalState(environment);
        knowledgeBase.updateKnowledge(previousState, currentState, previousAction);
    }

    boolean shouldTryExperiment() {
        return knowledgeBase.getExperimentRate() < Math.random();
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        for(IPositionChangeObserver observer : subscribers)
        {
            observer.positionChanged(oldPosition, newPosition, this);
        }
    }

    private double giveEnergyToAChild() {
        double startEnergy = energy;
        energy = energy*0.75;
        return startEnergy - energy;
    }
}
