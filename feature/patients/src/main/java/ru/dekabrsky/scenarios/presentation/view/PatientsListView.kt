package ru.dekabrsky.scenarios.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.common.domain.model.ContactEntity
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface PatientsListView : BasicView {
    fun setItems(items: List<ContactEntity>)
    fun showEmptyLayout()
    fun setLoadingViewVisibility(isVisible: Boolean)
}