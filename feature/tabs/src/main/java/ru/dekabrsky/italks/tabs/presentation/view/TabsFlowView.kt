package ru.dekabrsky.easylife.tabs.presentation.view

import androidx.annotation.MenuRes
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface TabsFlowView: MvpView {
    fun setSelectedCallTab()
    fun setSelectedScenariosTab()
    fun setTabsByRole(@MenuRes menu: Int)
}
