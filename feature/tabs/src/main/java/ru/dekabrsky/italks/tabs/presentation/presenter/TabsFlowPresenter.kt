package ru.dekabrsky.italks.tabs.presentation.presenter

import ru.dekabrsky.callersbase_common.presentation.model.CallersBasesFlowScreenArgs
import ru.dekabrsky.dialings_common.presentation.model.DialingsFlowScreenArgs
import ru.dekabrsky.italks.basic.navigation.Flow
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.feature.tabs.R
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.view.TabsFlowView
import javax.inject.Inject

class TabsFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<TabsFlowView>(router) {

    private var currentFlow: Flow? = null
    private val screensChain by lazy { LinkedHashSet<Flow>() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.preSetScreens(
            Flows.CallersBase.name to CallersBasesFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.CallersBase.SCREEN_BASES_LIST
            ),
            Flows.Scenarios.name to null,
            Flows.Dialing.name to DialingsFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.Dialing.SCREEN_DIALINGS_LIST
            ),
            Flows.Stats.name to null
        )
        toggleScreen(
            Flows.Dialing,
            DialingsFlowScreenArgs(
                Scopes.SCOPE_APP,
                Flows.Dialing.SCREEN_DIALINGS_LIST
            )
        )
    }

    fun onTabSelect(itemId: Int) {
        when (itemId) {
            R.id.callersBase -> toggleScreen(
                Flows.CallersBase,
                CallersBasesFlowScreenArgs(
                    Scopes.SCOPE_APP,
                    Flows.CallersBase.SCREEN_BASES_LIST
                )
            )
            R.id.call -> toggleScreen(
                Flows.Dialing,
                DialingsFlowScreenArgs(
                    Scopes.SCOPE_APP,
                    Flows.Dialing.SCREEN_DIALINGS_LIST
                )
            )
            R.id.scenarios -> toggleScreen(Flows.Scenarios)
            R.id.stats -> toggleScreen(Flows.Stats)
        }
    }

    private fun selectTab(flow: Flow) {
        when (flow) {
            Flows.Dialing -> viewState.setSelectedCallTab()
            Flows.Scenarios -> viewState.setSelectedScenariosTab()
        }
    }

    private fun toggleScreen(flow: Flow, data: Any? = null) {
        if (currentFlow != flow) {
            router.toggleScreen(flow.name, currentFlow?.name.orEmpty(), data)
            updateScreensChain(currentFlow, flow)
            currentFlow = flow
        } else {
            router.sendNewData(flow.name, data)
        }
    }

    private fun updateScreensChain(prevScreen: Flow?, nextScreen: Flow) {
        prevScreen ?: return
        screensChain.add(prevScreen)
        if (screensChain.contains(nextScreen)) screensChain.remove(nextScreen)
    }
}