package ru.dekabrsky.feature.notifications.implementation.receiver

import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import main.utils.orZero
import org.threeten.bp.LocalDateTime
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.utils.sendNotification
import java.util.Calendar

class NotificationsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        val id = intent.getLongExtra("NOTIFICATION_ID", 0)

        val extra = intent.getSerializableExtra("NOTIFICATION")

        val notification = extra as? NotificationEntity

        val dateTime = intent.getLongExtra("NOTIFICATION_TIME", 0)

        val calendar: Calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTime

        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 1)

        Log.d("onReceive: ", notification?.uid.orZero().toString())

        val notifyIntent = Intent(context, this::class.java)

        notifyIntent.putExtras(
            Bundle().apply {
                putLong("NOTIFICATION_TIME", calendar.timeInMillis)
                putLong("NOTIFICATION_ID", id)
                putSerializable("NOTIFICATION", notification)
            }
        )

        val notifyPendingIntent = PendingIntent.getBroadcast(
            context,
            id.toInt(),
            notifyIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        if (notification?.weekDays?.contains(LocalDateTime.now().dayOfWeek) == true) {
            notificationManager.sendNotification(
                messageTitle = "Сработало напоминание",
                messageBody = "Пора зайти в EasyLife",
                applicationContext = context,
                notification = notification
            )
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, notifyPendingIntent)

    }
}