package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.common.domain.interactor.DialingsInteractor
import ru.dekabrsky.common.presentation.mapper.MiniDialingUiMapper
import ru.dekabrsky.common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.scenarios.presentation.view.ScenarioDetailsView
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import javax.inject.Inject

class ScenarioDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val model: ScenarioItemUiModel,
    private val dialingsInteractor: DialingsInteractor,
    private val dialingMapper: MiniDialingUiMapper
) : BasicPresenter<ScenarioDetailsView>(router) {

    override fun onFirstViewAttach() {
        viewState.setMainData(model)
        loadDialings()
    }

    @Suppress("EmptyFunctionBlock")
    fun onDialingClick(dialing: MiniDialingUiModel) {

    }

    private fun loadDialings() =
        dialingsInteractor.getDialingsByCallersBase(model.id)
            .observeOn(RxSchedulers.main())
            .map { it.map { entity -> dialingMapper.map(entity) } }
            .subscribe(
                {
                    if (it.isNotEmpty()) {
                        viewState.setupDialings(it)
                    }
                },
                { viewState.showError(it) }
            )
            .addFullLifeCycle()


}