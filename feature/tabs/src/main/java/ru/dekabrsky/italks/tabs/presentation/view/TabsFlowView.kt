package ru.dekabrsky.italks.tabs.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface TabsFlowView: MvpView {
    fun setSelectedCallTab()
    fun setSelectedScenariosTab()
}
