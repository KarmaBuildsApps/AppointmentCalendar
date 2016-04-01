package myapp.tae.ac.uk.mycalendar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.LinearLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import myapp.tae.ac.uk.mycalendar.api.OnAppointmentClickListener;
import myapp.tae.ac.uk.mycalendar.ui.AppointmentCalendarView;
import myapp.tae.ac.uk.mycalendar.ui.GridviewCellAdapter;

public class MainActivity extends AppCompatActivity implements OnAppointmentClickListener {
    private static final String TAG = MainActivity.class.getSimpleName();
    @Bind(R.id.calView)
    AppointmentCalendarView calendarView;
    @Bind(R.id.toolbar)
    Toolbar toolbar;
//    @Bind(R.id.calSearchView)
//    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setupSearchView();
        initiateCalendarView();
        LinearLayout linearLayout;
        setSupportActionBar(toolbar);
    }

    private void initiateCalendarView() {
//        calendarView.setShowWeekNumber(false);
//        calendarView.setFirstDayOfWeek(2);
//        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
//            @Override
//            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
//
//            }
//        });
        calendarView.setOnAppointmentClickedListener(this);
        calendarView.setCalendarAdapter(new GridviewCellAdapter(this, null));

    }

    private void setupSearchView() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.options_menu, menu);
        return super.onCreateOptionsMenu(menu) | true;
    }

    @Override
    public void onNextMonthClicked(View view) {
        Log.i(TAG, "onNextMonthClicked: NextMonthClicked");
        calendarView.showNextMonth(null);
    }

    @Override
    public void onPrevMonthClicked(View view) {
        Log.i(TAG, "onPrevMonthClicked: PreviousClicked");
        calendarView.showPrevMonth(null);
    }
}
