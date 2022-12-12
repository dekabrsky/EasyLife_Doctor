package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.view.ChatDetailsView
import ru.dekabrsky.dialings_common.domain.interactor.DialingsInteractor
import ru.dekabrsky.dialings_common.presentation.mapper.MiniDialingUiMapper
import ru.dekabrsky.dialings_common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import javax.inject.Inject

class ChatDetailsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val model: CallersBaseEntity,
    private val uiMapper: ChatEntityToUiMapper,
    private val dialingsInteractor: DialingsInteractor,
    private val dialingMapper: MiniDialingUiMapper
) : BasicPresenter<ChatDetailsView>(router) {

    override fun onFirstViewAttach() {
        viewState.setMainData(uiMapper.map(model))
        viewState.setupVariables(model.columns)
        loadDialings()
    }

    fun onDialingClick(dialing: MiniDialingUiModel) {
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