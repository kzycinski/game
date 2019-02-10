package com.game;

import com.game.engine.Box;
import com.game.engine.BoxProvider;
import com.game.engine.GameOverBox;
import com.game.engine.MoneyBox;
import com.game.engine.SecondChanceBox;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Krystian Życiński
 */
public class MainUtils {
    private static final double SIMULATIONS = 10000000;
    private static final List<Box> gameBoxList = BoxProvider.getGameBoxList();
    private static final List<Box> gameOverBoxList = BoxProvider.getGameOverBoxList();
    private static final List<Box> gameOverOverBoxList = BoxProvider.getGameOverOverBoxList();

    public static double simulate() {
        long reward = 0;
        for (int i = 0; i < SIMULATIONS; i++) {
            reward += GameSimulator.simulateGame();
        }
        return Math.round(reward / SIMULATIONS);
    }

    public static double calculate() {
        double result = 0;
        double prob = 1 / (double) gameBoxList.size();
        for (Box box : gameBoxList) {
            result += prob * calculate(box, new ArrayList<>(gameBoxList), 0, false, false);
        }
        return result;
    }
/**
 *  Zadanie nie jest dobrze zrobione, wylicza przewidywaną nagrodę w przypadku, gdy po wylosowaniu GameOver 
 *  i następnie ExtraLife nie tasuję kart tylko kontynuuję grę.
 *  Zmiana żeby spełniało wymaganie jest prosta, lecz niestety wtedy to rozwiązanie ma zbyt dużą złożoność.
 */
    private static double calculate(Box currentBox, List<Box> boxList, int reward, boolean secondLife,
                                    boolean gameOver) {
        boxList.remove(currentBox);
        double[] finalReward = {0};
        double probability = 1 / (double) boxList.size();
        if (currentBox instanceof GameOverBox) {
            if (secondLife) {
                boxList.forEach(e ->
                        finalReward[0] += probability * calculate(e, new ArrayList<>(boxList), 0, false, gameOver)
                );
            } else {
                finalReward[0] += calculateGameOver(boxList, reward, gameOver);
            }

        } else if (currentBox instanceof SecondChanceBox) {
            boxList.forEach(e ->
                    finalReward[0] += probability * calculate(e, new ArrayList<>(boxList), 0, true, gameOver)
            );
        } else if (currentBox instanceof MoneyBox) {
            int thisValue = ((MoneyBox) currentBox).getValue();
            boxList.forEach(e -> 
                finalReward[0] += probability *
                        calculate(e, new ArrayList<>(boxList), 0, false, gameOver)
            );
            return thisValue + finalReward[0];
        }
        return finalReward[0];
    }

    private static double calculateGameOver(List<Box> boxList, int reward, boolean gameOver) {
        double[] finalReward = {0};
        double probability;
        if (gameOver) {
            probability = 1 / (double) gameOverOverBoxList.size();
            gameOverOverBoxList.forEach(e -> finalReward[0] +=
                    probability * (((MoneyBox) e).getValue() + reward));
        } else {
            probability = 1 / (double) gameOverBoxList.size();
            gameOverBoxList.forEach(e -> {
                if (e instanceof MoneyBox) {
                    finalReward[0] += probability * (((MoneyBox) e).getValue() + reward);
                }
            });
            boxList.forEach(e ->
                    finalReward[0] += probability * (1 / (double) boxList.size()) *
                            calculate(e, new ArrayList<>(boxList), reward, false, true)
            );
        }
        return finalReward[0];
    }
}
