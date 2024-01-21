package ru.dekabrsky.analytics

object AnalyticsUtils {
    fun sendScreenOpen(presenterClass: Any, sender: AnalyticsSender) {
        sender.sendEventScreenView(presenterClass::class.simpleName.orEmpty().replace("Presenter", ""))
    }
}