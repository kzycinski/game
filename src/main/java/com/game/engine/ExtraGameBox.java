package com.game.engine;

/**
 * @author Krystian Życiński
 */
public class ExtraGameBox extends Box {

    public ExtraGameBox() {
        super();
    }

    public ExtraGameBox(GameEngine game) {
        super(game);
    }

    @Override
    public void action() {
        this.game.extraGame();
    }

    @Override
    public ExtraGameBox clone() {
        return new ExtraGameBox(game);
    }
}
