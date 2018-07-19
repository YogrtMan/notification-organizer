package notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskListPage

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper

class ItemTouchHelperCallback constructor (var mItemTouchAdapter: ItemTouchHelperAdapter): ItemTouchHelper.Callback() {

    /*
    specifies supported gestures
    */
    override fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or(ItemTouchHelper.DOWN);
        val swipeFlags = ItemTouchHelper.LEFT or (ItemTouchHelper.RIGHT);
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    override fun onMove(recyclerView: RecyclerView?, viewHolder: RecyclerView.ViewHolder?, target: RecyclerView.ViewHolder?): Boolean {
        if (viewHolder == null || target == null) {
            return false;
        }

        mItemTouchAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition);
        return true;
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder?, direction: Int) {
        if (viewHolder != null) {
            mItemTouchAdapter.onItemDismiss(viewHolder.adapterPosition);
        }
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true;
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true;
    }
}