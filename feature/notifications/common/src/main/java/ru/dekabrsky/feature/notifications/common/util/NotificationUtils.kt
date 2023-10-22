package ru.dekabrsky.feature.notifications.common.util

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import ru.dekabrsky.feature.notifications.api.R

//TODO Проверить, работу нескольких уведомлений подряд с разными id
private const val NOTIFICATION_ID = 0

fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context) {
    val builder = NotificationCompat.Builder(
        applicationContext,
        applicationContext.getString(R.string.easy_life_notification_channel_id)
    )

    builder.setSmallIcon(R.drawable.ic_logo)
        .setContentTitle(applicationContext.getString(R.string.easy_life_notification_header))
        .setContentText(messageBody)

    notify(NOTIFICATION_ID, builder.build())
}