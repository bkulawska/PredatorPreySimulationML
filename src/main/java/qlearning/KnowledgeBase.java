package qlearning;

import javafx.util.Pair;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeBase {

    private final Map<Pair<AnimalState, Action>, Double> knowledge; // todo equals for key
    private double learningRate; // todo set values
    private double experimentRate;
    private double discountFactor;

    public KnowledgeBase() {
        this.knowledge = new HashMap<>();
    }

    public double getExperimentRate() {
        return experimentRate;
    }

    public Action getBestAction(AnimalState state) {
        Action bestAction = Action.getRandomAction();
        Double bestScore = Double.MIN_VALUE;
        for (Action action : Action.values()) {
            Double score  = knowledge.get(new Pair<>(state, action));
            if (score != null && score > bestScore) {
                bestAction = action;
                bestScore = score;
            }
        }
        return bestAction;
    }

    public Double getBestScore(AnimalState state) {
        Double bestScore = Double.MIN_VALUE;
        for (Action action : Action.values()) {
            Double score  = knowledge.get(new Pair<>(state, action));
            if (score != null && score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore == Double.MIN_VALUE ? 0 : bestScore;
    }

    public void updateKnowledge(AnimalState previousState, AnimalState currentState, Action action) {
        Double score = ( 1 - learningRate ) * knowledge.get(new Pair<>(currentState, action))
                + learningRate * ( RewardUtil.calculateReward(previousState) + discountFactor * getBestScore(currentState));
        knowledge.put(new Pair<>(previousState, action), score);
    }

}
