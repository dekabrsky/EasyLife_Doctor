package ru.dekabrsky.feature.notifications.common.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat
import ru.dekabrsky.feature.notifications.api.R
import ru.dekabrsky.feature.notifications.common.util.sendNotification

class NotificationsReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Toast.makeText(context, context.getText(R.string.easy_life_first_push_message), Toast.LENGTH_SHORT).show()

        val notificationManager = ContextCompat.getSystemService(
            context,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            context.getText(R.string.easy_life_first_push_message).toString(),
            context
        )

    }

}