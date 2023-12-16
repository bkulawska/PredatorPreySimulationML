package qlearning;


public class RewardUtil {

    public static Long calculateReward(AnimalState state) {
        return state.isPredator() ? calculateRewardForPredator(state) : calculateRewardForPreys(state);
    }

    private static Long calculateRewardForPredator(AnimalState state) {
        return 1L;
    }

    private static Long calculateRewardForPreys(AnimalState state) {
        return 1L;
    }

}
