package ru.dekabrsky.dialings.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import moxy.viewstate.strategy.alias.OneExecution
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.dialings.domain.model.PlainProduct
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface DialingsListView : BasicView {
    fun setItems(items: List<PlainProduct>)
    fun showEmptyLayout()
    fun hideEmptyLayout()

    @OneExecution
    fun showNoConnectionDialog()
    fun setFilter(chipId: Int)

    @OneExecution
    fun showRunNowDialog(id: Int)

    fun setCityMarkerVisibility(notEmpty: Boolean)
    fun setTypeMarkerVisibility(notEmpty: Boolean)
}