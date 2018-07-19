package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskListPage

interface ItemTouchHelperAdapter{

    fun onItemMove(fromPosition: Int, toPosition: Int);

    fun onItemDismiss(position: Int);
}