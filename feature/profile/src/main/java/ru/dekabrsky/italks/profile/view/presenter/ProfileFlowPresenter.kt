package ru.dekabrsky.easylife.profile.view.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.profile.view.ProfileFlowView
import javax.inject.Inject

class ProfileFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<ProfileFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Profile.SCREEN_PROFILE)
    }
}