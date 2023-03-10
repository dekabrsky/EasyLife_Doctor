package com.matthew.calendar.calendar.view.presenter

import com.matthew.calendar.calendar.view.CalendarFlowView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class CalendarFlowPresenter @Inject constructor(
    private val router: FlowRouter
): BasicPresenter<CalendarFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.newRootScreen(Flows.Calendar.SCREEN_CALENDAR)
    }
}