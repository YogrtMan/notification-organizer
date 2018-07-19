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

class ListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // The last two arguments ensure LayoutParams are inflated properly.

        val rootView: View = inflater.inflate(
                R.layout.fragment_task_list, container, false);

        var recyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList);
        var recyclerTaskListAdapter = TaskListRecyclerViewAdapter(mutableListOf("hello", "bye", "I'm done for today", "1", "2", "3", "4", "5"));
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