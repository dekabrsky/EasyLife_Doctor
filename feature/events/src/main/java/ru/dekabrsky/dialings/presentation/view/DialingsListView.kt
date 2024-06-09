package ru.dekabrsky.dialings.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.easylife.basic.fragments.BasicView

@AddToEndSingle
interface DialingsListView : BasicView {
    fun setItems(items: List<DialingUiModel>)
    fun showEmptyLayout()
    fun hideEmptyLayout()

    @OneExecution
    fun showNoConnectionDialog()
    fun setFilter(chipId: Int)

    @OneExecution
    fun showRunNowDialog(id: Int)
}