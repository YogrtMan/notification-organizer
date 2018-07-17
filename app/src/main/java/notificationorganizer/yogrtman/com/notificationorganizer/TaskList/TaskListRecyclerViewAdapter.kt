package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import notificationorganizer.yogrtman.com.notificationorganizer.R
import java.util.*


class TaskListRecyclerViewAdapter constructor (var mTaskList: MutableList<String>) : RecyclerView.Adapter<TaskListViewHolder>(), ItemTouchHelperAdapter {
    /*
    override ItemTouchHelperAdapter interface
     */
    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(mTaskList, i, i + 1)
            }
        }
        else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(mTaskList, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
//        return true
    }

    override fun onItemDismiss(position: Int) {
        mTaskList.removeAt(position);
        notifyItemRemoved(position);
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false);
        return (TaskListViewHolder(view));
    }

    override fun getItemCount(): Int {
        return (mTaskList.size);
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.mCardView.findViewById<TextView>(R.id.txtTaskTitle).setText(mTaskList[position]);
    }
}