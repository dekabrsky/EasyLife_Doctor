package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.feature.notifications.implementation.presentation.view.RestOfPillsView
import javax.inject.Inject

class RestOfPillsPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<RestOfPillsView>(router) {
    fun onAddPilClick() {
        router.navigateTo(Flows.Notifications.SCREEN_EDIT_REST_OF_PILLS)
    }
}