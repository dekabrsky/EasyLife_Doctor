package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentChatDetailsBinding
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase.presentation.adapter.ChatsVariablesListAdapter
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.callersbase.presentation.presenter.ChatDetailsPresenter
import ru.dekabrsky.dialings_common.presentation.adapter.MiniDialingsAdapter
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class ChatDetailsFragment : BasicFragment(), ChatDetailsView {

    private lateinit var model: CallersBaseEntity

    override val layoutRes: Int
        get() = R.layout.fragment_chat_details

    @InjectPresenter
    lateinit var presenter: ChatDetailsPresenter

    private val binding by viewBinding(FragmentChatDetailsBinding::bind)

    private val variablesAdapter by lazy { ChatsVariablesListAdapter() }

    private val dialingsAdapter by lazy { MiniDialingsAdapter(presenter::onDialingClick) }

    @ProvidePresenter
    fun providePresenter(): ChatDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_CHATS, scopeName)
            .module { bind(CallersBaseEntity::class.java).toInstance(model) }
            .getInstance(ChatDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onResume() {
        super.onResume()
        (parentFragment as ChatsListFragment).setNavBarVisibility(false)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.baseVariablesList.adapter = variablesAdapter
        binding.linkedDialings.adapter = dialingsAdapter
    }


    override fun setMainData(model: CallersBaseUiModel) {
        binding.toolbar.title = model.title
        binding.countValue.text = "${model.count} эл."
        binding.dateValue.text = model.date
    }

    override fun setupVariables(variables: List<String>) {
        variablesAdapter.updateItems(variables)
    }

    override fun setupDialings(dialings: List<MiniDialingUiModel>) {
        binding.divider.visibility = View.VISIBLE
        binding.dialingsListTitle.visibility = View.VISIBLE
        binding.linkedDialings.visibility = View.VISIBLE
        dialingsAdapter.updateItems(dialings)
    }

    companion object {
        fun newInstance(model: CallersBaseEntity) = ChatDetailsFragment().apply {
            this.model = model
        }
    }
}