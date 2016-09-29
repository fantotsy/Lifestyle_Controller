import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestClass {
    private static final double delta = 0.000001;

    private Health health;

    @Before
    public void setUp() {
        health = new Health(3000, 2.0, 120, 3000);
    }

    @Test
    public void testEatKcal() {
        health.eatKcal(1000);
        int currentKcal = (int) health.getCurrentResult().getCertainInfo(0);
        assertEquals(1000, currentKcal);
    }

    @Test
    public void testDrinkLiters() {
        health.drinkLiters(1.0);
        double currentLiters = health.getCurrentResult().getCertainInfo(1);
        assertEquals(1.0, currentLiters, delta);
    }

    @Test
    public void testGoTime() {
        health.goTime(30);
        int currentTime = (int) health.getCurrentResult().getCertainInfo(2);
        assertEquals(30, currentTime, delta);
    }

    @Test
    public void testGoSteps() {
        health.goSteps(2000);
        int currentSteps = (int) health.getCurrentResult().getCertainInfo(3);
        assertEquals(2000, currentSteps, delta);
    }

    @Test
    public void testKcalLeft() {
        health.eatKcal(1000);
        int kcalLeft = health.kcalLeft();
        assertEquals(2000, kcalLeft);
    }

    @Test
    public void testLitersLeft() {
        health.drinkLiters(1.0);
        double litersLeft = health.litersLeft();
        assertEquals(1.0, litersLeft, delta);
    }

    @Test
    public void testTimeLeft() {
        health.goTime(60);
        int timeLeft = health.timeLeft();
        assertEquals(60, timeLeft);
    }

    @Test
    public void testStepsLeft() {
        health.goSteps(1000);
        int stepsLeft = health.stepsLeft();
        assertEquals(2000, stepsLeft);
    }

    @Test
    public void testPercentage() {
        health.eatKcal(1500);
        int actualKcal = (int) health.getCurrentResult().getCertainInfo(0);
        int percentage = health.getPercentage(health.getMinKcal(), actualKcal);
        assertEquals(50, percentage);
    }

    @Test
    public void testWeekMedian() {
        List<Result> weekResults = new ArrayList<Result>();
        for (int i = 0; i < 7; i++) {
            Result result = new Result();
            result.increaseCertainInfo(0, 1000 * i);
            weekResults.add(result);
        }
        String date = "2016.09.29";
        for (Result result : weekResults) {
            health.getHistory().put(date, result);
            date = DateOperations.getNextDate(date);
        }
        assertEquals(3000, health.getMedian(0, weekResults), delta);
    }

    @Test
    public void testGetNextDate() {
        String date = "2017.03.01";
        assertTrue(date.equals(DateOperations.getNextDate("2017.02.28")));
    }

    @Test
    public void testNotLeapYear() {
        assertEquals(false, DateOperations.isLeapYear(2017));
    }

    @Test
    public void testLeapYear() {
        assertEquals(true, DateOperations.isLeapYear(2020));
    }
}