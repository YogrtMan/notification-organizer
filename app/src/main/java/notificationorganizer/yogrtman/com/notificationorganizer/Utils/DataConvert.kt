package notificationorganizer.yogrtman.com.notificationorganizer.Utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
import org.json.JSONArray
import org.json.JSONObject
import java.io.File
import java.io.FileNotFoundException
import java.util.*

class DataConvert {

    companion object {
        val TAG = "DataConvert";

        val TASK_LIST_FILE_NAME: String = "task-list-json.json";

        fun getTaskItemListFromJSON(raw: String) : MutableList<TaskItem>{
            var tasks: ArrayList<TaskItem> = ArrayList<TaskItem>();

            var jsonTaskArr: JSONArray = JSONObject(raw).getJSONArray("tasks");

            for(i : Int in 0..jsonTaskArr.length()-1) {
                var jsonTask = jsonTaskArr.getJSONObject(i);

                tasks.add(TaskItem(
                        jsonTask.getString("title"),
                        jsonTask.getString("description"),
                        jsonTask.getInt("priority"),
                        Date(jsonTask.getLong("dateCreation")),
                        Date(jsonTask.getLong("dateDeadline"))

                ));
            }
            Log.d(TAG, "Read tasks: " + tasks);
            return tasks;
        }

        fun convertToJSONString(taskList: MutableList<TaskItem>): String {
            var jsonTaskArr = JSONArray();

            for(task:TaskItem in taskList) {
                var jsonTask = JSONObject();
                jsonTask.put("title", task.title);
                jsonTask.put("description", task.description);
                jsonTask.put("priority", task.priority);
                jsonTask.put("dateCreation", task.dateCreation.time);
                jsonTask.put("dateDeadline", task.dateDeadline.time);

                jsonTaskArr.put(jsonTask);
                Log.d(TAG, "Created task json " + jsonTask.toString());
            }
            var json = JSONObject().put("tasks", jsonTaskArr);
            return json.toString();
        }

        fun writeJSONToStorage(context: Context, raw: String) {
            //write raw to internal storage
            var taskListFile = File(context.filesDir, TASK_LIST_FILE_NAME);
            taskListFile.writeText(raw);
        }

        fun readJSONFromStorage(context: Context): MutableList<TaskItem> {
            var taskList: MutableList<TaskItem>;
            try {
                taskList = getTaskItemListFromJSON(File(context.filesDir, TASK_LIST_FILE_NAME).readText());
            }
            catch (e: FileNotFoundException) {
                Toast.makeText(context, "No saved task list found", Toast.LENGTH_SHORT).show();
                taskList = getTempTaskList();
            }
            return taskList;
        }

        fun getTempTaskList(): MutableList<TaskItem> {
            var mTaskList = ArrayList<TaskItem>();

            mTaskList.add(TaskItem(
                    "Take out trash",
                    "",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time-239847)
            ));

            mTaskList.add(TaskItem(
                    "Groceries",
                    "eggs, steak, asparagus",
                    0,
                    Date(Calendar.getInstance().time.time-1010000000000),
                    Date(Calendar.getInstance().time.time-69879835)
            ));

            mTaskList.add(TaskItem(
                    "135 Assignment",
                    "Start early, review chapter on CRT",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time-84902)
            ));

            mTaskList.add(TaskItem(
                    "Finish side project",
                    "hurry up and finish it you shit",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time-49883)
            ));

            mTaskList.add(TaskItem(
                    "Send greyhound refund email",
                    "",
                    0,
                    Date(Calendar.getInstance().time.time-201000000000),
                    Date(Calendar.getInstance().time.time-49238479)
            ));

            mTaskList.add(TaskItem(
                    "Wake up from bed",
                    "pretty necessary to do stuff, and I need one more item",
                    0,
                    Date(Calendar.getInstance().time.time),
                    Date(Calendar.getInstance().time.time)
            ));
            return mTaskList;
        }
    }
}