package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.CalendarView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_task_view.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.Notification.NotificationUtils
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.DataConvert
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.MarkStyleExp
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData
import java.text.SimpleDateFormat
import java.util.*

//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf

class TaskActivity : AppCompatActivity() {

    val TAG:String = "TaskActivity";

    companion object {
        val CODE_NEW_TASK = 19382;
        val LABEL_NEW_TASK_TITLE = "NEW_TASK_TITLE";
        val LABEL_NEW_TASK_DESCRIPTION = "NEW_TASK_DESCRIPTION"
        val LABEL_NEW_TASK_HOUR = "NEW_TASK_HOUR";
        val LABEL_NEW_TASK_MINUTE = "NEW_TASK_MINUTE";
    }

    var mTaskList: MutableList<TaskItem> = ArrayList<TaskItem>();
    var mTaskByDate: MutableMap<Long, MutableList<TaskItem> > = HashMap<Long, MutableList<TaskItem> >()

    lateinit var mRecyclerTaskList: RecyclerView;
    lateinit var mRecyclerTaskListAdapter: TaskListRecyclerViewAdapter;

    lateinit var calendarView: MCalendarView;
    lateinit var fabNewTask: FloatingActionButton;

    var mHighlightedDate: Calendar = Calendar.getInstance();
    var mLastHighlightedDate: DateData = DateData(0,0,0);

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_view)
        setSupportActionBar(appBar);

        fabNewTask = findViewById<FloatingActionButton>(R.id.fabNewTask);
        fabNewTask.setOnClickListener { view ->
            var intent = Intent(this, NewTaskActivity::class.java)

            intent.putExtra(NewTaskActivity.LABEL_SELECTED_DATE, mHighlightedDate.time.time);
            startActivityForResult(intent, CODE_NEW_TASK);
        }

        calendarView = findViewById<MCalendarView>(R.id.calendar);
        calendarView.setMarkedStyle(MarkStyle.BACKGROUND, getColor(R.color.colorPrimaryDark));
//        calendarView.setMarkedCell(R.drawable.ic_priority_high)
        calendarView.setOnDateClickListener(object: OnDateClickListener() {
            override fun onDateClick(view: View?, date: DateData?) {
                if (date == null) return;
                else {
                    Log.d(TAG, "Calendar highlighted " + date.year + "-" + date.month + "-" + date.day);
                    mHighlightedDate.set(Calendar.YEAR, date.year);
                    mHighlightedDate.set(Calendar.MONTH, date.month-1);     //MCalendarView has weird month indexing
                    mHighlightedDate.set(Calendar.DAY_OF_MONTH, date.day);

                    calendarView.markDate(date.setMarkStyle(MarkStyle(MarkStyle.BACKGROUND, getColor(R.color.colorPrimary))));
                    calendarView.unMarkDate(mLastHighlightedDate);

                    mLastHighlightedDate = date;
//                    Log.d(TAG, "Truncated date " +
//                            SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS").format(
//                                    Date(DataConvert.truncateToDay(mHighlightedDate.timeInMillis)))
//                            );

                    setTaskListByDay(DataConvert.truncateToDay(mHighlightedDate.timeInMillis))
                }
            }
        });

        mTaskList = DataConvert.readJSONFromStorage(this);
        for (taskItem: TaskItem in mTaskList) {
            var date = DataConvert.truncateToDay(taskItem.dateDeadline);
//            Log.d(TAG, "Adding to map: Key " + SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS").format(
//                    Date(date))
//            );

            if (!mTaskByDate.containsKey(date)) {
                mTaskByDate[date] = ArrayList<TaskItem>();
            }
            mTaskByDate[date]?.add(taskItem);
        }

        mRecyclerTaskList = findViewById<RecyclerView>(R.id.recyclerTaskList);
        mRecyclerTaskListAdapter = TaskListRecyclerViewAdapter(mTaskList);
        mRecyclerTaskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            adapter = mRecyclerTaskListAdapter;
        }

        for (taskItem: TaskItem in mTaskList) {
            var calendar = Calendar.getInstance();
            calendar.time = taskItem.dateDeadline;

//            calendarView.setMarkedStyle(MarkStyle.DOT, getColor(R.color.md_red_100))
            calendarView.markDate(
                    DateData(
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1,     //account for +1 MCalendarView month indexing
                        calendar.get(Calendar.DAY_OF_MONTH)
                ).setMarkStyle(MarkStyle(MarkStyle.DOT, getColor(R.color.md_red_A700)))
            )
        }

        var itemTouchHelperCallback: ItemTouchHelper.Callback = ItemTouchHelperCallback(mRecyclerTaskListAdapter);
        var itemTouchHelper: ItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerTaskList);

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            CODE_NEW_TASK -> {
                var taskDeadlineHour: Int = if (data == null) 0 else data.getIntExtra(LABEL_NEW_TASK_HOUR, 0);
                var taskDeadlineMinute: Int = if (data == null) 0 else data.getIntExtra(LABEL_NEW_TASK_MINUTE, 0);
                var taskTitle: String = if (data == null) "" else data.getStringExtra(LABEL_NEW_TASK_TITLE);
                var taskDescription: String = if (data == null) "" else data.getStringExtra(LABEL_NEW_TASK_DESCRIPTION);

                mHighlightedDate.set(Calendar.HOUR_OF_DAY, taskDeadlineHour);
                mHighlightedDate.set(Calendar.MINUTE, taskDeadlineMinute);

                Toast.makeText(this, "New task received: " + taskTitle + ": " + taskDescription + "\n" + "Due at " + SimpleDateFormat("yyyy/MM/dd hh:mm").format(mHighlightedDate.time), Toast.LENGTH_LONG).show();

                var newTask: TaskItem = TaskItem(
                        taskTitle,
                        taskDescription,
                        0,
                        Calendar.getInstance().time,
                        mHighlightedDate.time
                );

                mTaskList.add(newTask);
                mRecyclerTaskListAdapter.notifyItemInserted(mTaskList.size-1);

                NotificationUtils.setNotification(Calendar.getInstance().timeInMillis+5000, this)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        DataConvert.writeJSONToStorage(this, DataConvert.convertToJSONString(mTaskList));
    }

    fun setTaskListByDay(date: Long) {
//        Log.d(TAG, "Passed date " +
//                SimpleDateFormat("yyyy/MM/dd hh:mm:ss.SSS").format(
//                        Date(date))
//        );

        if (mTaskByDate.containsKey(date)) {
            Log.d(TAG, "Found mapping for date");
            mRecyclerTaskListAdapter.mTaskList = mTaskByDate[date];
        }
        else {
            Log.d(TAG, "No mapping found for date");
            mRecyclerTaskListAdapter.mTaskList = null;
        }
        mRecyclerTaskListAdapter.notifyDataSetChanged();
    }
}