package ru.dekabrsky.scenarios.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel

@AddToEndSingle
interface ScenarioDetailsView : BasicView {
    fun setMainData(model: ScenarioItemUiModel)
    fun setupDialings(dialings: List<MiniDialingUiModel>)
}