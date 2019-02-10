package com.game.engine;

/**
 * @author Krystian Życiński
 * 
 * I suppose player can pick specific box only once (not specified in task)
 */
public abstract class Box {
    protected GameEngine game;
    protected boolean taken;

    public abstract void action();
    
    public abstract Box clone();
    
    protected Box() {
        taken = false;
    }
    protected Box(GameEngine game) {
        this.game = game;
        taken = false;
    }
    
    public boolean isTaken() {
        return this.taken;
    }
    
    protected void setTaken() {
        this.taken = true;
    }
    
    protected void reset() {this.taken = false; }
    
    protected void setGame(GameEngine game) {
        this.game = game;
    }
}
