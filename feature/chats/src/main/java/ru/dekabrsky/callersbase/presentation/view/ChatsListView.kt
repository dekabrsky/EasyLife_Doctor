package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.easylife.basic.fragments.BasicView

@AddToEndSingle
interface BaseChatsListView: BasicView {
    fun setChatsList(items: List<ChatUiModel>)
    fun showEmptyLayout(isVisible: Boolean)
    fun setLoadingViewVisibility(isVisible: Boolean)
}

interface ChatsListView: BaseChatsListView

interface NewContactsListView: BaseChatsListView