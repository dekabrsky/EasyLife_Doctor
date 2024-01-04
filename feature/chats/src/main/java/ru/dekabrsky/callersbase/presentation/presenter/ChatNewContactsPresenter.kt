package ru.dekabrsky.callersbase.presentation.presenter

import io.reactivex.Single
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatFlowCache
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.view.ChatsListView
import ru.dekabrsky.callersbase.presentation.view.NewContactsListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withCustomLoadingViewIf
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.basic.rx.withLoadingViewIf
import ru.dekabrsky.italks.flows.Flows
import javax.inject.Inject

class ChatNewContactsPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl,
    private val uiMapper: ChatEntityToUiMapper,
    private val cache: ChatFlowCache
) : BaseChatListPresenter<NewContactsListView>(router, interactor) {

    override fun load() {
        Single.zip(
            interactor.getDoctors(),
            interactor.getPatients(),
            interactor.getChildren()
        ) { doctors, patients, children -> doctors + patients + children }
            .observeOn(RxSchedulers.main())
            .withCustomLoadingViewIf(viewState::setLoadingViewVisibility, isFirstLoading)
            .map { users -> uiMapper.prepareChatsList(users = users.filter { it.id !in cache.existingCompanionIds }) }
            .subscribe(::dispatchLoading, viewState::showError)
            .addFullLifeCycle()
    }

    override fun navigateToChat(model: ChatUiModel) =
        router.replaceScreen(
            Flows.Chats.SCREEN_CHAT_CONVERSATION,
            ChatConversationScreenArgs(model.chatId, model.secondUser)
        )
}