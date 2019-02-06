package com.yggdrasil.recruitment.game;

/**
 * @author Krystian Życiński
 */
public class Main {
    private static final int SIMULATIONS = 10000000;
    public static void main(String[] args) {
        int simulationResult = simulate();
        int calculationResult = calculate();
        System.out.println("Result of 10 000 000 simulations: ");
        System.out.println("Result of calculations: ");
    }
    
    private static int simulate() {
        long reward = 0;
        for(int i = 0; i < SIMULATIONS; i++){
            //todo reward += Game.simulateGame();
        }
        return (int)(reward / SIMULATIONS);
    }
    
    private static int calculate() {
        return 0;
    }
}
