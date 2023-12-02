package ru.dekabrsky.feature.notifications.implementation.data.provider

import retrofit2.Retrofit
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import javax.inject.Inject
import javax.inject.Provider

class NotificationApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<NotificationApi> {

    override fun get(): NotificationApi = retrofit.create(NotificationApi::class.java)

}