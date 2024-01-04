package ru.dekabrsky.callersbase.presentation.presenter

import io.reactivex.Single
import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.view.BaseChatsListView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView

abstract class BaseChatListPresenter<T: BaseChatsListView> constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl
) : BasicPresenter<T>(router) {

    protected var isFirstLoading = true

    override fun attachView(view: T) {
        super.attachView(view)
        load()
    }

    abstract fun load()

    protected open fun dispatchLoading(items: List<ChatUiModel>) {
        isFirstLoading = false
        viewState.showEmptyLayout(items.isEmpty())
        if (items.isEmpty().not()) {
            viewState.setChatsList(items)
        }
    }

    fun onChatClick(model: ChatUiModel) {
        if (model.chatIsStarted) {
            navigateToChat(model)
        } else {
            interactor.startChat(model.secondUser.id)
                .observeOn(RxSchedulers.main())
                .withLoadingView(viewState)
                .subscribe({ navigateToChat(model) }, viewState::showError)
                .addFullLifeCycle()
        }
    }

    abstract fun navigateToChat(model: ChatUiModel)
}