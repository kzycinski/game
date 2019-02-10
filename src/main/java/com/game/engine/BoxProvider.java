package com.game.engine;

import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Krystian Życiński
 */
public class BoxProvider {
    private static List<Box> gameBoxList;
    private static List<Box> gameOverBoxList;
    private static List<Box> gameOverOverBoxList;

    static {
        initGameBoxList();
        initGameOverBoxList();
        initGameOverOverBoxList();
    }

    private static void initGameBoxList() {
        gameBoxList = new ArrayList<>();
        gameBoxList.add(new SecondChanceBox());
        for (int i = 0; i < 3; i++) {
            gameBoxList.add(new GameOverBox());
        }
        for (int i = 0; i < 2; i++) {
            gameBoxList.add(new MoneyBox(20));
        }
        for (int i = 0; i < 5; i++) {
            gameBoxList.add(new MoneyBox(5));
        }
        gameBoxList.add(new MoneyBox(100));
    }

    private static void initGameOverBoxList() {
        gameOverBoxList = new ArrayList<>();
        gameOverBoxList.add(new MoneyBox(5));
        gameOverBoxList.add(new MoneyBox(10));
        gameOverBoxList.add(new MoneyBox(20));
        gameOverBoxList.add(new ExtraGameBox());
    }

    private static void initGameOverOverBoxList() {
        gameOverOverBoxList = new ArrayList<>();
        gameOverOverBoxList.add(new MoneyBox(5));
        gameOverOverBoxList.add(new MoneyBox(10));
        gameOverOverBoxList.add(new MoneyBox(20));
    }

    public static List<Box> getGameBoxList(GameEngine game) {
        return getShuffledCopyOfList(gameBoxList, game);
    }

    public static List<Box> getGameOverBoxList(GameEngine game) {
        return getShuffledCopyOfList(gameOverBoxList, game);
    }

    public static List<Box> getGameBoxList() {
        return getShuffledCopyOfList(gameBoxList);
    }

    public static List<Box> getGameOverBoxList() {
        return getShuffledCopyOfList(gameOverBoxList);
    }

    public static List<Box> getGameOverOverBoxList() {
        return getShuffledCopyOfList(gameOverOverBoxList);
    }

    private static List<Box> getShuffledCopyOfList(List<Box> list) {
        List<Box> shuffledList = list.stream().map(Box::clone).collect(toList());
        Collections.shuffle(shuffledList);
        return shuffledList;
    }

    private static List<Box> getShuffledCopyOfList(List<Box> list, GameEngine game) {
        List<Box> shuffledList = list.stream().map(Box::clone).collect(toList());
        shuffledList.forEach(e -> e.setGame(game));
        Collections.shuffle(shuffledList);
        return shuffledList;
    }
}
