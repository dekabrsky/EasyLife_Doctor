package ru.dekabrsky.dialings.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.easylife.basic.fragments.BasicView
import java.util.Date

@AddToEndSingle
interface DialingDetailsView: BasicView {
    fun setMainData(model: DialingUiModel)
    fun setupProgress(progressInt: Int, progress: String, details: String)
    fun setupTime(startTime: String, canEdit: Boolean)
    fun setupPieChart()
    fun setupLineChart()
    fun showDatePicker()
    fun showTimePicker(date: Date)
    fun showNewStartDate(newValue: String)
    fun setRunNowVisibility(isVisible: Boolean)
    fun showRunNowDialog()
}