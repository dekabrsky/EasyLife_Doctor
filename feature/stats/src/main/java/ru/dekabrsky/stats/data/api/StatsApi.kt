package ru.dekabrsky.stats.data.api

import io.reactivex.Observable
import retrofit2.http.GET
import ru.dekabrsky.stats.data.model.MainStatsResponse
import ru.dekabrsky.stats.data.model.SuccessCallsChartResponse

interface StatsApi {
    @GET("statistic/common")
    fun getMainStats(): Observable<MainStatsResponse>

    @GET("statistic/success-calls-chart")
    fun getCallersBase(): Observable<List<SuccessCallsChartResponse>>
}