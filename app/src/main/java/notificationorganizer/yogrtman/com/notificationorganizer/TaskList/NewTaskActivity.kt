package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager

class NewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task);
        setSupportActionBar(appBar);

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return AppBarManager.onOptionsItemSelected(item);
    }
}