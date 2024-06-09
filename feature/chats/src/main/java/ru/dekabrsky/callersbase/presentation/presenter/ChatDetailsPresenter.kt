package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.view.ChatDetailsView
import ru.dekabrsky.common.domain.interactor.DialingsInteractor
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.common.presentation.mapper.TakingMedicationsUiMapper
import ru.dekabrsky.common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.scopes.Scopes
import javax.inject.Inject

class ChatDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val model: CallersBaseEntity,
    private val uiMapper: ChatEntityToUiMapper,
    private val dialingsInteractor: DialingsInteractor,
    private val dialingMapper: TakingMedicationsUiMapper
) : BasicPresenter<ChatDetailsView>(router) {

    override fun onFirstViewAttach() {
        viewState.setMainData(uiMapper.map(model))
        viewState.setupVariables(model.columns)
        loadDialings()
    }

    fun onDialingClick(dialing: TakingMedicationsUiModel) {
        router.startFlow(
            Flows.Events.name,
            EventsFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.Events.SCREEN_DIALING_DETAILS,
                dialing.id
            )
        )
    }

    private fun loadDialings() =
        dialingsInteractor.getDialingsByCallersBase(model.id)
            .subscribeOnIo()
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