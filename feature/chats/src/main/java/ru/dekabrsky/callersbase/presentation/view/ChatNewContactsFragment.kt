package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import main.utils.gone
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentChatsListBinding
import ru.dekabrsky.callersbase.presentation.adapter.ChatsListAdapter
import ru.dekabrsky.callersbase.presentation.model.ChatUiModel
import ru.dekabrsky.callersbase.presentation.presenter.ChatNewContactsPresenter
import ru.dekabrsky.callersbase.presentation.presenter.ChatsListPresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class ChatNewContactsFragment : BaseChatsListFragment<ChatNewContactsPresenter>(), NewContactsListView {

    @InjectPresenter
    override lateinit var presenter: ChatNewContactsPresenter

    @ProvidePresenter
    fun providePresenter(): ChatNewContactsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_CHATS, scopeName)
            .getInstance(ChatNewContactsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        setNavBarVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setTitle(R.string.start_chat_title)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }

        binding.startChat.gone()
        binding.emptyLayoutMessage.setText(R.string.no_contacts)
    }


    companion object {
        fun newInstance() = ChatNewContactsFragment()
    }
}