package notificationorganizer.yogrtman.com.notificationorganizer.Notification

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import android.os.Build
import java.util.*
import android.app.NotificationChannel
import notificationorganizer.yogrtman.com.notificationorganizer.R
import notificationorganizer.yogrtman.com.notificationorganizer.TaskList.TaskActivity


class NotificationService : IntentService("DeadlineNotificationService") {

    companion object {
        const val CHANNEL_ID = "NotificationOrganizer_Deadline_Channel"
        const val CHANNEL_NAME = "NotificationOrganizer Deadlines"
    }


    private lateinit var mNotification: Notification
    private val mNotificationId: Int = 1000

    fun createChannel() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            // Create the NotificationChannel, but only on API 26+ because
            // the NotificationChannel class is new and not in the support library

            val context = this.applicationContext
            val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationChannel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableVibration(true)
            notificationChannel.setShowBadge(true)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = getColor(R.color.colorPrimary)
            notificationChannel.description = "Notifies you of upcoming deadlines"
            notificationChannel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override fun onHandleIntent(intent: Intent?) {
        //Create Channel
        createChannel()

        var timestamp: Long = 0
        var title: String = resources.getString(R.string.app_name)

        if (intent != null && intent.extras != null) {
            timestamp = intent.extras!!.getLong("timestamp")
            title = intent.getStringExtra("TASK_TITLE")
        }

        if (timestamp > 0) {

            val context = this.applicationContext
            var notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val notifyIntent = Intent(this, TaskActivity::class.java)

            val message = "Deadline approaching"

            notifyIntent.putExtra("title", title)
            notifyIntent.putExtra("message", message)
            notifyIntent.putExtra("notification", true)

            notifyIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = timestamp


            val pendingIntent = PendingIntent.getActivity(context, 0, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            val res = this.resources
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


                mNotification = Notification.Builder(this, CHANNEL_ID)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_priority_high)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setContentTitle(title)
                        .setStyle(Notification.BigTextStyle()
                                .bigText(message))
                        .setContentText(message).build()
            } else {

                mNotification = Notification.Builder(this)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.drawable.ic_priority_high)
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.mipmap.ic_launcher))
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentTitle(title)
                        .setStyle(Notification.BigTextStyle()
                                .bigText(message))
                        .setSound(uri)
                        .setContentText(message).build()

            }



            notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            // mNotificationId is a unique int for each notification that you must define
            notificationManager.notify(mNotificationId, mNotification)
        }


    }
}