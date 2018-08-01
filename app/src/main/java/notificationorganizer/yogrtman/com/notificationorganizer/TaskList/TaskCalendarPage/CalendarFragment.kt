package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskCalendarPage

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.Toast
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.NewTaskActivity
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
import java.text.SimpleDateFormat
import java.util.*

class CalendarFragment : Fragment() {
    val TAG: String = "CalendarFragment";

    companion object {
        val CODE_NEW_TASK = 19382;
        val LABEL_NEW_TASK_TITLE = "NEW_TASK_TITLE";
        val LABEL_NEW_TASK_DESCRIPTION = "NEW_TASK_DESCRIPTION"
        val LABEL_NEW_TASK_HOUR = "NEW_TASK_HOUR";
        val LABEL_NEW_TASK_MINUTE = "NEW_TASK_MINUTE";
    }

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
            startActivityForResult(intent, CODE_NEW_TASK);
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode){
            CODE_NEW_TASK -> {
                var taskDeadlineHour: Int = if (data == null) 0 else data.getIntExtra(LABEL_NEW_TASK_HOUR, 0);
                var taskDeadlineMinute: Int = if (data == null) 0 else data.getIntExtra(LABEL_NEW_TASK_MINUTE, 0);
                var taskTitle: String = if (data == null) "" else data.getStringExtra(LABEL_NEW_TASK_TITLE);
                var taskDescription: String = if (data == null) "" else data.getStringExtra(LABEL_NEW_TASK_DESCRIPTION);

                mHighlightedDate.set(Calendar.HOUR_OF_DAY, taskDeadlineHour);
                mHighlightedDate.set(Calendar.MINUTE, taskDeadlineMinute);

                Toast.makeText(context, "New task received: " + taskTitle + ": " + taskDescription + "\n" + "Due at " + SimpleDateFormat("yyyy/MM/dd hh:mm").format(mHighlightedDate.time), Toast.LENGTH_LONG).show();

                var newTask: TaskItem = TaskItem(
                    taskTitle,
                    taskDescription,
                    0,
                    Calendar.getInstance().time,
                    mHighlightedDate.time
                );


            }
        }
    }
}