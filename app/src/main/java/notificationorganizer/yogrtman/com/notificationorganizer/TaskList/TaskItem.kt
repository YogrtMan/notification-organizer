package notificationorganizer.yogrtman.com.notificationorganizer.TaskList

import java.util.*

class TaskItem {
    companion object {
        val PROGRESS_NOT_STARTED: Int = 0;
        val PROGRESS_IN_PROGRESS: Int = 1;
        val PROGRESS_DONE: Int = 2;
    }

    lateinit var title: String;
    lateinit var description: String;
    //details unclear, treat as descending priority 0-10 for now
    var priority: Int = 10;
    lateinit var dateCreation: Date;
    lateinit var dateDeadline: Date;

    var progressStatus: Int = PROGRESS_NOT_STARTED;


    constructor(
            title: String,
            description: String,
            priority: Int,
            dateCreation: Date,
            dateDeadline: Date) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dateCreation = dateCreation;
        this.dateDeadline = dateDeadline;
    }
}