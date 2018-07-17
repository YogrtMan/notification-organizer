package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import notificationorganizer.yogrtman.com.notificationorganizer.R

class TaskListRecyclerViewAdapter constructor (var taskList: MutableList<String>) : RecyclerView.Adapter<TaskListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskListViewHolder {

        var view = LayoutInflater.from(parent.context).inflate(R.layout.card_task, parent, false);
        return (TaskListViewHolder(view));
    }

    override fun getItemCount(): Int {
        return (taskList.size);
    }

    override fun onBindViewHolder(holder: TaskListViewHolder, position: Int) {
        holder.cardView.findViewById<TextView>(R.id.txtTaskTitle).setText(taskList[position]);
    }
}