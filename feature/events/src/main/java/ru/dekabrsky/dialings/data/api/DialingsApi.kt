package ru.dekabrsky.dialings.data.api

import io.reactivex.Completable
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import ru.dekabrsky.dialings.data.model.DialingResultResponse
import ru.dekabrsky.dialings.data.model.DialingsResponse
import ru.dekabrsky.easylife.basic.network.utils.Direction
import ru.dekabrsky.easylife.basic.network.utils.SortVariants

interface DialingsApi {
    @GET("dialing")
    fun getDialings(
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("direction") direction: String = Direction.ASC.name,
        @Query("sortBy") sortBy: String = SortVariants.NAME.name,
        @Query("status") status: String? = null
    ): Observable<DialingsResponse>

    @GET("dialing/callers-base/{callers_base_id}")
    fun getDialingsByCallersBase(@Path("callers_base_id") baseId: Int):
            Observable<List<DialingResultResponse>>

    @GET("dialing/{id}/result/common")
    fun getDialingsById(@Path("id") id: Int): Observable<DialingResultResponse>

    @POST("dialing/scheduled/{id}/start")
    fun startDialingById(@Path("id") id: Int): Completable
}