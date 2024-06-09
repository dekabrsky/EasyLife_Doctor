package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.easylife.basic.fragments.BasicView

@AddToEndSingle
interface ChatDetailsView: BasicView {
    fun setMainData(model: CallersBaseUiModel)
    fun setupVariables(variables: List<String>)
    fun setupDialings(dialings: List<TakingMedicationsUiModel>)
}