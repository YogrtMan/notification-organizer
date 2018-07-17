package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

interface ItemTouchHelperAdapter{

    fun onItemMove(fromPosition: Int, toPosition: Int);

    fun onItemDismiss(position: Int);
}