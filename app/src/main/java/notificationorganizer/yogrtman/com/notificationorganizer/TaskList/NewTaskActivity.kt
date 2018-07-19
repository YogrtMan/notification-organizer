package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.DatePicker
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.TimePickerFragment

class NewTaskActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task);
        setSupportActionBar(appBar);

        btnPickTime.setOnClickListener(View.OnClickListener {
            var fragment: DialogFragment = TimePickerFragment();
            fragment.show(supportFragmentManager, "timePicker");
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return AppBarManager.onOptionsItemSelected(item);
    }
}