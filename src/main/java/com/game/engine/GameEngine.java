package com.game.engine;

import java.util.List;

/**
 * @author Krystian Życiński
 * 
 * GameEngine class contains logic of game.
 *
 */
public class GameEngine {
    private List<Box> gameBoxList;
    private List<Box> gameOverBoxList;
    private boolean secondChance;
    private int reward;
    private boolean gameOver;
    private boolean gameOverOver;            //there is no better name for this variable
    private boolean finished;
    
    public GameEngine() {
        this.gameBoxList = BoxProvider.getGameBoxList(this);
        this.gameOverBoxList = BoxProvider.getGameOverBoxList(this);
        secondChance = false;
        gameOver = false;
        gameOverOver = false;
        finished = false;
        reward = 0;
    }

    /**
     * @param box box that was selected by player
     * @return list of boxes to show to player
     */
    public List<Box> action(Box box) {
        if(finished) {
            return null;
        }
        if(box.isTaken()) {
            throw new IllegalStateException("Selected box was already used");
        }
        box.action();
        box.setTaken();
        if(!gameOver) {
            return gameBoxList;
        } else {
            return getGameOverList();
        }
    }

    private List<Box> getGameOverList() {
        if (secondChance) {
            secondChance = false;
            gameOver = false;
            gameOverOver = false;
            return gameBoxList;
        }
        if(!gameOverOver) {
            gameOverOver = true;
            return gameOverBoxList;
        } else {
            finished = true;
            return null;
        }
    }

    public void addReward(int reward) {
        this.reward += reward;
    }
    
    public void addSecondChance() {
        this.secondChance = true;
    }
    
    public void gameOverAction() {
        if(secondChance) {
            this.secondChance = false;
        } else {
            this.gameOver = true;
        }
    }
    
    public void extraGame() {
        gameBoxList.forEach(Box::reset);
        gameOver = false;
        gameOverOver = false;
    }
    
    public int getReward() {
        return this.reward;
    }
    
    public List<Box> getGameBoxList() {
        
        return this.gameBoxList;
    }
}
