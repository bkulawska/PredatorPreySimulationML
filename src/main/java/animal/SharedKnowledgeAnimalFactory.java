package animal;

import map.WorldMap;
import qlearning.KnowledgeBase;
import vector2d.Vector2d;

public class SharedKnowledgeAnimalFactory implements AnimalFactory {
    private KnowledgeBase predatorKnowledge;
    private KnowledgeBase preyKnowledge;

    public SharedKnowledgeAnimalFactory() {
        predatorKnowledge = new KnowledgeBase();
        preyKnowledge = new KnowledgeBase();
    }

    @Override
    public Predator createPredator(WorldMap worldMap, Vector2d position) {
        return new Predator(worldMap, position, predatorKnowledge);
    }

    @Override
    public Prey createPrey(WorldMap worldMap, Vector2d position) {
        return new Prey(worldMap, position, preyKnowledge);
    }

    public SharedKnowledgeAnimalFactory withPredatorKnowledge(KnowledgeBase predatorKnowledge) {
        this.predatorKnowledge = predatorKnowledge;
        return this;
    }

    public SharedKnowledgeAnimalFactory withPreyKnowledge(KnowledgeBase preyKnowledge) {
        this.preyKnowledge = preyKnowledge;
        return this;
    }

    public KnowledgeBase getPredatorKnowledge() {
        return predatorKnowledge;
    }

    public KnowledgeBase getPreyKnowledge() {
        return preyKnowledge;
    }
}
