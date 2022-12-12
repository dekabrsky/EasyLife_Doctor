package ru.dekabrsky.scenarios.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel

@AddToEndSingle
interface ScenarioDetailsView : BasicView {
    fun setMainData(model: ScenarioItemUiModel)
    fun setupDialings(dialings: List<MiniDialingUiModel>)
}