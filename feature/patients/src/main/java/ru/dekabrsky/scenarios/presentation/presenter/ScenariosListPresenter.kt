package ru.dekabrsky.scenarios.presentation.presenter

import android.content.DialogInterface
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.scenarios.domain.interactor.DoctorPatientsInteractorImpl
import ru.dekabrsky.scenarios.presentation.mapper.ScenariosUiMapper
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.scenarios.presentation.view.PatientsListView
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import javax.inject.Inject

class ScenariosListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val uiMapper: ScenariosUiMapper,
    private val interactor: DoctorPatientsInteractorImpl
) : BasicPresenter<PatientsListView>(router) {

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

    fun generatePatients(dialog: DialogInterface) {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Пациент добавлен в систему",
                subtitle = "Теперь можно перейти в его карточку",
                mode = BottomSheetMode.LK,
                buttonState = ButtonState("Перейти", {})
            )
        )
//        interactor.generateCode()
//            .observeOn(RxSchedulers.main())
//            .subscribe({
//                viewState.showCodeDialog(dialog, it)
//            }, viewState::showError)
//            .addFullLifeCycle()
    }
}