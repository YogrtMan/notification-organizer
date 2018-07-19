package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskCalendarPage

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.NewTaskActivity

class CalendarFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // The last two arguments ensure LayoutParams are inflated properly.

        val rootView: View = inflater.inflate(
                R.layout.fragment_task_calendar, container, false);

        rootView.findViewById<FloatingActionButton>(R.id.fabNewTask).setOnClickListener { view ->
            var intent = Intent(context, NewTaskActivity::class.java)
            startActivity(intent);
        }

        return rootView
    }

}