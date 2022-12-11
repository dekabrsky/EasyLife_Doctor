package ru.dekabrsky.italks.testerSettings.presentation.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface TesterSettingsView : MvpView {
    fun setupServerAddress(baseEndpoint: String)
    fun restartApp()
}