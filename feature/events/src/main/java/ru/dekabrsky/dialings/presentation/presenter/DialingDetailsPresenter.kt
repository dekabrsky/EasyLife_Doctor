package ru.dekabrsky.dialings.presentation.presenter

import android.view.View
import androidx.core.content.ContextCompat
import org.eazegraph.lib.models.ValueLinePoint
import org.eazegraph.lib.models.ValueLineSeries
import org.threeten.bp.LocalDateTime
import ru.dekabrsky.common.domain.interactor.ContactsInteractor
import ru.dekabrsky.common.presentation.model.ChatsFlowScreenArgs
import ru.dekabrsky.dialings.presentation.mapper.DialingListUiMapper
import ru.dekabrsky.dialings.presentation.view.DialingDetailsView
import ru.dekabrsky.common.domain.interactor.DialingsInteractor
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.dialings.presentation.model.ConcurentUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.italks.basic.di.IntWrapper
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import java.util.*
import javax.inject.Inject

class DialingDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val product: PlainProduct
) : BasicPresenter<DialingDetailsView>(router) {

    override fun onFirstViewAttach() {
        viewState.setMainData(product)
        viewState.setPrecision(product.pred, (product.pred - product.avg) / product.avg * 100)
        viewState.setConcurents(product.positions.map { ConcurentUiModel(it.firm, it.price) })
        viewState.setupLineChart(mapChart(), mapTrendChart())
    }

    private fun mapChart(): ValueLineSeries {
        val series = ValueLineSeries()
        product.history.takeLast(50).forEachIndexed { index, i ->
            series.addPoint(ValueLinePoint("$index", i.toFloat()))
        }
        return series
    }

    private fun mapTrendChart(): ValueLineSeries {
        val series = ValueLineSeries()
        product.trend.takeLast(50).forEachIndexed { index, i ->
            series.addPoint(ValueLinePoint("$index", i))
        }
        return series
    }

}