package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatFlowCache
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.view.ChatsListView
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.italks.basic.rx.withCustomLoadingViewIf
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class ChatsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl,
    private val uiMapper: ChatEntityToUiMapper,
    private val cache: ChatFlowCache,
    private val loginDataCache: LoginDataCache,
    private val errorHandler: ServerErrorHandler
) : BaseChatListPresenter<ChatsListView>(router, interactor, errorHandler) {

    override fun load() {
         interactor.getChats()
            .subscribeOnIo()
            .withCustomLoadingViewIf(viewState::setLoadingViewVisibility, isFirstLoading)
            .map { chats -> uiMapper.prepareChatsList(chats = chats) }
            .subscribe(::dispatchLoading) { errorHandler.onError(it, viewState) }
            .addFullLifeCycle()
    }

    override fun dispatchLoading(items: List<ChatUiModel>) {
        cache.existingCompanionIds = items.map { it.secondUser.id }
        super.dispatchLoading(items)

        checkHasNotifications(items)
    }

    private fun checkHasNotifications(items: List<ChatUiModel>) {


        loginDataCache.chatId?.toLongOrNull()?.let { id ->
            val chat = items.find { it.chatId == id }
            chat?.let(::onChatClick)

            loginDataCache.chatId = null
        }
    }

    fun onStartChatClick() {
        router.navigateTo(Flows.Chats.SCREEN_CHAT_NEW_CONTACTS)
    }

    override fun navigateToChat(model: ChatUiModel) =
        router.navigateTo(
            Flows.Chats.SCREEN_CHAT_CONVERSATION,
            ChatConversationScreenArgs(model.chatId, model.secondUser)
        )
}