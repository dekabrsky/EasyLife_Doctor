package ru.dekabrsky.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.mixpanel.android.mpmetrics.MixpanelAPI
import org.json.JSONObject
import javax.inject.Inject


class AnalyticsSender @Inject constructor(
    private val firebaseAnalytics: FirebaseAnalytics,
    private val mixpanelAPI: MixpanelAPI
) {
    fun sendEventScreenView(name: String, content: Map<String, String> = mapOf()) {
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, name)

        val props = JSONObject()
        props.put(FirebaseAnalytics.Param.ITEM_NAME, name)

        content.entries.forEach {
            bundle.putString(it.key, it.value)
            props.put(it.key, it.value)
        }

        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, bundle)
        mixpanelAPI.track(FirebaseAnalytics.Event.SCREEN_VIEW, props)
    }

    fun sendEvent(name: String, content: Map<String, String> = mapOf()) {
        val bundle = Bundle()
        val props = JSONObject()
        content.entries.forEach {
            bundle.putString(it.key, it.value)
            props.put(it.key, it.value)
        }
        firebaseAnalytics.logEvent(name, bundle)
        mixpanelAPI.track(name, props)
    }

    fun setUserId(id: Long) {
        firebaseAnalytics.setUserId(id.toString())
        mixpanelAPI.identify(id.toString())
    }

    fun sendLogin() {
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.LOGIN, Bundle())
        mixpanelAPI.track(FirebaseAnalytics.Event.LOGIN)
    }
}