package com.game;

import com.game.engine.Box;
import com.game.engine.BoxProvider;
import com.game.engine.ExtraGameBox;
import com.game.engine.GameOverBox;
import com.game.engine.MoneyBox;
import com.game.engine.SecondChanceBox;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krystian Życiński
 * <p>
 * Calculation of expected reward:
 * 1. Calculate expected reward for case when after finding GameOver game ends.
 * 2. Calculate expected reward for GameOver case.
 * 3. Sum both results.
 * Results are a little bit different from real, because of accuracy loss in floating point calculations in Java, and
 * rounding used in simulations.
 */
public class Main {
    private static final int SIMULATIONS = 10000000;
    private static final List<Box> GAME_BOX_LIST = BoxProvider.getGameBoxList();
    private static final List<Box> GAME_OVER_BOX_LIST = BoxProvider.getGameOverBoxList();
    private static final List<Box> GAME_OVER_OVER_BOX_LIST = BoxProvider.getGameOverOverBoxList();
    
    public static void main(String[] args) {
        System.out.println("Result of 10 000 000 simulations: " + simulate());
        System.out.println("Result of calculations: " + calculateExpectedReward());
    }
    
    private static double simulate() {
        long reward = 0;
        for (int i = 0; i < SIMULATIONS; i++) {
            reward += GameSimulator.simulateGame();
        }
        return Math.round((double) reward / SIMULATIONS);
    }

    private static double calculateExpectedReward() {
        double result = 0;
        result += calculateMainGameExpectedReward();              //reward of main game
        result += calculateGameOverExpectedReward();              //reward after gameOver
        return result;
    }

    private static double calculateMainGameExpectedReward() {
        return calculateMainGameExpectedReward(new ArrayList<>(GAME_BOX_LIST), false);
    }

    private static double calculateGameOverExpectedReward() {
        return calculateGameOverExpectedReward(new ArrayList<>(GAME_OVER_BOX_LIST));
    }

    private static double calculateMainGameExpectedReward(List<Box> boxList, boolean secondChance) {
        double result = 0;
        double probability = 1 / (double) boxList.size();
        for (Box box : boxList) {
            result += probability * calculateMainGameExpectedRewardForBox(box, new ArrayList<>(boxList), secondChance);     //probability of selecting that path * expected reward
        }
        return result;
    }

    private static double calculateMainGameExpectedRewardForBox(Box currentBox, List<Box> boxList, boolean secondChance) {
        boxList.remove(currentBox);
        if (currentBox instanceof GameOverBox) {
            if (secondChance) {
                return calculateMainGameExpectedReward(boxList, false);
            } else {
                return 0;
            }
        } else if (currentBox instanceof SecondChanceBox) {
            return calculateMainGameExpectedReward(boxList, true);
        } else if (currentBox instanceof MoneyBox) {
            return ((MoneyBox) currentBox).getValue() + calculateMainGameExpectedReward(boxList, secondChance);
        }
        return 0;
    }

    private static double calculateGameOverExpectedReward(List<Box> boxList) {                //calculateMainGameExpectedRewardForBox reward for game over
        double[] reward = {0};
        double prob = 1 / (double) boxList.size();
        boxList.forEach(e -> reward[0] += prob * calculateGameOverExpectedRewardForBox(e));
        return reward[0];
    }

    private static double calculateGameOverExpectedRewardForBox(Box box) {   //calculateMainGameExpectedRewardForBox reward for single box in game over mode      
        if (box instanceof MoneyBox) {
            return ((MoneyBox) box).getValue();
        } else if (box instanceof ExtraGameBox) {       //extra game calculates reward for main game and adds game over with shortened box list
            return calculateMainGameExpectedReward(new ArrayList<>(GAME_BOX_LIST), false) +
                    calculateGameOverExpectedReward(new ArrayList<>(GAME_OVER_OVER_BOX_LIST));
        }
        return 0;
    }
}



