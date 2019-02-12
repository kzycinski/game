package com.game.engine;

/**
 * @author Krystian Życiński
 */
public class GameOverBox extends Box {
    public GameOverBox() {
        super();
    }

    public GameOverBox(GameEngine game) {
        super(game);
    }

    @Override
    public void action() {
        this.game.gameOverAction();
    }

    @Override
    public GameOverBox clone() {
        return new GameOverBox(game);
    }
}
