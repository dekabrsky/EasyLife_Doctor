package ru.dekabrsky.stats.domain.interactor

import ru.dekabrsky.stats.data.repository.StatsRepository
import javax.inject.Inject

class StatsInteractor @Inject constructor(
    private val repository: StatsRepository
) {
    fun getMainStats() = repository.getMainStats()

    fun getChartData() = repository.getChart()
}