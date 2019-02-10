package com.game.engine;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Krystian Życiński
 */
public class GameEngineTest {

    private GameEngine gameEngine;
    private MoneyBox moneyBox1, moneyBox2, moneyBox3, moneyBox4;
    private GameOverBox gameOverBox1, gameOverBox2;
    private SecondChanceBox secondChanceBox1, secondChanceBox2;

    @Before
    public void init() {
        gameEngine = new GameEngine();
        moneyBox1 = new MoneyBox(gameEngine, 20);
        moneyBox2 = new MoneyBox(gameEngine, 20);
        moneyBox3 = new MoneyBox(gameEngine, 20);
        moneyBox4 = new MoneyBox(gameEngine, 20);
        gameOverBox1 = new GameOverBox(gameEngine);
        gameOverBox2 = new GameOverBox(gameEngine);
        secondChanceBox1 = new SecondChanceBox(gameEngine);
        secondChanceBox2 = new SecondChanceBox(gameEngine);
    }

    @Test
    public void testGetGameList() {
        Assert.assertEquals(12, gameEngine.getGameBoxList().size());
    }

    @Test
    public void testActionWithMoneyBox() {
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);
        Assert.assertEquals(40, gameEngine.getReward());
    }

    @Test
    public void testActionWithGameOver() {
        gameEngine.action(gameOverBox1);
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);    //shouldn't count that
        Assert.assertEquals(20, gameEngine.getReward());
    }

    @Test
    public void testActionWithSecondChance() {
        gameEngine.action(secondChanceBox1);
        gameEngine.action(gameOverBox1);
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);
        gameEngine.action(gameOverBox2);
        gameEngine.action(moneyBox3);
        gameEngine.action(moneyBox4);    //shouldn't count that
        Assert.assertEquals(60, gameEngine.getReward());
    }

    @Test
    public void testActionWithExtraLife() {
        gameEngine.action(gameOverBox1);
        gameEngine.action(secondChanceBox1);
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);
        gameEngine.action(gameOverBox2);
        gameEngine.action(moneyBox3);
        gameEngine.action(moneyBox4);
        Assert.assertEquals(60, gameEngine.getReward());
    }

    @Test(expected = IllegalStateException.class)
    public void testActionWithSelectTheSameBoxTwice() {
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox1);
    }

    @Test(expected = IllegalStateException.class)
    public void testActionWithExtraLifeTwice() {
        gameEngine.action(gameOverBox1);
        gameEngine.action(secondChanceBox1);
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);
        gameEngine.action(gameOverBox1);
        gameEngine.action(secondChanceBox1);
    }

    @Test
    public void testActionWithGameOverThenTwoSecondChances() {
        gameEngine.action(gameOverBox1);
        gameEngine.action(secondChanceBox1);
        gameEngine.action(moneyBox1);
        gameEngine.action(moneyBox2);
        gameEngine.action(secondChanceBox2);
        gameEngine.action(gameOverBox2);
        gameEngine.action(moneyBox3);
        gameEngine.action(moneyBox4);
        Assert.assertEquals(80, gameEngine.getReward());
    }

    @Test
    public void testGetGameOverList() {
        Assert.assertEquals(4, gameEngine.action(gameOverBox1).size());
    }
}
