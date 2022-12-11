package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface CallersBaseListView: BasicView {
    fun setItems(items: List<CallersBaseUiModel>)
    fun showEmptyLayout()
}