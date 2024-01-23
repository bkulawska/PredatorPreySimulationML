package qlearning;

import animal.DayReport;

public class RewardUtil {

    public static Double calculateReward(AnimalState state, DayReport dayReport) {
        var reward = 1.0;
        if (dayReport.didEat()) {
            reward += 50.0;
        }
        if (dayReport.didMate()) {
            reward += 30.0;
        }
        return reward;
    }
}
