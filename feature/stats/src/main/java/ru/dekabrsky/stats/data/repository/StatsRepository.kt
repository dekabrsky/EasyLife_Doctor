package ru.dekabrsky.stats.data.repository

import io.reactivex.Observable
import ru.dekabrsky.stats.data.api.StatsApi
import ru.dekabrsky.stats.data.mapper.StatsResponseToEntityMapper
import ru.dekabrsky.stats.domain.model.MainStatsEntity
import ru.dekabrsky.stats.domain.model.SuccessCallsChartEntity
import javax.inject.Inject

class StatsRepository @Inject constructor(
    private val api: StatsApi,
    private val mapper: StatsResponseToEntityMapper
) {
    fun getMainStats(): Observable<MainStatsEntity> =
        api.getMainStats().map { mapper.mapMainStats(it) }

    fun getChart(): Observable<List<SuccessCallsChartEntity>> =
        api.getCallersBase().map { it.map { mapper.mapLineChart(it) } }
}