package ru.dekabrsky.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject


class AnalyticsSender @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics
) {
    fun sendEventScreenView(name: String, content: Map<String, String> = mapOf()) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)
        content.entries.forEach {
            bundle.putString(it.key, it.value)
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
    }

    fun sendEvent(name: String, content: Map<String, String> = mapOf()) {
        val bundle = Bundle()
        content.entries.forEach {
            bundle.putString(it.key, it.value)
        }
        firebaseAnalytics.logEvent(name, bundle)
    }

    fun setUserId(id: Long) {
        firebaseAnalytics.setUserId(id.toString())
    }

    fun sendLogin() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle())
    }
}