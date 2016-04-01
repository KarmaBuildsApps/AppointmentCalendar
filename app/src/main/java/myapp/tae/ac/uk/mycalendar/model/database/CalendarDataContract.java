package myapp.tae.ac.uk.mycalendar.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Karma on 29/03/16.
 */
public final class CalendarDataContract {
    public CalendarDataContract() {
    }

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "appointment.db";
    public static final String TEXT_TYPE = " TEXT";
    public static final String INTEGER_TYPE = " INTEGER";
    public static final String COMMA_SEP = ",";
    public static final String OPEN_PARAM = " (";
    public static final String CLOSE_PARAM = " )";
    public static final String CREATE_TABLE = "CREATE TABLE " +
            Appointment.TABLE_NAME + OPEN_PARAM +
            Appointment._ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL" + COMMA_SEP +
            Appointment.COLUMN_APPOINTMENT_TITLE + TEXT_TYPE + COMMA_SEP +
            Appointment.COLUMN_APPOINTMENT_DATE_TIME + TEXT_TYPE + COMMA_SEP +
            Appointment.COLUMN_APPOINTMENT_DETAIL + INTEGER_TYPE + CLOSE_PARAM;
    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + Appointment.TABLE_NAME;

    public static abstract class Appointment implements BaseColumns {
        public static final String TABLE_NAME = "Appointment_Table";
        public static final String COLUMN_APPOINTMENT_TITLE = "_title";
        public static final String COLUMN_APPOINTMENT_DATE_TIME = "_date_time";
        public static final String COLUMN_APPOINTMENT_DETAIL = "_detail";
    }
}
