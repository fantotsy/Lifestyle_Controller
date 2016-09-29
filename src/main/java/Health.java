import java.text.SimpleDateFormat;
import java.util.*;

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

    public List<Result> getPeriod(String firstDate, String lastDate) {
        List<Result> result = new ArrayList<Result>();
        String date = firstDate;
        if (history.containsKey(firstDate) && history.containsKey(lastDate)) {
            do {
                result.add(history.get(date));
                date = DateOperations.getNextDate(date);
            } while (!date.equals(lastDate));
        } else {
            throw new IllegalArgumentException();
        }
        return result;
    }

    public double getMedian(int position, List<Result> results) {
        int amountOfValues = results.size();
        List<Double> investigatedValues = new ArrayList<Double>();
        for (Result result : results) {
            investigatedValues.add(result.getCertainInfo(position));
        }
        Collections.sort(investigatedValues, new Comparator<Double>() {
            public int compare(Double value1, Double value2) {
                return value1.compareTo(value2);
            }
        });
        if (amountOfValues % 2 != 0) {
            return investigatedValues.get(amountOfValues / 2);
        } else {
            double firstValue = investigatedValues.get((amountOfValues / 2) - 1);
            double secondValue = investigatedValues.get(amountOfValues / 2);
            return ((firstValue + secondValue) / 2);
        }
    }

}