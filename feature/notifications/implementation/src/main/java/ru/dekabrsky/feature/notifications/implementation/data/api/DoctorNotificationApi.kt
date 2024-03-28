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

interface DoctorNotificationApi {
    @GET("notifications/users/{userId}")
    fun getNotifications(@Path("userId") userId: Long): Single<List<NotificationResponse>>

    @POST("notifications/users/{userId}")
    fun postNotification(@Body request: NotificationRequest, @Path("userId") userId: Long): Completable

    @DELETE("notifications/{id}/users/{userId}")
    fun deleteNotification(@Path("id") id: Long, @Path("userId") userId: Long): Completable

    @PUT("notifications/{id}/users/{userId}")
    fun putNotification(
        @Path("id") id: Long,
        @Path("userId") userId: Long,
        @Body request: NotificationRequest,
    ): Completable
}