package myapp.tae.ac.uk.mycalendar.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import org.joda.time.DateTime;

import java.util.List;
import java.util.Map;

import myapp.tae.ac.uk.mycalendar.R;
import myapp.tae.ac.uk.mycalendar.api.OnAppointmentClickListener;
import myapp.tae.ac.uk.mycalendar.extras.DateUtil;
import myapp.tae.ac.uk.mycalendar.model.Appointment;

/**
 * Created by Karma on 01/04/16.
 */
public class AppointmentCalendarView extends RelativeLayout implements View.OnClickListener {
    private static final String TAG = AppointmentCalendarView.class.getSimpleName();
    private ImageView ivPrevMonth;
    private ImageView ivNextMonth;
    private TextView tvMonthName;
    private GridView gvCalendarView;
    private GridviewCellAdapter calendarAdapter;
    private LinearLayout llDaysOfWeekHeader;
    private OnAppointmentClickListener appointmentClickListener;
    private int[] seasonHeaderColors;
    private int[] monthSeasonKeys;


    public AppointmentCalendarView(Context context) {
        super(context);
        inflateAndInitiateViews(context);
    }

    public AppointmentCalendarView(Context context, AttributeSet attr) {
        super(context, attr);
        inflateAndInitiateViews(context);
    }

    public AppointmentCalendarView(Context context, AttributeSet attr, int defStyle) {
        super(context, attr, defStyle);
        inflateAndInitiateViews(context);
    }

    private void inflateAndInitiateViews(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.calendar_view_layout, this);
        seasonHeaderColors = getHeaderColors();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        llDaysOfWeekHeader = (LinearLayout) this.findViewById(R.id.calendarDaysOfWeek);
        ivPrevMonth = (ImageView) this.findViewById(R.id.calendarPrev);
        ivNextMonth = (ImageView) this.findViewById(R.id.calendarNext);
        tvMonthName = (TextView) this.findViewById(R.id.calendarMonthLabel);
        gvCalendarView = (GridView) this.findViewById(R.id.gvCalendarView);
        ivPrevMonth.setOnClickListener(this);
        ivNextMonth.setOnClickListener(this);

    }

    public void setCalendarAdapter(GridviewCellAdapter adapter) {
        this.calendarAdapter = adapter;
        gvCalendarView.setAdapter(adapter);
        updateHeaders(calendarAdapter.getCurrentDate());

    }

    public void setAppointmentEvents(Map<String, List<Appointment>> appointments) {
        if (calendarAdapter != null) {
            calendarAdapter.setCalendarEvents(appointments);
        }
    }

    public void showNextMonth(Map<String, List<Appointment>> appointments) {
        calendarAdapter.showNextMonth(appointments);
        updateHeaders(calendarAdapter.getCurrentDate());
    }

    public void showPrevMonth(Map<String, List<Appointment>> appointments) {
        calendarAdapter.showPrevMonth(appointments);
        updateHeaders(calendarAdapter.getCurrentDate());
    }

    private void updateHeaders(DateTime currentDate) {
        String calendarMonth = DateUtil.getMonthYear(currentDate);
        int seasonColorKey = monthSeasonKeys[currentDate.getMonthOfYear() - 1];
        Log.i(TAG, "updateHeaders: Current Color Code: " + seasonColorKey);
        tvMonthName.setText(calendarMonth);
        llDaysOfWeekHeader.setBackgroundColor(getResources().getColor(seasonHeaderColors[seasonColorKey]));

    }

    public void setOnAppointmentClickedListener(OnAppointmentClickListener appointmentClickedListener) {
        this.appointmentClickListener = appointmentClickedListener;
    }

    @Override
    public void onClick(View v) {
        if (v == ivNextMonth) {
            appointmentClickListener.onNextMonthClicked(v);
        } else if (v == ivPrevMonth) {
            appointmentClickListener.onPrevMonthClicked(v);
        }
    }

    public int[] getHeaderColors() {
        int[] headerColors = new int[]{
                R.color.winter, R.color.spring, R.color.summer, R.color.fall
        };
        monthSeasonKeys = new int[]{0, 0, 1, 1, 1, 2, 2, 2, 3, 3, 3, 0};
        return headerColors;
    }
}
