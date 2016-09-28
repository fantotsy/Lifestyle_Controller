import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Health {
    private int minKcal;
    private double minLiters;
    private int minTime;
    private int minSteps;

    private Map<String, Result> history;

    Health() {
        history = new HashMap<String, Result>();
    }

    Health(int minKcal, double minLiters, int minTime, int minSteps) {
        this();
        this.minKcal = minKcal;
        this.minLiters = minLiters;
        this.minTime = minTime;
        this.minSteps = minSteps;
    }

    public Map<String, Result> getHistory() {
        return history;
    }

    public int getMinKcal() {
        return minKcal;
    }

    public double getMinLiters() {
        return minLiters;
    }

    public int getMinTime() {
        return minTime;
    }

    public int getMinSteps() {
        return minSteps;
    }

    public void eatKcal(int kcal) {
        trackHistory(0, kcal);
    }

    public void drinkLiters(double liters) {
        trackHistory(1, liters);
    }

    public void goTime(int time) {
        trackHistory(2, time);
    }

    public void goSteps(int steps) {
        trackHistory(3, steps);
    }

    public int kcalLeft() {
        Result currentResult = getCurrentResult();
        int result = minKcal - (int) currentResult.getCertainInfo(0);
        return result > 0 ? result : 0;
    }

    public double litersLeft() {
        Result currentResult = getCurrentResult();
        double result = minLiters - currentResult.getCertainInfo(1);
        return result > 0.0 ? result : 0.0;
    }

    public int timeLeft() {
        Result currentResult = getCurrentResult();
        int result = minTime - (int) currentResult.getCertainInfo(2);
        return result > 0 ? result : 0;
    }

    public int stepsLeft() {
        Result currentResult = getCurrentResult();
        int result = minSteps - (int) currentResult.getCertainInfo(3);
        return result > 0 ? result : 0;
    }

    public Result getCurrentResult() {
        String shortToday = getTodayDate();
        if (!history.containsKey(shortToday)) {
            history.put(shortToday, new Result());
        }
        return history.get(shortToday);
    }

    public void trackHistory(int position, double value) {
        Result currentResult = getCurrentResult();
        currentResult.increaseCertainInfo(position, value);
    }

    public String getTodayDate() {
        Date today = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(today);
    }

    public int getPercentage(double minimum, double actual) {
        return (int) ((actual * 100) / minimum);
    }

}