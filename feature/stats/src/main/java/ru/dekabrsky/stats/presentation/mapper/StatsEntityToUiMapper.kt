package ru.dekabrsky.stats.presentation.mapper

import ru.dekabrsky.stats.domain.model.MainStatsEntity
import ru.dekabrsky.stats.domain.model.SuccessCallsChartEntity
import ru.dekabrsky.stats.presentation.model.ChartPointUiModel
import ru.dekabrsky.stats.presentation.model.MainStatsUiModel
import javax.inject.Inject

class StatsEntityToUiMapper @Inject constructor() {

//    fun mapMainStats(entity: MainStatsEntity) = MainStatsUiModel(
//        averageCallDuration = with (entity.averageCallDuration) {
//            "$hours ч. $minutes мин. $seconds сек."
//        },
//        averageDialingsDuration = with (entity.averageDialingsDuration) {
//            "$hours ч. $minutes мин. $seconds сек."
//        },
//        averageNumberOfCallsPerDialing = entity.averageNumberOfCallsPerDialing.toString(),
//        totalDialings = entity.totalDialings.toString()
//    )

    fun mapMainStats() = MainStatsUiModel(
        averageCallDuration = "1 мин. 15 сек.",
        averageDialingsDuration = "1 ч. 10 мин. 23 сек.",
        averageNumberOfCallsPerDialing = "20",
        totalDialings = "5"
    ) // для демо

//    fun mapLineChart(entities: List<SuccessCallsChartEntity>) = entities.map {
//        entity -> ChartPointUiModel(
//            x = entity.time,
//            y = entity.successCalls
//        )
//    }

    @Suppress("MagicNumber")
    fun mapLineChart() = listOf(
        ChartPointUiModel("12:00", 1),
        ChartPointUiModel("13:00", 3),
        ChartPointUiModel("14:00", 5),
        ChartPointUiModel("15:00", 9),
        ChartPointUiModel("16:00", 8),
        ChartPointUiModel("17:00", 10),
        ChartPointUiModel("18:00", 5)
    ) // для демо
}