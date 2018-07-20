package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskCalendarPage

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.NewTaskActivity
import java.util.*

class CalendarFragment : Fragment() {
    val TAG: String = "CalendarFragment";

    lateinit var calendarView: CalendarView;
    lateinit var fabNewTask: FloatingActionButton;

    var mHighlightedDate: Calendar = Calendar.getInstance();

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // The last two arguments ensure LayoutParams are inflated properly.

        val rootView: View = inflater.inflate(
                R.layout.fragment_task_calendar, container, false);

        fabNewTask = rootView.findViewById<FloatingActionButton>(R.id.fabNewTask);
        fabNewTask.setOnClickListener { view ->
            var intent = Intent(context, NewTaskActivity::class.java)

            intent.putExtra(NewTaskActivity.LABEL_SELECTED_DATE, mHighlightedDate.time.time);
            startActivity(intent);
        }

        calendarView = rootView.findViewById<CalendarView>(R.id.calendar);
        calendarView.setOnDateChangeListener {view, year, month, dayOfMonth ->
            Log.d(TAG, "Calendar highlighted " + year + "-" + month + "-" + dayOfMonth);
            mHighlightedDate.set(Calendar.YEAR, year);
            mHighlightedDate.set(Calendar.MONTH, month);
            mHighlightedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }
        return rootView
    }

}