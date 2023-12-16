package qlearning;

public class RewardUtil {

    public static Double calculateReward(AnimalState state) {
        return state.isPredator() ? calculateRewardForPredator(state) : calculateRewardForPrey(state);
    }

    private static Double calculateRewardForPredator(AnimalState state) {
        return 1.0;
    }

    private static Double calculateRewardForPrey(AnimalState state) {
        return 1.0;
    }
}
