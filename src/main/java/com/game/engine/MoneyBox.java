package com.game.engine;

/**
 * @author Krystian Życiński
 */
public class MoneyBox extends Box {
    private int value;

    public MoneyBox(int value) {
        this.value = value;
    }

    public MoneyBox(GameEngine game, int value) {
        super(game);
        this.value = value;
    }

    @Override
    public void action() {
        this.game.addReward(value);
    }

    public MoneyBox clone() {
        return new MoneyBox(game, value);
    }

    public int getValue() {
        return this.value;
    }
}
