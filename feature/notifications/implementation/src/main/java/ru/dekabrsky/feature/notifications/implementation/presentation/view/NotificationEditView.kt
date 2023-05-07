package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.italks.basic.fragments.BasicView
import java.util.Date

@AddToEndSingle
interface NotificationEditView: BasicView {
    @OneExecution
    fun showTimePicker()
    fun setTime(time: String)
}