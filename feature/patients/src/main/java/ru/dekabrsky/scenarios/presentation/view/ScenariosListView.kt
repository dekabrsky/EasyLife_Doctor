package ru.dekabrsky.scenarios.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel

@AddToEndSingle
interface ScenariosListView : BasicView {
    fun setItems(items: List<ScenarioItemUiModel>)
    fun showEmptyLayout()
}