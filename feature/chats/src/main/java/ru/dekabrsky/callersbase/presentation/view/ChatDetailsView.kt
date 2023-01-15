package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface ChatDetailsView: BasicView {
    fun setMainData(model: CallersBaseUiModel)
    fun setupVariables(variables: List<String>)
    fun setupDialings(dialings: List<MiniDialingUiModel>)
}