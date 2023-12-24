package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import main.utils.setBoolVisibility
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentChatsListBinding
import ru.dekabrsky.callersbase.presentation.adapter.ChatsListAdapter
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.presenter.BaseChatListPresenter
import ru.dekabrsky.callersbase.presentation.presenter.ChatsListPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

abstract class BaseChatsListFragment<T: BaseChatListPresenter<*>>: BasicFragment(), BaseChatsListView {

    override val layoutRes: Int = R.layout.fragment_chats_list

    private val chatsAdapter by lazy { ChatsListAdapter(presenter::onChatClick) }

    protected val binding by viewBinding(FragmentChatsListBinding::bind)

    abstract var presenter: T

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.basesCardsList.adapter = chatsAdapter
    }

    override fun setChatsList(items: List<ChatUiModel>) {
        chatsAdapter.updateItems(items)
    }

    override fun showEmptyLayout(isVisible: Boolean) {
        binding.emptyLayout.setBoolVisibility(isVisible)
    }

    override fun setLoadingViewVisibility(isVisible: Boolean) {
        binding.loadingText.setBoolVisibility(isVisible)
    }

    override fun onBackPressed() = presenter.onBackPressed()

    fun setNavBarVisibility(isVisible: Boolean) {
        (parentFragment as ChatFlowFragment).setNavBarVisibility(isVisible)
    }
}