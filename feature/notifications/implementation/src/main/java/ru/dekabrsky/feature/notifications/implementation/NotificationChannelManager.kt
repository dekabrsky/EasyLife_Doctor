package ru.dekabrsky.feature.notifications.implementation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.dekabrsky.feature.notifications.api.R
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import javax.inject.Inject

class NotificationChannelManager @Inject constructor(
    private val notificationManager: NotificationManagerCompat,
    resourceProvider: ResourceProvider
) {

    private val defaultChannel = ChannelInfo(
        id = resourceProvider.getString(R.string.easy_life_notification_channel_id),
        name = resourceProvider.getString(R.string.easy_life_notification_header)
    )

    fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannelsSafety()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannelsSafety() {
        createChannel(defaultChannel)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(channelInfo: ChannelInfo) {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelInfo.id, channelInfo.name, importance).apply {
            enableLights(true)
            enableVibration(true)
            setShowBadge(true)
            lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE
        }

        notificationManager.createNotificationChannel(channel)
    }
}

class ChannelInfo(
    val id: String,
    val name: String
)