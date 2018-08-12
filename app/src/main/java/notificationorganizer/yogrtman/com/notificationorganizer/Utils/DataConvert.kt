package notificationorganizer.yogrtman.com.notificationorganizer.Utils

import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskItem
import org.json.JSONArray
import org.json.JSONObject
import java.util.*

class DataConvert {
    companion object {
        fun getTaskItemListFromJSON(raw: String) : MutableList<TaskItem>{
            var tasks: ArrayList<TaskItem> = ArrayList<TaskItem>();

            var jsonTaskArr: JSONArray = JSONArray(JSONObject(raw).getJSONArray("tasks"));

            for(i : Int in 0..jsonTaskArr.length()) {
                var jsonTask = jsonTaskArr.getJSONObject(i);

                tasks.add(TaskItem(
                        jsonTask.getString("title"),
                        jsonTask.getString("description"),
                        jsonTask.getInt("priority"),
                        Date(jsonTask.getLong("dateCreation")),
                        Date(jsonTask.getLong("dateDeadline"))

                ));
            }
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

                jsonTaskArr.put(jsonTask.toString());
            }

            return JSONObject().put("tasks", jsonTaskArr.toString()).toString();
        }

        fun writeJSONToStorage(raw: String) {
            //write raw to internal storage
        }

        fun getTempTaskList(): MutableList<TaskItem> {
            var mTaskList = ArrayList<TaskItem>();

            mTaskList.add(TaskItem(
                    "Take out trash",
                    "",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time)
            ));

            mTaskList.add(TaskItem(
                    "Groceries",
                    "eggs, steak, asparagus",
                    0,
                    Date(Calendar.getInstance().time.time-1010000000000),
                    Date(Calendar.getInstance().time.time)
            ));

            mTaskList.add(TaskItem(
                    "135 Assignment",
                    "Start early, review chapter on CRT",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time)
            ));

            mTaskList.add(TaskItem(
                    "Finish side project",
                    "hurry up and finish it you shit",
                    0,
                    Date(Calendar.getInstance().time.time-1000000),
                    Date(Calendar.getInstance().time.time)
            ));

            mTaskList.add(TaskItem(
                    "Send greyhound refund email",
                    "",
                    0,
                    Date(Calendar.getInstance().time.time-201000000000),
                    Date(Calendar.getInstance().time.time)
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