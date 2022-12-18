package ru.dekabrsky.italks.profile.view.presenter

import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.profile.view.ProfileFlowView
import javax.inject.Inject

class ProfileFlowPresenter @Inject constructor(
    private val router: FlowRouter
) : BasicPresenter<ProfileFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Profile.SCREEN_PROFILE)
    }
}