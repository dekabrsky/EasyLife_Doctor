package ru.dekabrsky.dialings.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface FilterListView: BasicView {
    fun setList(list: List<String>)
}