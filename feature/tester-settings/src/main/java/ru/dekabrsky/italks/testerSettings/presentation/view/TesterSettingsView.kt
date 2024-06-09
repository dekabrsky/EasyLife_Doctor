package ru.dekabrsky.easylife.testerSettings.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface TesterSettingsView : MvpView {
    fun setupServerAddress(baseEndpoint: String)
    fun restartApp()
}