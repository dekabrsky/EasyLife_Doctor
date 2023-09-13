package ru.dekabrsky.scenarios.presentation.view

import android.content.DialogInterface
import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel

@AddToEndSingle
interface PatientsListView : BasicView {
    fun setItems(items: List<ScenarioItemUiModel>)
    fun showEmptyLayout()
}