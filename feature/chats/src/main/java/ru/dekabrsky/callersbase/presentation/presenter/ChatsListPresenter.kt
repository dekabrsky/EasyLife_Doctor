package ru.dekabrsky.callersbase.presentation.presenter

import ru.dekabrsky.callersbase.domain.interactor.ContactsInteractorImpl
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.callersbase.presentation.mapper.ChatEntityToUiMapper
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.callersbase.presentation.view.ChatsListView
import ru.dekabrsky.callersbase_common.presentation.model.ChatUiModel
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import javax.inject.Inject

class ChatsListPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: ContactsInteractorImpl,
    private val uiMapper: ChatEntityToUiMapper
) : BasicPresenter<ChatsListView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        load()
    }

    private fun load(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ) {
//        interactor.getCallersBases(direction, sortBy)
//            .observeOn(RxSchedulers.main())
//            .map { it.map { entity -> uiMapper.map(entity) } }
//            .subscribe(
//                { dispatchLoading(it) },
//                {
//                    viewState.showError(it, ::load)
//                    viewState.showEmptyLayout()
//                }
//            )
//            .addFullLifeCycle()
        viewState.setChatsList(uiMapper.mapChats())
    }

    fun loadSortByName() = load()

    fun loadSortByDateAsc() = load(Direction.ASC.name, SortVariants.CREATION_DATE.name)

    fun loadSortByDateDesc() = load(Direction.DESC.name, SortVariants.CREATION_DATE.name)

    private fun dispatchLoading(items: List<CallersBaseUiModel>) {
        if (items.isEmpty()) {
            viewState.showEmptyLayout()
        } else {
            viewState.setChatsList(uiMapper.mapChats())
        }
    }

    fun onChatClick(model: ChatUiModel) {
        // переход в чат
    }

}