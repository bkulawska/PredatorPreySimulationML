package qlearning;

import java.util.HashMap;
import java.util.Map;

public class KnowledgeBase {

    private final Map<StateActionPair, Double> knowledge;
    private double learningRate;
    private double experimentRate;
    private double discountFactor;

    public KnowledgeBase() {
        this.knowledge = new HashMap<>();
        // TODO: implement changing parameters during training
        this.learningRate = 0.5;
        this.experimentRate = 0.5;
        this.discountFactor = 1;
    }

    public double getExperimentRate() {
        return experimentRate;
    }

    public Action getBestAction(AnimalState state) {
        Action bestAction = Action.getRandomAction();
        double bestScore = Double.MIN_VALUE;
        for (Action action : Action.values()) {
            Double score = knowledge.get(new StateActionPair(state, action));
            if (score != null && score > bestScore) {
                bestAction = action;
                bestScore = score;
            }
        }
        return bestAction;
    }

    private Double getBestScore(AnimalState state) {
        double bestScore = Double.MIN_VALUE;
        for (Action action : Action.values()) {
            Double score = knowledge.get(new StateActionPair(state, action));
            if (score != null && score > bestScore) {
                bestScore = score;
            }
        }
        return bestScore == Double.MIN_VALUE ? 0.0 : bestScore;
    }

    public void updateKnowledge(AnimalState previousState, AnimalState currentState, Action action) {
        Double value = knowledge.get(new StateActionPair(currentState, action));
        double score = value == null ? 0.0 : value;
        Double newScore = (1 - learningRate) * score +
                learningRate * (RewardUtil.calculateReward(previousState) + discountFactor * getBestScore(currentState));
        knowledge.put(new StateActionPair(previousState, action), newScore);
    }
}
