package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.app.DatePickerDialog
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.TimePickerFragment
import java.text.DateFormat
import java.util.*

class NewTaskActivity : AppCompatActivity() {
    val TAG: String = "NewTasKActivity";

    companion object {
        val LABEL_SELECTED_DATE: String = "DATE";
    }

    lateinit var mDate: Date;

    lateinit var btnPickTime: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task);
        setSupportActionBar(appBar);

        mDate = Date(intent.getLongExtra(LABEL_SELECTED_DATE, 0));
        Log.d(TAG, "Date: " + mDate.toString());

        btnPickTime = findViewById<Button>(R.id.btnPickTime);
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