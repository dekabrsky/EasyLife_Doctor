package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.stats.domain.interactor.StatsInteractor
import ru.dekabrsky.stats.presentation.mapper.StatsEntityToUiMapper
import ru.dekabrsky.stats.presentation.view.StatsView
import javax.inject.Inject

class StatsPresenter @Inject constructor(
    private val interactor: StatsInteractor,
    private val uiMapper: StatsEntityToUiMapper
): BasicPresenter<StatsView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        loadMainData()
//        loadLineChart()
//        viewState.showPieChart()
    }

    private fun loadLineChart() {
//        interactor.getChartData()
//            .observeOn(RxSchedulers.main())
//            .map { uiMapper.mapLineChart(it) }
//            .subscribe({ viewState.showLineChart(it) }, { viewState.showError(it) })
//            .addFullLifeCycle()
        viewState.showLineChart(uiMapper.mapLineChart())
    }

    private fun loadMainData() {
//        interactor.getMainStats()
//            .observeOn(RxSchedulers.main())
//            .map { uiMapper.mapMainStats(it) }
//            .subscribe({ viewState.showMainStats(it) }, { viewState.showError(it) })
//            .addFullLifeCycle()
        viewState.showMainStats(uiMapper.mapMainStats())
    }

}