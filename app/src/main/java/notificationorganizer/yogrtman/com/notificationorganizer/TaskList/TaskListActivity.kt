package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_dummy.*
import kotlinx.android.synthetic.main.activity_task_list.*
import kotlinx.android.synthetic.main.toolbar_app_action_bar.*
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.Utils.AppBarManager

//https://medium.com/@ipaulpro/drag-and-swipe-with-recyclerview-b9456d2b1aaf

class TaskListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)
        setSupportActionBar(appBar);

        var recyclerTaskList = findViewById<RecyclerView>(R.id.recyclerTaskList);
        recyclerTaskList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
            adapter = TaskListRecyclerViewAdapter(mutableListOf("hello", "bye", "I'm done for today"));
        }

        findViewById<FloatingActionButton>(R.id.fabNewTask).setOnClickListener { view ->
            var intent = Intent(this, NewTaskActivity::class.java)
            startActivity(intent);
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return AppBarManager.onCreateOptionsMenu(menuInflater, menu);
    }

}