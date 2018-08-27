package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.app.Notification
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.NotificationCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.Notification.NotificationUtils
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.DataConvert
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.DateUtil
import sun.bob.mcalendarview.MCalendarView
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.listeners.OnDateClickListener
import sun.bob.mcalendarview.vo.DateData
import java.text.SimpleDateFormat
import java.util.*
import android.app.PendingIntent



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
    var mLastHighlightedDate: Calendar = Calendar.getInstance();


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_view)
        setSupportActionBar(appBar);

        NotificationUtils.createNotificationChannels(this);

        mLastHighlightedDate.timeInMillis = 0;

        fabNewTask = findViewById<FloatingActionButton>(R.id.fabNewTask);
        fabNewTask.setOnClickListener { view ->
            var intent = Intent(this, NewTaskActivity::class.java)

            intent.putExtra(NewTaskActivity.LABEL_SELECTED_DATE, mHighlightedDate.time.time);
            startActivityForResult(intent, CODE_NEW_TASK);
        }

        calendarView = findViewById<MCalendarView>(R.id.calendar);
//        calendarView.setMarkedStyle(MarkStyle.BACKGROUND, getColor(R.color.colorPrimaryDark));
        calendarView.setOnDateClickListener(object: OnDateClickListener() {
            override fun onDateClick(view: View?, date: DateData?) {
                if (date == null) return;
                else {
                    Log.d(TAG, "Calendar highlighted " + date.year + "-" + date.month + "-" + date.day);
                    mHighlightedDate.time = DateUtil.getJavaDateFromDateData(date);

                    doDateMarkUnmark();

                    mLastHighlightedDate.time = DateUtil.getJavaDateFromDateData(date);
                    setTaskListByDay(DateUtil.truncateToDay(mHighlightedDate.timeInMillis))
                }
            }
        });

        mTaskList = DataConvert.readJSONFromStorage(this);
        for (taskItem: TaskItem in mTaskList) {
            addToTaskMap(taskItem);
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

            calendarView.markDate(
                    DateUtil.getDateDataFromJavaData(taskItem.dateDeadline)
                    .setMarkStyle(MarkStyle(MarkStyle.DOT, getColor(R.color.md_red_A700)))
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
                addToTaskMap(newTask);

                setTaskListByDay(DateUtil.truncateToDay(mHighlightedDate.timeInMillis))
                mRecyclerTaskListAdapter.notifyItemInserted(mTaskList.size-1);

//                NotificationUtils.setNotification(Calendar.getInstance().timeInMillis+5000, this)

                NotificationUtils.setNotification(this);
            }
        }
    }

    override fun onPause() {
        super.onPause()
        DataConvert.writeJSONToStorage(this, DataConvert.convertToJSONString(mTaskList));
    }

    fun setTaskListByDay(date: Long) {
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

    fun addToTaskMap(taskItem: TaskItem) {
        var date = DateUtil.truncateToDay(taskItem.dateDeadline);
        if (!mTaskByDate.containsKey(date)) {
            mTaskByDate[date] = ArrayList<TaskItem>();
        }
        mTaskByDate[date]?.add(taskItem);
    }

    fun doDateMarkUnmark() {
        var newDate = DateUtil.getDateDataFromJavaData(mHighlightedDate.time);
        var oldDate = DateUtil.getDateDataFromJavaData(mLastHighlightedDate.time);

        //un-mark date with tasks associated before highlighting
        if (mTaskByDate.containsKey(DateUtil.truncateToDay(mHighlightedDate.timeInMillis))) {
            Log.d(TAG, "doDateMarkUnmark: was previously marked");
            calendarView.unMarkDate(newDate);
        }
        calendarView.markDate(newDate.setMarkStyle(MarkStyle(MarkStyle.BACKGROUND, getColor(R.color.colorPrimary))));

        calendarView.unMarkDate(oldDate);
        //re-mark if date had tasks associated
        if (mTaskByDate.containsKey(DateUtil.truncateToDay(mLastHighlightedDate.timeInMillis))) {
            Log.d(TAG, "doDateMarkUnmark: ");
            calendarView.markDate(oldDate.setMarkStyle(MarkStyle(MarkStyle.DOT, getColor(R.color.md_red_A700))));
        }
    }
}