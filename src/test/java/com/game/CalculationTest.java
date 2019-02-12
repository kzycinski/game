package com.game;

import com.game.engine.Box;
import com.game.engine.ExtraGameBox;
import com.game.engine.GameOverBox;
import com.game.engine.MoneyBox;
import com.game.engine.SecondChanceBox;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

/**
 * @author Krystian Życiński
 */
@PrepareForTest(Main.class)
@RunWith(PowerMockRunner.class)
public class CalculationTest {
    private DecimalFormat df = new DecimalFormat("#.####");
    private Method calculateMainGameExpectedReward;
    private Method calculateMainGameExpectedRewardForBox;
    private Method calculateGameOverExpectedReward;
    private Method calculateGameOverExpectedRewardForBox;
    private MoneyBox moneyBox1;
    private MoneyBox moneyBox2;
    private GameOverBox gameOverBox;
    private SecondChanceBox secondChanceBox;
    private ExtraGameBox extraGameBox;

    @Before
    public void init() {
        moneyBox1 = new MoneyBox(10);
        moneyBox2 = new MoneyBox(10);
        gameOverBox = new GameOverBox();
        secondChanceBox = new SecondChanceBox();
        extraGameBox = new ExtraGameBox();
        try {
            calculateMainGameExpectedReward =
                    Main.class.getDeclaredMethod("calculateMainGameExpectedReward", List.class, boolean.class);
            calculateMainGameExpectedRewardForBox =
                    Main.class.getDeclaredMethod("calculateMainGameExpectedRewardForBox", Box.class, List.class, boolean.class);
            calculateGameOverExpectedReward =
                    Main.class.getDeclaredMethod("calculateGameOverExpectedReward", List.class);
            calculateGameOverExpectedRewardForBox =
                    Main.class.getDeclaredMethod("calculateGameOverExpectedRewardForBox", Box.class);
            
            calculateMainGameExpectedReward.setAccessible(true);
            calculateMainGameExpectedRewardForBox.setAccessible(true);
            calculateGameOverExpectedReward.setAccessible(true);
            calculateGameOverExpectedRewardForBox.setAccessible(true);
        } catch (NoSuchMethodException e) {
            System.out.println("Error in init()");
            e.printStackTrace();
        }
    }

    @Test
    public void testCalculateMainGameExpectedRewardForBox() throws InvocationTargetException, IllegalAccessException {
        Assert.assertEquals(10.0, 
                calculateMainGameExpectedRewardForBox.invoke(null, moneyBox1, new ArrayList<>(), false)
        );
        Assert.assertEquals(0.0, 
                calculateMainGameExpectedRewardForBox.invoke(null, gameOverBox, new ArrayList<>(), false)
        );
        Assert.assertEquals(0.0,
                calculateMainGameExpectedRewardForBox.invoke(null, gameOverBox, Arrays.asList(moneyBox2, moneyBox1), false)
        );
        Assert.assertEquals(10.0,
                calculateMainGameExpectedRewardForBox.invoke(null, gameOverBox, Collections.singletonList(moneyBox1), true)
        );
        Assert.assertEquals(20.0, 
                calculateMainGameExpectedRewardForBox.invoke(null, gameOverBox, Arrays.asList(moneyBox2, moneyBox1), true)
        );
        Assert.assertEquals(20.0,
                calculateMainGameExpectedRewardForBox.invoke(null, moneyBox1, Collections.singletonList(moneyBox2), false)
        );
        Assert.assertEquals(15.0,
                calculateMainGameExpectedRewardForBox.invoke(null, moneyBox1, Arrays.asList(moneyBox2, gameOverBox), false)
        );
        Assert.assertEquals(10.0,
                calculateMainGameExpectedRewardForBox.invoke(null, secondChanceBox, Arrays.asList(moneyBox2, gameOverBox), false)
        );
        Assert.assertEquals(
                df.format(10 + 20.0 / 3),
                df.format(calculateMainGameExpectedRewardForBox.invoke(null, moneyBox1, Arrays.asList(moneyBox2, gameOverBox, secondChanceBox), false))
        );
    }
    
    @Test
    public void testCalculateMainGameExpectedReward() throws InvocationTargetException, IllegalAccessException {
        calculateMainGameExpectedReward.invoke(null, new ArrayList<>(), false);
        Assert.assertEquals(10.0,
                calculateMainGameExpectedReward.invoke(null, Collections.singletonList(moneyBox1), false)
        );
        Assert.assertEquals(5.0,
                calculateMainGameExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox), false)
        );
        Assert.assertEquals(10.0,
                calculateMainGameExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox), true)
        );
        Assert.assertEquals(
                df.format(20.0 / 3),
                df.format(calculateMainGameExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox, secondChanceBox), false))               
        );
        Assert.assertEquals(10.0, 
                calculateMainGameExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox, moneyBox2), false)
        );
        Assert.assertEquals(
                df.format((50.0 / 6) + 5),
                df.format(calculateMainGameExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox, moneyBox2, secondChanceBox), false))
        );
    }

    @Test
    public void testCalculateGameOverExpectedRewardForBox() throws InvocationTargetException, IllegalAccessException {
        PowerMockito.stub(PowerMockito.method(Main.class, "calculateMainGameExpectedReward")).toReturn(10.0);
        PowerMockito.stub(PowerMockito.method(Main.class, "calculateGameOverExpectedReward")).toReturn(10.0);
        Assert.assertEquals(10.0, calculateGameOverExpectedRewardForBox.invoke(null, moneyBox1));
        Assert.assertEquals(0.0, calculateGameOverExpectedRewardForBox.invoke(null, secondChanceBox));
        Assert.assertEquals(0.0, calculateGameOverExpectedRewardForBox.invoke(null, gameOverBox));
    }

    @Test
    public void testCalculateGameOverExpectedReward() throws Exception {
        Assert.assertEquals(10.0, calculateGameOverExpectedReward.invoke(null, Arrays.asList(moneyBox1, moneyBox2)));
        Assert.assertEquals(5.0, calculateGameOverExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox)));
        Assert.assertEquals(5.0, calculateGameOverExpectedReward.invoke(null, Arrays.asList(moneyBox1, gameOverBox)));

        PowerMockito.stub(PowerMockito.method(Main.class, "calculateGameOverExpectedRewardForBox")).toReturn(20.0);
        Assert.assertEquals(20.0,
                calculateGameOverExpectedReward.invoke(null, Collections.singletonList(extraGameBox))
        );
        Assert.assertEquals(20.0,
                calculateGameOverExpectedReward.invoke(null, Arrays.asList(extraGameBox, moneyBox1, moneyBox2))
        );
    }
}
