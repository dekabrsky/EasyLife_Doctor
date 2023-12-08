package ru.dekabrsky.feature.notifications.implementation.provider

import android.content.Context
import androidx.core.app.NotificationManagerCompat
import javax.inject.Inject
import javax.inject.Provider

class NotificationManagerCompatProvider @Inject constructor(
    private val context: Context
) : Provider<NotificationManagerCompat> {

    override fun get(): NotificationManagerCompat {
        return NotificationManagerCompat.from(context)
    }
}