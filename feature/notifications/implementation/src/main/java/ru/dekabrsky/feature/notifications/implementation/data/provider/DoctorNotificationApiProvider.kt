package ru.dekabrsky.feature.notifications.implementation.data.provider

import retrofit2.Retrofit
import ru.dekabrsky.feature.notifications.implementation.data.api.DoctorNotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import javax.inject.Inject
import javax.inject.Provider

class DoctorNotificationApiProvider @Inject constructor(private val retrofit: Retrofit) :
    Provider<DoctorNotificationApi> {

    override fun get(): DoctorNotificationApi = retrofit.create(DoctorNotificationApi::class.java)

}