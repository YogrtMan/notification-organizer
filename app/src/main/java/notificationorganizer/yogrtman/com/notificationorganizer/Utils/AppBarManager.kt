package notificationorganizer.yogrtman.com.notificationorganizer.Utils

import android.content.Context
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import notificationorganizer.yogrtman.com.notificationorganizer.R

class AppBarManager {
    companion object {
        fun initializeAppBar(context: Context, appBar: Toolbar) {

        }

        fun onCreateOptionsMenu(menuInflater: MenuInflater, menu: Menu?): Boolean {
            menuInflater.inflate(R.menu.menu_task_list, menu);
            return true;
        }

        fun onOptionsItemSelected(item: MenuItem?): Boolean {
            if (item == null)
                return false;

            return when (item.itemId) {
                else -> {
                    return false;
                }
            }
        }
    }

}