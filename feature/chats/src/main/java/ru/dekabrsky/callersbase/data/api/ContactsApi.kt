package ru.dekabrsky.callersbase.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ChatResponse
import ru.dekabrsky.callersbase.data.model.ChatsListResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase.data.model.UsersListIdNameResponse
import ru.dekabrsky.easylife.basic.network.utils.Direction
import ru.dekabrsky.easylife.basic.network.utils.SortVariants

interface ContactsApi {
    @GET("callers-base/header")
    fun getCallersBases(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("direction") direction: String = Direction.ASC.name,
        @Query("sortBy") sortBy: String = SortVariants.NAME.name
    ): Observable<CallersBaseResponse>

    @GET("callers-base/header/{id}")
    fun getCallersBase(@Path("id") id: Long): Observable<ContentResponse>

    @GET("users/doctors")
    fun getDoctors(): Single<UsersListIdNameResponse>

    @GET("users/patients")
    fun getPatients(): Single<UsersListIdNameResponse>

    @GET("users/children")
    fun getChildren(): Single<UsersListIdNameResponse>

    @GET("users/parents")
    fun getParents(): Single<UsersListIdNameResponse>

    @POST("chats/users/{companionUserId}")
    fun startChat(@Path("companionUserId") userId: Long): Completable

    @GET("chats/users/{companionUserId}")
    fun getChat(@Path("companionUserId") userId: Long): Single<ChatResponse>

    @GET("chats")
    fun getChats(): Single<ChatsListResponse>
}