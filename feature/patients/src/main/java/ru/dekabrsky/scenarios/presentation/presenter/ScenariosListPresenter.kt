package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.domain.interactor.ScenariosInteractorImpl
import ru.dekabrsky.scenarios.presentation.mapper.ScenariosUiMapper
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.scenarios.presentation.view.ScenariosListView
import javax.inject.Inject

class ScenariosListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uiMapper: ScenariosUiMapper,
    private val interactor: ScenariosInteractorImpl
) : BasicPresenter<ScenariosListView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ) {
//        interactor.getScenarios(direction, sortBy)
//            .observeOn(RxSchedulers.main())
//            .map { it.map { entity -> uiMapper.map(entity) } }
//            .subscribe(
//                { dispatchLoading(it) },
//                {
//                    viewState.showError(it, ::load)
//                    viewState.showEmptyLayout()
//                }
//            )
//            .addFullLifeCycle()
        dispatchLoading(listOf(uiMapper.map(), uiMapper.map(), uiMapper.map(), uiMapper.map() , uiMapper.map()))
        //viewState.showEmptyLayout()
    }

    fun loadSortByName() = load()

    fun loadSortByDateAsc() = load(Direction.ASC.name, SortVariants.CREATION_DATE.name)

    fun loadSortByDateDesc() = load(Direction.DESC.name, SortVariants.CREATION_DATE.name)

    private fun dispatchLoading(items: List<ScenarioItemUiModel>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.setItems(items)
        }
    }

    fun onItemClick(model: ScenarioItemUiModel) {
        router.navigateTo(Flows.Patients.SCREEN_SCENARIO_DETAILS, model)
    }

    fun startGame() {
        router.startFlow(Flows.Game.name)
    }
}