package ru.dekabrsky.callersbase.presentation.presenter

import io.reactivex.Single
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatFlowCache
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.view.NewContactsListView
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.rx.withCustomLoadingViewIf
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import javax.inject.Inject

class ChatNewContactsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl,
    private val uiMapper: ChatEntityToUiMapper,
    private val cache: ChatFlowCache,
    private val loginDataCache: LoginDataCache,
    private val errorHandler: ServerErrorHandler
) : BaseChatListPresenter<NewContactsListView>(router, interactor, errorHandler) {

    private val source = when (loginDataCache.currentUserData?.role) {
        UserType.PATIENT ->
            Single.zip(
                interactor.getDoctors(),
                interactor.getParents()
            ) { doctors, parents -> doctors + parents }
        UserType.PARENT ->
            Single.zip(
                interactor.getDoctors(),
                interactor.getChildren()
            ) { doctors, children -> doctors + children }
        UserType.DOCTOR ->
            Single.zip(
                interactor.getPatients(),
                interactor.getParents()
            ) { patients, parents -> patients + parents }
        UserType.CHILD, null ->
            Single.just(listOf())
    }

    override fun load() {
        source
            .subscribeOnIo()
            .withCustomLoadingViewIf(viewState::setLoadingViewVisibility, isFirstLoading)
            .map { users -> uiMapper.prepareChatsList(users = users.filter { it.id !in cache.existingCompanionIds }) }
            .subscribe(::dispatchLoading) { errorHandler.onError(it, viewState) }
            .addFullLifeCycle()
    }

    override fun navigateToChat(model: ChatUiModel) =
        router.replaceScreen(
            Flows.Chats.SCREEN_CHAT_CONVERSATION,
            ChatConversationScreenArgs(model.chatId, model.secondUser)
        )
}