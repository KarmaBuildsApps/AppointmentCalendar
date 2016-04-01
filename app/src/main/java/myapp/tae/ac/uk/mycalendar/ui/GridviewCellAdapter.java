package myapp.tae.ac.uk.mycalendar.ui;

import android.content.Context;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import myapp.tae.ac.uk.mycalendar.R;
import myapp.tae.ac.uk.mycalendar.extras.DateUtil;
import myapp.tae.ac.uk.mycalendar.model.Appointment;

/**
 * Created by Karma on 31/03/16.
 */
public class GridviewCellAdapter extends BaseAdapter implements View.OnClickListener {
    private static final int CELL_SIZE = 42;
    private static final String TAG = GridviewCellAdapter.class.getSimpleName();
    private Context context;
    private Map<String, List<Appointment>> appointments;
    private List<Integer> calendarDates;
    private DateTime currentDate;
    private TextView prevSelectedItem;
    private View todayView;
    private long prevSelectItemId = 0;
    private boolean isFirstTime = true;
    private boolean isDayNotPartCurrentMonth = true;

    public GridviewCellAdapter(Context context, HashMap<String, List<Appointment>> appointments) {
        this.context = context;
        this.appointments = appointments;
        calendarDates = new ArrayList<>();
        currentDate = DateTime.now();
        setDaysOfTheCalendar(currentDate);
    }

    private void setDaysOfTheCalendar(DateTime date) {
        date = date.withDayOfMonth(1).minusDays(date.getDayOfWeek()).withTime(0, 0, 0, 0);
        calendarDates.clear();
        for (int i = 0; i < CELL_SIZE; i++) {
            DateTimeFormatter dft = DateTimeFormat.forPattern("dd:MM:yyyy");
            calendarDates.add((int) (date.getMillis() / 1000));
            date = date.plusDays(1);
        }
    }

    public void setCalendarEvents(Map<String, List<Appointment>> calendarEvents) {
        this.appointments = calendarEvents;
    }

    public void showNextMonth(Map<String, List<Appointment>> appointments) {
        setUnSelected();
        if (todayView != null) {
            todayView.setBackgroundResource(R.color.colorCellBackground);
        }
        currentDate = currentDate.plusMonths(1);
        setDaysOfTheCalendar(currentDate);
        setCalendarEvents(appointments);
        notifyDataSetChanged();
    }

    public void showPrevMonth(Map<String, List<Appointment>> appointments) {
        setUnSelected();
        if (todayView != null) {
            todayView.setBackgroundResource(R.color.colorCellBackground);
        }
        currentDate = currentDate.minusMonths(1);
        setDaysOfTheCalendar(currentDate);
        setCalendarEvents(appointments);
        notifyDataSetChanged();
    }

    public DateTime getCurrentDate() {
        return currentDate;
    }

    @Override
    public int getCount() {
        return CELL_SIZE;
    }

    @Override
    public String getItem(int position) {
        return calendarDates.get(position) + "";
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.gridview_cell_layout, null);
            convertView.setOnClickListener(this);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.gridviewCellImage);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.gridviewCellText);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        DateTime dateTime = new DateTime((long) calendarDates.get(position) * 1000);
        int day = dateTime.getDayOfMonth();
        viewHolder.textView.setText(day + "");
        viewHolder.textView.setTag(dateTime.getMillis());
        DateTime today = DateTime.now().withTime(0, 0, 0, 0);
//grey if the days are not of the currently displayed date
        if (currentDate.getMonthOfYear() != dateTime.getMonthOfYear()) {
            setTextColor(viewHolder.textView, R.color.colorGreyText);
        }else {
            setTextColor(viewHolder.textView, R.color.colorCalendarText);
        }
//if selected date time is exist
        if (prevSelectItemId != 0) {
            if (prevSelectItemId == dateTime.getMillis())
                setSelected(viewHolder.textView);
        }
// highlight date if the date is today's date
        if (today.getMillis() == dateTime.getMillis()) {
            if (isFirstTime) {
                isFirstTime = false;
                setSelected(viewHolder.textView);
            }
            convertView.setBackgroundResource(R.color.colorCalendarToday);
            todayView = convertView;
        }

        if (appointments != null && appointments.size() > 0) {
            String dateInFormat = DateUtil.getDateInFormat((int) (dateTime.getMillis() / 1000));
            Set<String> keys = appointments.keySet();
            if (keys.contains(dateInFormat)) {
                viewHolder.imageView.setImageResource(R.drawable.ic_appointment_line);
            }
        }
        return convertView;
    }


    @Override
    public void onClick(View v) {
        setSelected(((ViewHolder) v.getTag()).textView);// FIXME: 01/04/16 The first item's click is not responding
    }

    public void setTextColor(TextView textView, int textColor) {
        int buildVersion = Build.VERSION.SDK_INT;
        if (buildVersion >= 23)
            textView.setTextColor(ContextCompat.getColor(context, textColor));
        else
            textView.setTextColor(context.getResources().getColor(textColor));
    }

    public void setSelected(TextView selectedView) {
        setUnSelected();
        selectedView.setBackgroundResource(R.drawable.ic_round_circle);
        selectedView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        setTextColor(selectedView, R.color.colorCalendarItemSelected);
        prevSelectedItem = selectedView;
        prevSelectItemId = (long) selectedView.getTag();
    }

    private void setUnSelected() {
        if (prevSelectItemId != 0) {
            prevSelectedItem.setBackground(null);
            prevSelectedItem.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);
            DateTime selectedDate = new DateTime(prevSelectedItem.getTag());
            if (selectedDate.getMonthOfYear() == currentDate.getMonthOfYear())
                setTextColor(prevSelectedItem, R.color.colorCalendarText);
            else
                setTextColor(prevSelectedItem, R.color.colorGreyText);
        }
    }

    public static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }
}
