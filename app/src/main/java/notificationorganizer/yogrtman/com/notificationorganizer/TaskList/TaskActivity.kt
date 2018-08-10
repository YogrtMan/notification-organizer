package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task_view.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskCalendarPage.CalendarFragment
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import java.util.*

//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf

class TaskActivity : AppCompatActivity() {
    lateinit var taskViewPagerAdapter: TaskViewPagerAdapter;
    lateinit var mTaskList: MutableList<TaskItem>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_view)
        setSupportActionBar(appBar);

        mTaskList = ArrayList<TaskItem>();
        //temp values
        mTaskList.add(TaskItem(
                "Take out trash",
                "",
                0,
                Date(Calendar.getInstance().time.time-1000000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Groceries",
                "eggs, steak, asparagus",
                0,
                Date(Calendar.getInstance().time.time-1010000000000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "135 Assignment",
                "Start early, review chapter on CRT",
                0,
                Date(Calendar.getInstance().time.time-1000000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Finish side project",
                "hurry up and finish it you shit",
                0,
                Date(Calendar.getInstance().time.time-1000000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Send greyhound refund email",
                "",
                0,
                Date(Calendar.getInstance().time.time-201000000000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Wake up from bed",
                "pretty necessary to do stuff, and I need one more item",
                0,
                Date(Calendar.getInstance().time.time),
                Date(Calendar.getInstance().time.time)
        ));

        taskViewPagerAdapter = TaskViewPagerAdapter(supportFragmentManager, mTaskList);
        pagerTaskView.adapter = taskViewPagerAdapter;
        tabTaskView.setupWithViewPager(pagerTaskView);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CalendarFragment.CODE_NEW_TASK -> {
                Toast.makeText(this, "got activity " + data?.getStringExtra("NEW_TASK_JSON"), Toast.LENGTH_LONG).show();
            }
        }
    }
}