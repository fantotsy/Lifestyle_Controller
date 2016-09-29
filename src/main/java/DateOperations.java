import java.util.HashMap;
import java.util.Map;

public final class DateOperations {
    private static final Map<Integer, Integer> monthsAndDays = new HashMap<Integer, Integer>() {{
        put(1, 31);
        put(3, 31);
        put(4, 30);
        put(5, 31);
        put(6, 30);
        put(7, 31);
        put(8, 31);
        put(9, 30);
        put(10, 31);
        put(11, 30);
        put(12, 31);
    }};

    public static String getNextDate(String date) {
        String result;

        Integer year = Integer.parseInt(date.substring(0, 4));
        Integer month = Integer.parseInt(date.substring(5, 7));
        Integer day = Integer.parseInt(date.substring(8));

        int daysInMonth;

        if (month != 2) {
            daysInMonth = monthsAndDays.get(month);
        } else {
            if (isLeapYear(year)) {
                daysInMonth = 29;
            } else {
                daysInMonth = 28;
            }
        }

        if ((month == 12) && (day == daysInMonth)) {
            year++;
        }
        if (day == daysInMonth) {
            month++;
            day = 1;
        } else {
            day++;
        }

        result = "" + year + ".";
        if (month.toString().length() == 1) {
            result += "0";
        }
        result += month + ".";
        if (day.toString().length() == 1) {
            result += "0";
        }
        result += day;
        return result;
    }

    public static boolean isLeapYear(int year) {
        return (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0));
    }
}