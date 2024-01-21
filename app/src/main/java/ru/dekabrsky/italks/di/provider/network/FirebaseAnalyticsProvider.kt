package ru.dekabrsky.italks.di.provider.network

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject
import javax.inject.Provider

class FirebaseAnalyticsProvider @Inject constructor(private val context: Context) : Provider<FirebaseAnalytics> {
    override fun get() = FirebaseAnalytics.getInstance(context)
}