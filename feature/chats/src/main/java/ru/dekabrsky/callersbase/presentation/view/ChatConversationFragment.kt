package ru.dekabrsky.callersbase.presentation.view

import ru.dekabrsky.callersbase.R
import ru.dekabrsky.italks.basic.fragments.BasicFragment

class ChatConversationFragment: BasicFragment() {
    override val layoutRes: Int
        get() = R.layout.fragment_chat_conversation

    override fun onBackPressed() {}

    override fun onResume() {
        super.onResume()
        (parentFragment as ChatFlowFragment).setNavBarVisibility(false)
    }

    companion object {
        fun newInstance() = ChatConversationFragment()
    }
}