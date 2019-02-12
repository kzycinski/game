package com.game.engine;

/**
 * @author Krystian Życiński
 */
public class SecondChanceBox extends Box {

    public SecondChanceBox() {
        super();
    }

    public SecondChanceBox(GameEngine game) {
        super(game);
    }

    @Override
    public void action() {
        this.game.addSecondChance();
    }

    @Override
    public SecondChanceBox clone() {
        return new SecondChanceBox(game);
    }
}
