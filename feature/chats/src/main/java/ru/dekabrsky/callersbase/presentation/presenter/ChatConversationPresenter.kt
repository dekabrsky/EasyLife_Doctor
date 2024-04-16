package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.analytics.AnalyticsUtils
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.mapper.MessageEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import ru.dekabrsky.callersbase.presentation.view.ChatConversationView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.withLoadingView
import javax.inject.Inject

class ChatConversationPresenter @Inject constructor(
    private val interactor: ContactsInteractorImpl,
    private val args: ChatConversationScreenArgs,
    private val mapper: MessageEntityToUiMapper,
    private val router: FlowRouter,
    private val analyticsSender: AnalyticsSender,
    private val errorHandler: ServerErrorHandler
) : BasicPresenter<ChatConversationView>(router) {

    private val messages = mutableListOf<ChatMessageUiModel>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMessages()
        viewState.setTitle("Чат с ${args.companion.name}")
        AnalyticsUtils.sendScreenOpen(this, analyticsSender)
    }

    private fun loadMessages() {
        interactor.getChat(args.companion.id)
            .subscribeOnIo()
            .withLoadingView(viewState)
            .map(mapper::map)
            .subscribe(::onMessagesLoaded) { errorHandler.onError(it, viewState) }
            .addFullLifeCycle()
    }

    private fun onMessagesLoaded(messages: List<ChatMessageUiModel>) {
        this.messages.addAll(messages)
        updateViewMessages()

        interactor.observeMessagesWs(args.chatId)
            .subscribeOnIo()
            .map { mapper.mapMessage(it, args.companion.name) }
            .subscribe({
//                this.messages.add(it)
//                updateViewMessages()
                       viewState.addMessage(it)
            }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
    }

    private fun updateViewMessages() {
        viewState.setMessages(messages)
    }

    fun postMessage(msg: String) {
        if (msg.isBlank()) {
            viewState.showToast("Введите непустое сообщение")
            return
        }

        interactor.postMessageWs(args.chatId, msg)
//        messages.add(mapper.mapMyMessage(msg))
//        updateViewMessages()
        viewState.addMessage(mapper.mapMyMessage(msg), withClean = true)
    }
}