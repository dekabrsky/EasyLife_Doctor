package ru.dekabrsky.callersbase.presentation.presenter

import io.reactivex.Observable
import io.reactivex.rxkotlin.Observables
import org.threeten.bp.LocalDateTime
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.mapper.MessageEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import ru.dekabrsky.callersbase.presentation.view.ChatConversationView
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ChatConversationPresenter @Inject constructor(
    private val interactor: ContactsInteractorImpl,
    private val args: ChatConversationScreenArgs,
    private val mapper: MessageEntityToUiMapper,
    private val router: FlowRouter
) : BasicPresenter<ChatConversationView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMessages()
        viewState.setTitle("Чат с ${args.companion.name}")
    }

    private fun loadMessages() {
        Observable.interval(UPDATING_DELAY, TimeUnit.SECONDS).startWith(0)
            .flatMapSingle { interactor.getChat(args.companion.id) }
            .observeOn(RxSchedulers.main())
            .map(mapper::map)
            .subscribe(viewState::setMessages, viewState::showError)
            .addFullLifeCycle()
    }

    fun postMessage(msg: String) {
        if (msg.isBlank()) {
            viewState.showToast("Введите непустое сообщение")
            return
        }

        interactor.postMessage(args.chatId, msg)
            .observeOn(RxSchedulers.main())
            .subscribe({
                val uiMsg = ChatMessageUiModel(
                    userName = "Я",
                    isMyMessage = true,
                    message = msg,
                    dateTime = mapper.mapMessageTime(LocalDateTime.now())
                )
                viewState.addMessage(uiMsg)
            }, viewState::showError)
            .addFullLifeCycle()
    }

    companion object {
        private const val UPDATING_DELAY = 15L
    }
}