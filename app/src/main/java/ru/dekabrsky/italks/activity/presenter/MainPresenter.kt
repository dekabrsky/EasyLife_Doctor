package ru.dekabrsky.italks.activity.presenter

import moxy.MvpPresenter
import moxy.MvpView
import ru.dekabrsky.italks.activity.view.MainView
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.domain.UserType
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val router: FlowRouter,
): BasicPresenter<MainView>() {

    override fun onFirstViewAttach() {
        startFlow(Flows.Login.name, TabsFlowArgs(UserType.PATIENT))
    }

    private fun startFlow(flowName: String, data: Any? = null) {
        router.preSetScreens(Pair(flowName, data))
        router.toggleScreen(flowName, "")
    }

    fun onAttach() = onFirstViewAttach()

    fun toTesterSettings() {
        router.startFlow(Flows.TesterSettings.name)
    }
}