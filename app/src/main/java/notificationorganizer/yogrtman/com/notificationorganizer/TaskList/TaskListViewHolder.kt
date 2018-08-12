package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
import java.text.SimpleDateFormat

class TaskListViewHolder constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mCardView: CardView = itemView.findViewById<CardView>(R.id.cardTask);
    lateinit var mTaskItem: TaskItem;

    fun bindTaskItem(taskItem: TaskItem) {
        mTaskItem = taskItem;

        var txtTaskTitle: TextView = mCardView.findViewById(R.id.txtTaskTitle);
        var txtTaskDescription: TextView = mCardView.findViewById(R.id.txtTaskDescription);
        var txtDateCreation: TextView = mCardView.findViewById(R.id.txtDateCreation);
        var txtDateDeadline: TextView = mCardView.findViewById(R.id.txtDateDeadline);

        txtTaskTitle.setText(taskItem.title);
        txtTaskDescription.setText(taskItem.description);
        txtDateCreation.setText(SimpleDateFormat("yyyy/MM/dd hh:mm").format(taskItem.dateCreation.time));
        txtDateDeadline.setText(SimpleDateFormat("yyyy/MM/dd hh:mm").format(taskItem.dateDeadline.time));
    }
}