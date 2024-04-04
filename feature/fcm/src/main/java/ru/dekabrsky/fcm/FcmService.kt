package ru.dekabrsky.fcm

import android.app.NotificationManager
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.dekabrsky.feature.notifications.common.utils.sendNotification

class FcmService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        val notificationManager = ContextCompat.getSystemService(
            baseContext,
            NotificationManager::class.java
        ) as NotificationManager

        notificationManager.sendNotification(
            messageTitle = message.notification?.title.orEmpty(),
            messageBody = message.notification?.body.orEmpty(),
            applicationContext = baseContext,
            data = message.data
        )
    }
}