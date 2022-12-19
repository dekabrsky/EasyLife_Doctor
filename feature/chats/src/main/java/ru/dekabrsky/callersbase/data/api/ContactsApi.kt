package ru.dekabrsky.callersbase.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.*
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ChatResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase.data.model.UserIdNameResponse
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.network.utils.Direction

interface ContactsApi {
    @GET("callers-base/header")
    fun getCallersBases(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("direction") direction: String = Direction.ASC.name,
        @Query("sortBy") sortBy: String = SortVariants.NAME.name
    ): Observable<CallersBaseResponse>

    @GET("callers-base/header/{id}")
    fun getCallersBase(@Path("id") id: Int): Observable<ContentResponse>

    @GET("users/doctors")
    fun getDoctors(): Single<UserIdNameResponse>

    @GET("users/patients")
    fun getPatients(): Single<UserIdNameResponse>

    @GET("users/children")
    fun getChildren(): Single<UserIdNameResponse>

    @POST("chats")
    fun startChat(@Query("companionUserId") userId: Int = 0): Completable

    @GET("chats/users/{companionUserId}")
    fun getChat(@Query("companionUserId") userId: Int = 0): Single<ChatResponse>

    @GET("chats")
    fun getChats(): Single<List<ChatResponse>>

    @POST("chats/messages")
    fun postMessage(@Body userId: Int = 0): Single<ChatResponse>
}