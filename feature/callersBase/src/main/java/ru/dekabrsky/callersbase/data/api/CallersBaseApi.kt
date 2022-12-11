package ru.dekabrsky.callersbase.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.network.utils.Direction

interface CallersBaseApi {
    @GET("callers-base/header")
    fun getCallersBases(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("direction") direction: String = Direction.ASC.name,
        @Query("sortBy") sortBy: String = SortVariants.NAME.name
    ): Observable<CallersBaseResponse>

    @GET("callers-base/header/{id}")
    fun getCallersBase(@Path("id") id: Int): Observable<ContentResponse>
}