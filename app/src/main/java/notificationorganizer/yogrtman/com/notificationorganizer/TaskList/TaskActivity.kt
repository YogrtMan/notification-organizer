package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.Menu
import android.widget.CalendarView
import android.widget.Toast
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.DataConvert
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
    lateinit var mRecyclerTaskList: RecyclerView;
    lateinit var mRecyclerTaskListAdapter: TaskListRecyclerViewAdapter;

    lateinit var calendarView: CalendarView;
    lateinit var fabNewTask: FloatingActionButton;

    var mHighlightedDate: Calendar = Calendar.getInstance();

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

        calendarView = findViewById<CalendarView>(R.id.calendar);
        calendarView.setOnDateChangeListener {view, year, month, dayOfMonth ->
            Log.d(TAG, "Calendar highlighted " + year + "-" + month + "-" + dayOfMonth);
            mHighlightedDate.set(Calendar.YEAR, year);
            mHighlightedDate.set(Calendar.MONTH, month);
            mHighlightedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        }

        mTaskList = ArrayList<TaskItem>();
        mRecyclerTaskList = findViewById<RecyclerView>(R.id.recyclerTaskList);
        mRecyclerTaskListAdapter = TaskListRecyclerViewAdapter(mTaskList);

        mRecyclerTaskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            adapter = mRecyclerTaskListAdapter;
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

            }
        }
    }

    //this should really be onStop but let's leave it like this cuz it's easier to test
    override fun onPause() {
        super.onPause();
        DataConvert.writeJSONToStorage(this, DataConvert.convertToJSONString(mTaskList));
    }

    //and this should be onStart...
    override fun onResume() {
        super.onResume();
        mTaskList = DataConvert.readJSONFromStorage(this);
        if (mRecyclerTaskListAdapter == null)
            mRecyclerTaskListAdapter = TaskListRecyclerViewAdapter(mTaskList);
        mRecyclerTaskListAdapter.notifyDataSetChanged();
    }
}