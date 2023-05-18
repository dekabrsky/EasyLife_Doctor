package ru.dekabrsky.dialings.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import org.eazegraph.lib.models.ValueLineSeries
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.model.ConcurentUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView
import java.util.*

@AddToEndSingle
interface DialingDetailsView: BasicView {

    fun setupLineChart(series: ValueLineSeries, trendSeries: ValueLineSeries)
    fun setMainData(product: PlainProduct)
    fun setPrecision(precision: Int, delta: Double)
    fun setConcurents(concurents: List<ConcurentUiModel>)
}