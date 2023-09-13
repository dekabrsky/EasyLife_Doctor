package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface ChatsListView: BasicView {
    fun setChatsList(items: List<ChatUiModel>)
    fun showEmptyLayout()
}