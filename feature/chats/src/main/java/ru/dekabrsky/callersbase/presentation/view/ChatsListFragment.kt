package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.presentation.presenter.ChatsListPresenter
import ru.dekabrsky.easylife.scopes.Scopes.SCOPE_FLOW_CHATS
import toothpick.Toothpick

class ChatsListFragment : BaseChatsListFragment<ChatsListPresenter>(), ChatsListView {

    @InjectPresenter
    override lateinit var presenter: ChatsListPresenter

    @ProvidePresenter
    fun providePresenter(): ChatsListPresenter {
        return Toothpick.openScopes(SCOPE_FLOW_CHATS, scopeName)
            .getInstance(ChatsListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        setNavBarVisibility(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.toolbar.setTitle(R.string.callers_bases_title)
        binding.startChat.setOnClickListener { presenter.onStartChatClick() }

        binding.emptyLayoutMessage.setText(R.string.no_chats)
    }

    companion object {
        fun newInstance() = ChatsListFragment()
    }
}