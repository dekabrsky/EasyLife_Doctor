package ru.dekabrsky.italks.di.provider.network

import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import ru.dekabrsky.italks.BuildConfig
import javax.inject.Inject
import javax.inject.Provider

class MixPanelApiProvider @Inject constructor(private val context: Context) : Provider<MixpanelAPI> {
    override fun get() = MixpanelAPI.getInstance(context, BuildConfig.MIXPANEL_TOKEN, true)
}