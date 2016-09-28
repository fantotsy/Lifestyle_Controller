public class Result {
    /*
        [kcal, liters, time, steps]
     */
    private double[] info;

    public Result() {
        info = new double[4];
    }

    public void increaseCertainInfo(int position, double value) {
        info[position] += value;
    }

    public double getCertainInfo(int position) {
        return info[position];
    }
}