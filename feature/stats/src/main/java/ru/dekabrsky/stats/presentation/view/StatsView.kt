package ru.dekabrsky.stats.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.stats.presentation.model.ChartPointUiModel
import ru.dekabrsky.stats.presentation.model.MainStatsUiModel

@AddToEndSingle
interface StatsView: BasicView {
    fun showMainStats(model: MainStatsUiModel)
    fun showLineChart(mapLineChart: List<ChartPointUiModel>)
    fun showPieChart()
}