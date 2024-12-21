package ru.dekabrsky.feature.notifications.implementation.presentation.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.feature.notifications.implementation.presentation.view.EditRestOfPillsView
import ru.dekabrsky.feature.notifications.implementation.presentation.view.RestOfPillsView
import javax.inject.Inject

class EditRestOfPillsPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<EditRestOfPillsView>(router) {
}