package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.feature.notifications.implementation.presentation.model.NotificationEditUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView
import java.util.Date

@AddToEndSingle
interface NotificationEditView: BasicView {
    fun setTime(time: String)
    fun setNotesFields(uiModel: NotificationEditUiModel)
    @OneExecution
    fun showTimePicker(hour: Int, minute: Int)
}