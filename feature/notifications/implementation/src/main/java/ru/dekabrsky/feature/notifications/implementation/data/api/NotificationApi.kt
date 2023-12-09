package ru.dekabrsky.feature.notifications.implementation.data.api

import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationRequest
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationResponse

interface NotificationApi {
    @GET("notifications")
    fun getNotifications(): Single<List<NotificationResponse>>

    @POST("notifications")
    fun postNotification(@Body request: NotificationRequest): Single<NotificationResponse>

    @DELETE("notifications/{id}")
    fun deleteNotification(@Path("id") id: Int): Completable

    @PUT("notifications/{id}")
    fun putNotification(@Path("id") id: Int, @Body request: NotificationRequest): Completable
}