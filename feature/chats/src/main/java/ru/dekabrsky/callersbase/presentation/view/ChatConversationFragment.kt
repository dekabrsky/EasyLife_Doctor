package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentChatConversationBinding
import ru.dekabrsky.callersbase.presentation.adapter.ChatMessagesAdapter
import ru.dekabrsky.callersbase.presentation.model.ChatConversationScreenArgs
import ru.dekabrsky.callersbase.presentation.model.ChatMessageUiModel
import ru.dekabrsky.callersbase.presentation.presenter.ChatConversationPresenter
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
import toothpick.Toothpick

class ChatConversationFragment: BasicFragment(), ChatConversationView {
    override val layoutRes: Int
        get() = R.layout.fragment_chat_conversation

    private var args: ChatConversationScreenArgs? = null

    private val adapter by lazy { ChatMessagesAdapter() }

    private val binding by viewBinding(FragmentChatConversationBinding::bind)

    @InjectPresenter
    lateinit var presenter: ChatConversationPresenter

    @ProvidePresenter
    fun providePresenter(): ChatConversationPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_CHATS, scopeName)
            .module { bind(ChatConversationScreenArgs::class.java).toInstance(args) }
            .getInstance(ChatConversationPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as ChatFlowFragment).setNavBarVisibility(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.messagesList.adapter = adapter
        binding.sendBtn.setOnClickListener { presenter.postMessage(binding.chatInput.text.toString()) }
    }

    override fun setTitle(title: String) {
        binding.toolbar.title = title
    }

    override fun setMessages(messages: List<ChatMessageUiModel>) {
        adapter.updateItems(messages)
    }

    override fun addMessage(uiMsg: ChatMessageUiModel, withClean: Boolean) {
        adapter.addMessage(uiMsg)
        if (withClean) binding.chatInput.setText("")
        binding.messagesList.scrollToPosition(0)
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance(args: ChatConversationScreenArgs) = ChatConversationFragment().apply { this.args = args }
    }
}