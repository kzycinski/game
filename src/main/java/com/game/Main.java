package com.game;

/**
 * @author Krystian Życiński
 */
public class Main {
    public static void main(String[] args) {
        double simulationResult = MainUtils.simulate();
        System.out.println("Result of 10 000 000 simulations: " + simulationResult);

        double calculationResult = MainUtils.calculate();
        System.out.println("Result of calculations: " + calculationResult);
    }

}



