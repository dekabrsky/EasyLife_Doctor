package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface CallersBaseDetailsView: BasicView {
    fun setMainData(model: CallersBaseUiModel)
    fun setupVariables(variables: List<String>)
    fun setupDialings(dialings: List<MiniDialingUiModel>)
}