package com.game;

import com.game.engine.Box;
import com.game.engine.GameEngine;
import java.util.List;

/**
 * @author Krystian Życiński
 */
public class GameSimulator {

    public static int simulateGame(){
        GameEngine gameEngine = new GameEngine();
        List<Box> boxList = gameEngine.getGameBoxList();
        int counter = 0;
        int gameOverCounter = 0;
        while(boxList != null) {
            if(boxList.size() == 12) {
                boxList = gameEngine.action(boxList.get(counter));
                counter++;
            } else {
                boxList = gameEngine.action(boxList.get(gameOverCounter));
                gameOverCounter++;
            }
        }
        return gameEngine.getReward();
    }
}
