package ru.dekabrsky.stats.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.domain.UserType
import ru.dekabrsky.login.data.repository.LoginRepository
import ru.dekabrsky.stats.domain.interactor.StatsInteractor
import ru.dekabrsky.stats.presentation.mapper.StatsEntityToUiMapper
import ru.dekabrsky.stats.presentation.view.StatsView
import javax.inject.Inject

class StatsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: StatsInteractor,
    private val uiMapper: StatsEntityToUiMapper,
    private val loginInteractor: LoginRepository
): BasicPresenter<StatsView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loginInteractor.getCurrentUser()
            .observeOn(RxSchedulers.main())
            .subscribe({
                viewState.showMyInfo(it)
                if (it.role == UserType.PARENT) {
                    viewState.showChildInfo()
                }
            }, viewState::showError)
            .addFullLifeCycle()
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

    fun onLogoutClick() {
        router.newRootFlow(Flows.Login.name)
    }

}