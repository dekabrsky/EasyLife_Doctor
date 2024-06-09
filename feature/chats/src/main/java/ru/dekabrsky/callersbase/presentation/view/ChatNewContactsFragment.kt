package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import main.utils.gone
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.presentation.presenter.ChatNewContactsPresenter
import ru.dekabrsky.easylife.scopes.Scopes
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