package ru.dekabrsky.scenarios.presentation.presenter

import ru.dekabrsky.common.domain.interactor.DialingsInteractor
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.common.presentation.mapper.TakingMedicationsUiMapper
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.scenarios.presentation.view.PatientDetailsView
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import javax.inject.Inject

class PatientDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val model: ContactEntity,
    private val dialingsInteractor: DialingsInteractor,
    private val takingMedicationsMapper: TakingMedicationsUiMapper
) : BasicPresenter<PatientDetailsView>(router) {

    override fun onFirstViewAttach() {
        viewState.setMainData(model)
        loadTakingMedications()
    }

    @Suppress("EmptyFunctionBlock")
    fun onDialingClick(dialing: TakingMedicationsUiModel) {

    }

    private fun loadTakingMedications() =
        viewState.setupTakingMedications(takingMedicationsMapper.mapMock())
//        dialingsInteractor.getDialingsByCallersBase(model.id)
//            .subscribeOnIo()
//            .map { it.map { entity -> dialingMapper.map(entity) } }
//            .subscribe(
//                {
//                    if (it.isNotEmpty()) {
//                        viewState.setupDialings(it)
//                    }
//                },
//                { viewState.showError(it) }
//            )
//            .addFullLifeCycle()


}