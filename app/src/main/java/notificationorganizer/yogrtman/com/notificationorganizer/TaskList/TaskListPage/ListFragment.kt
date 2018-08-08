package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskListPage

import android.os.Bundle
import android.support.v4.app.*
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
import java.util.*

class ListFragment : Fragment() {
    var mTaskList: MutableList<TaskItem> = ArrayList<TaskItem>();

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // The last two arguments ensure LayoutParams are inflated properly.

        val rootView: View = inflater.inflate(
                R.layout.fragment_task_list, container, false);

        //temp values
        mTaskList.add(TaskItem(
            "Take out trash",
                "",
                0,
                Date(Calendar.getInstance().time.time-10000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Groceries",
                "eggs, steak, asparagus",
                0,
                Date(Calendar.getInstance().time.time-100000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "135 Assignment",
                "Start early, review chapter on CRT",
                0,
                Date(Calendar.getInstance().time.time-1000),
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
                Date(Calendar.getInstance().time.time-20000),
                Date(Calendar.getInstance().time.time)
        ));

        mTaskList.add(TaskItem(
                "Wake up from bed",
                "pretty necessary to do stuff, and I need one more item",
                0,
                Date(Calendar.getInstance().time.time),
                Date(Calendar.getInstance().time.time)
        ));


        var recyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList);

        var recyclerTaskListAdapter = TaskListRecyclerViewAdapter(mTaskList);

        recyclerTaskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            adapter = recyclerTaskListAdapter;
        }

        var itemTouchHelperCallback: ItemTouchHelper.Callback = ItemTouchHelperCallback(recyclerTaskListAdapter);
        var itemTouchHelper: ItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerTaskList);

        return rootView
    }
}