package myapp.tae.ac.uk.mycalendar.extras;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;

/**
 * Created by Karma on 29/03/16.
 */
public final class DateUtil {
    private DateUtil() {
    }

    public static int getStartOfTheDate(int timeStamp) {
        DateTime dateTime = new DateTime((long) timeStamp * 1000);
        dateTime = dateTime.withTimeAtStartOfDay();
        int startOfTheDate = (int) (dateTime.getMillis() / 1000);
        return startOfTheDate;
    }

    public static int getEndOfTheDate(int timeStamp) {
        DateTime dateTime = new DateTime((long) timeStamp * 1000);
        dateTime = dateTime.withTimeAtStartOfDay();
        dateTime = dateTime.plus(1).withTimeAtStartOfDay();
        int endOfTheDate = (int) (dateTime.getMillis() / 1000);
        return endOfTheDate;
    }

    public static int getStartOfTheCalendar(int timeStamp) {
        DateTime dateTime = new DateTime((long) timeStamp * 1000);
        dateTime = dateTime.withDayOfMonth(1).minusDays(dateTime.getDayOfWeek());
        dateTime = dateTime.withTime(0, 0, 0, 0);
        int startOfTheCalendar = (int) (dateTime.getMillis() / 1000);
        return startOfTheCalendar;
    }

    public static int getEndOfTheCalendar(int timeStamp) {
        DateTime dateTime = new DateTime((long) timeStamp * 1000);
        dateTime = dateTime.withDayOfMonth(1).minusDays(dateTime.getDayOfWeek());
        dateTime = dateTime.withTime(0, 0, 0, 0).plusDays(41);
        int endOfTheCalendar = (int) (dateTime.getMillis() / 1000);
        return endOfTheCalendar;
    }

    public static String getDateInFormat(int date) {
        DateTime dateTime = new DateTime((long) date * 1000);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("dd:MM:yyyy");
        return dateTime.toString(dtf);
    }

    public static String getMonthYear(DateTime date) {
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MMMM yyyy");
        return date.toString(dtf);
    }
}
