package ru.dekabrsky.stats.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import ru.dekabrsky.stats.data.model.MainStatsResponse
import ru.dekabrsky.stats.data.model.SuccessCallsChartResponse
import ru.dekabrsky.stats.domain.model.DurationEntity
import ru.dekabrsky.stats.domain.model.MainStatsEntity
import ru.dekabrsky.stats.domain.model.SuccessCallsChartEntity
import java.time.LocalDateTime
import javax.inject.Inject

class StatsResponseToEntityMapper @Inject constructor() {
    fun mapMainStats(response: MainStatsResponse) = MainStatsEntity(
        averageCallDuration = with(response.averageCallDuration) {
            DurationEntity(hours, minutes, seconds)
        },
        averageDialingsDuration = with(response.averageDialingsDuration) {
            DurationEntity(hours, minutes, seconds)
        },
        averageNumberOfCallsPerDialing = response.averageNumberOfCallsPerDialing,
        totalDialings = response.totalDialings
    )

    fun mapLineChart(response: SuccessCallsChartResponse) = SuccessCallsChartEntity(
        date = Instant.ofEpochMilli(response.date).atZone(ZoneId.systemDefault()).toLocalDateTime(),
        successCalls = response.successCalls,
        time = response.time
    )
}
