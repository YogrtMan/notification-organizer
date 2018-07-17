package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.View
import notificationorganizer.yogrtman.com.notificationorganizer.R

class TaskListViewHolder constructor (itemView: View) : RecyclerView.ViewHolder(itemView) {
    var cardView: CardView = itemView.findViewById<CardView>(R.id.cardTask);
}