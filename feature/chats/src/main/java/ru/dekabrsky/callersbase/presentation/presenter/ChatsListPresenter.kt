package ru.dekabrsky.callersbase.presentation.presenter

import io.reactivex.Single
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.view.ChatsListView
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.login.data.repository.LoginRepository
import javax.inject.Inject

class ChatsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl,
    private val uiMapper: ChatEntityToUiMapper,
    private val loginInteractor: LoginRepository
) : BasicPresenter<ChatsListView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ) {
//        loginInteractor.getCurrentUser()
//            .observeOn(RxSchedulers.main())
//            .subscribe({
//                when (it.role) {
//                    UserType.DOCTOR -> viewState.setChatsList(uiMapper.mapDoctorChats())
//                    UserType.PARENT -> viewState.setChatsList(uiMapper.mapParentChats())
//                    else -> viewState.setChatsList(listOf())
//                }
//            }, viewState::showError)
//            .addFullLifeCycle()

        Single.zip(
            interactor.getDoctors(),
            interactor.getPatients(),
            interactor.getChildren(),
            interactor.getChats()
        ) { doctors, patients, children, chats -> doctors + patients + children to chats }
            .observeOn(RxSchedulers.main())
            .map { (users, chats) -> uiMapper.prepareChatsList(users, chats) }
            .subscribe(::dispatchLoading, viewState::showError)
            .addFullLifeCycle()

//        interactor.getChats()
//            .observeOn(RxSchedulers.main())
//            .map { list -> list.map { uiMapper.mapChat(it) } }
//            .subscribe(viewState::setChatsList, viewState::showError)
//            .addFullLifeCycle()

    }

    fun loadSortByName() = load()

    fun loadSortByDateAsc() = load(Direction.ASC.name, SortVariants.CREATION_DATE.name)

    fun loadSortByDateDesc() = load(Direction.DESC.name, SortVariants.CREATION_DATE.name)

    private fun dispatchLoading(items: List<ChatUiModel>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.setChatsList(items)
        }
    }

    fun onChatClick(model: ChatUiModel) {
        if (model.chatIsStarted) {
            router.navigateTo(
                Flows.Chats.SCREEN_CHAT_CONVERSATION,
                ChatConversationScreenArgs(model.chatId, model.secondUser)
            )
        } else {
            interactor.startChat(model.secondUser.id)
                .observeOn(RxSchedulers.main())
                .subscribe( { router.navigateTo(Flows.Chats.SCREEN_CHAT_CONVERSATION) }, viewState::showError)
                .addFullLifeCycle()
        }
    }

}