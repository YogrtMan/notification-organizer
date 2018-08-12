////Flagged for delete
//
//package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskListPage
//
//import android.os.Bundle
//import android.support.v4.app.*
//import android.support.v7.widget.LinearLayoutManager
//import android.support.v7.widget.RecyclerView
//import android.support.v7.widget.helper.ItemTouchHelper
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import notificationorganizer.yogrtman.com.notificationorganizer.R
//import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
//import java.util.*
//
//class ListFragment : Fragment() {
//    var mTaskList: MutableList<TaskItem> = ArrayList<TaskItem>();
//    lateinit var mRecyclerTaskList: RecyclerView;
//    lateinit var mRecyclerTaskListAdapter: TaskListRecyclerViewAdapter;
//
//    override fun onCreateView(inflater: LayoutInflater,
//                              container: ViewGroup?,
//                              savedInstanceState: Bundle?): View {
//        // The last two arguments ensure LayoutParams are inflated properly.
//
//        val rootView: View = inflater.inflate(
//                R.layout.fragment_task_list, container, false);
//
//        mRecyclerTaskList = rootView.findViewById<RecyclerView>(R.id.recyclerTaskList);
//        mRecyclerTaskListAdapter = TaskListRecyclerViewAdapter(mTaskList);
//
//        mRecyclerTaskList.apply {
//            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//            adapter = mRecyclerTaskListAdapter;
//        }
//
//        var itemTouchHelperCallback: ItemTouchHelper.Callback = ItemTouchHelperCallback(mRecyclerTaskListAdapter);
//        var itemTouchHelper: ItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback);
//        itemTouchHelper.attachToRecyclerView(mRecyclerTaskList);
//
//        return rootView
//    }
//
//    fun setTaskList(taskList: MutableList<TaskItem>) {
//        mTaskList = taskList;
//    }
//}