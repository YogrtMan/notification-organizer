package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_task_view.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager

//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf

class TaskActivity : AppCompatActivity() {
    lateinit var taskViewPagerAdapter: TaskViewPagerAdapter;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_view)
        setSupportActionBar(appBar);

        taskViewPagerAdapter = TaskViewPagerAdapter(supportFragmentManager);
        pagerTaskView.adapter = taskViewPagerAdapter;
        tabTaskView.setupWithViewPager(pagerTaskView);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

}