package notificationorganizer.yogrtman.com.notificationorganizer.Notification

import android.content.Context
import android.support.v4.app.NotificationCompat
import android.app.NotificationManager
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskActivity
import android.support.v4.app.NotificationManagerCompat




class NotificationUtils {
    companion object {
        val CHANNEL_ID_DEADLINE: String = "NotificationOrganizerDeadlineChannel"
        val CHANNEL_ID_PROMPT: String = "NotificationOrganizerPromptChannel"

        fun createDeadlineNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.notification_channel_deadline_name)
                val description = context.getString(R.string.notification_channel_deadline_description)
                val importance = NotificationManager.IMPORTANCE_HIGH

                val channel = NotificationChannel(CHANNEL_ID_DEADLINE, name, importance)

                channel.description = description
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createPromptNotificationChannel(context: Context) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val name = context.getString(R.string.notification_channel_prompt_name)
                val description = context.getString(R.string.notification_channel_prompt_description)
                val importance = NotificationManager.IMPORTANCE_HIGH

                val channel = NotificationChannel(CHANNEL_ID_PROMPT, name, importance)

                channel.description = description
                // Register the channel with the system; you can't change the importance
                // or other notification behaviors after this
                val notificationManager = context.getSystemService(NotificationManager::class.java)
                notificationManager.createNotificationChannel(channel)
            }
        }

        fun createNotificationChannels(context: Context) {
            createDeadlineNotificationChannel(context)
            createPromptNotificationChannel(context)
        }

        fun setNotification(context: Context) {
            val intent = Intent(context, TaskActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)

            val builder = NotificationCompat.Builder(context, NotificationUtils.CHANNEL_ID_DEADLINE)
                    .setSmallIcon(R.drawable.ic_priority_high)
                    .setContentTitle("My notification")
                    .setContentText("Hello World!")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)

            showNotification(context, builder, 5)
        }

        fun showNotification(context: Context, builder: NotificationCompat.Builder, id: Int) {
            val notificationManager = NotificationManagerCompat.from(context)

            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(id, builder.build())
        }
    }
}