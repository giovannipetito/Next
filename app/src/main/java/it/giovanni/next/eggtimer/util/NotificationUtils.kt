package it.giovanni.next.eggtimer.util

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import it.giovanni.next.MainActivity
import it.giovanni.next.R
import it.giovanni.next.eggtimer.receiver.SnoozeReceiver

private const val NOTIFICATION_ID = 0
private const val REQUEST_CODE = 0
private const val FLAGS = PendingIntent.FLAG_ONE_SHOT

// Extension function to send messages (GIVEN)
fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {

    // Create the content intent for the notification, which launches this activity
    val contentIntent = Intent(applicationContext, MainActivity::class.java)

    /*
    Create PendingIntent
    You created the intent, but the notification is displayed outside your app. To make an intent
    work outside your app, you need to create a new PendingIntent.
    PendingIntent grants rights to another application or the system to perform an operation on
    behalf of your application. A PendingIntent itself is simply a reference to a token maintained
    by the system describing the original data used to retrieve it. This means that, even if its
    owning application's process is killed, the PendingIntent itself will remain usable from other
    processes it has been given to. In this case, the system will use the pending intent to open
    the app on behalf of you, regardless of whether or not the timer app is running.
    */

    val contentPendingIntent = PendingIntent.getActivity(
            applicationContext,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
    )

    // Add snooze action
    val snoozeIntent = Intent(applicationContext, SnoozeReceiver::class.java)
    val snoozePendingIntent: PendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            REQUEST_CODE,
            snoozeIntent,
            FLAGS
    )

    // Add style
    val eggImage = BitmapFactory.decodeResource(applicationContext.resources, R.drawable.egg_cooked)
    val bigPicStyle = NotificationCompat.BigPictureStyle()
        .bigPicture(eggImage)
        .bigLargeIcon(null)

    /*
    Get an instance of NotificationCompat.Builder
    Starting with API level 26, all notifications must be assigned to a channel.
    Notification Channels are a way to group notifications. By grouping together similar types
    of notifications, developers and users can control all of the notifications in the channel.
    Once a channel is created, it can be used to deliver any number of notifications.
    */
    val builder = NotificationCompat.Builder(applicationContext, applicationContext.getString(R.string.egg_notification_channel_id))

        // Add style to builder
        .setContentTitle(applicationContext.getString(R.string.notification_title)) // Set title
        .setContentText(messageBody) // Set text
        .setSmallIcon(R.drawable.egg_cooked) // Set icon
        .setContentIntent(contentPendingIntent) // Set content intent
        .setAutoCancel(true)
        .setStyle(bigPicStyle)
        .setLargeIcon(eggImage)
        .addAction(R.drawable.egg_icon, applicationContext.getString(R.string.snooze), snoozePendingIntent) //  Add snooze action
        .setPriority(NotificationCompat.PRIORITY_HIGH) // Set priority

    /*
    Call notify
    NOTIFICATION_ID represents the current notification instance and is needed for updating
    or canceling this notification. Since your app will only have one active notification at
    a given time, you can use the same ID for all your notifications.
    */
    notify(NOTIFICATION_ID, builder.build())
}

// Cancel all notifications
fun NotificationManager.cancelNotifications() {
    cancelAll()
}