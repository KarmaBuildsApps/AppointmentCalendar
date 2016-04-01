package myapp.tae.ac.uk.mycalendar.model.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import myapp.tae.ac.uk.mycalendar.extras.DateUtil;

/**
 * Created by Karma on 29/03/16.
 */
public class AppointmentDatabaseHelper extends SQLiteOpenHelper {
    private Context context;

    public AppointmentDatabaseHelper(Context context) {
        super(context, CalendarDataContract.DATABASE_NAME, null, CalendarDataContract.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CalendarDataContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CalendarDataContract.DROP_TABLE);
        onCreate(db);
    }

    public void addAppointment(String title, String detail, int date) {
        ContentValues newAppointment = new ContentValues();
        newAppointment.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_TITLE, title);
        newAppointment.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DETAIL, detail);
        newAppointment.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME, date);
        SQLiteDatabase db = getWritableDatabase();
        db.insert(CalendarDataContract.Appointment.TABLE_NAME, null, newAppointment);
        db.close();
    }

    public void removeAppointment(int _id) {
        String whereClause = CalendarDataContract.Appointment._ID + "=?";
        String whereArgs[] = {_id + ""};
        SQLiteDatabase db = getWritableDatabase();
        db.delete(CalendarDataContract.Appointment.TABLE_NAME, whereClause, whereArgs);
        db.close();
    }

    public void editAppointment(int _id, String title, String detail, int date) {
        ContentValues newValues = new ContentValues();
        newValues.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_TITLE, title);
        newValues.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DETAIL, detail);
        newValues.put(CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME, date);
        String whereClause = CalendarDataContract.Appointment._ID + "=?";
        String whereArgs[] = {_id + ""};
        SQLiteDatabase db = getWritableDatabase();
        db.update(CalendarDataContract.Appointment.TABLE_NAME, newValues, whereClause, whereArgs);
        db.close();
    }

    public boolean hasAppointmentWithSameTitle(String title, int date) {
        int endOfTheDate = DateUtil.getEndOfTheDate(date);
        int startOfTheDate = DateUtil.getStartOfTheDate(date);
        String selectionClause = CalendarDataContract.Appointment.COLUMN_APPOINTMENT_TITLE + "=? COLLATE NOCASE AND " +
                CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME + ">? AND " +
                CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME + "<?";
        String[] selectionArgs = {"'" + title + "'", startOfTheDate + "", endOfTheDate + ""};
        SQLiteDatabase db = getReadableDatabase();
        Boolean hasAppointmentWithSameTitle = db.query(CalendarDataContract.Appointment.TABLE_NAME, null, selectionClause,
                selectionArgs, null, null, null).getCount() > 0;
        db.close();
        return hasAppointmentWithSameTitle;
    }

    public Cursor getAppointmentsInMonth(int date) {
        int startOfTheMonth = DateUtil.getStartOfTheCalendar(date);
        int endOfTheMonth = DateUtil.getEndOfTheCalendar(date);
        String selectionClause = CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME + ">? AND " +
                CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME + "<?";
        String[] selectionArgs = {startOfTheMonth + "", endOfTheMonth + ""};
        String orderBy = CalendarDataContract.Appointment.COLUMN_APPOINTMENT_DATE_TIME + " ASC";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(CalendarDataContract.Appointment.TABLE_NAME, null, selectionClause, selectionArgs,
                null, null, orderBy);
        db.close();
        return cursor;
    }
}
