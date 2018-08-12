package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.util.Calendar
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
import android.widget.TimePicker
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_new_task.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.TimePickerFragment
import java.text.DateFormat
import java.time.format.DateTimeFormatter
import java.util.*

class NewTaskActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {

    val TAG: String = "NewTaskActivity";

    companion object {
        val LABEL_SELECTED_DATE: String = "DATE";
    }

    var mHour: Int = 0;
    var mMinute: Int = 0;

    lateinit var btnPickTime: Button;
    lateinit var btnTaskComplete: Button;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_task);
        setSupportActionBar(appBar);

        btnPickTime = findViewById<Button>(R.id.btnPickTime);
        btnPickTime.setOnClickListener(View.OnClickListener {
            var fragment: DialogFragment = TimePickerFragment();
            fragment.show(supportFragmentManager, "timePicker");
        });

        btnTaskComplete = findViewById<Button>(R.id.btnTaskComplete);
        btnTaskComplete.setOnClickListener(View.OnClickListener {
            var result: Intent = Intent();
            result.putExtra(TaskActivity.LABEL_NEW_TASK_TITLE, etxtTaskTitle.text.toString());
            result.putExtra(TaskActivity.LABEL_NEW_TASK_DESCRIPTION, etxtTaskDescription.text.toString());
            result.putExtra(TaskActivity.LABEL_NEW_TASK_HOUR, mHour);
            result.putExtra(TaskActivity.LABEL_NEW_TASK_MINUTE, mMinute);

            setResult(TaskActivity.CODE_NEW_TASK, result);
            finish();
        })

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return AppBarManager.onOptionsItemSelected(item);
    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        Log.d(TAG, "NewTask time set " + hourOfDay + ":" + minute);
        mHour = hourOfDay;
        mMinute = minute;

        btnPickTime.setText("" + mHour + ":" + mMinute);
    }
}