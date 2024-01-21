package ru.dekabrsky.callersbase.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface ChatConversationView: BasicView {
    fun setMessages(messages: List<ChatMessageUiModel>)
    fun addMessage(uiMsg: ChatMessageUiModel, withClean: Boolean = false)
    fun setTitle(title: String)
}