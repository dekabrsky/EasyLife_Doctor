package ru.dekabrsky.callersbase.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase.databinding.FragmentCallersBaseDetailsBinding
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase.presentation.adapter.BasesVariablesListAdapter
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.callersbase.presentation.presenter.CallersBaseDetailsPresenter
import ru.dekabrsky.dialings_common.presentation.adapter.MiniDialingsAdapter
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick

class CallersBaseDetailsFragment : BasicFragment(), CallersBaseDetailsView {

    private lateinit var model: CallersBaseEntity

    override val layoutRes: Int
        get() = R.layout.fragment_callers_base_details

    @InjectPresenter
    lateinit var presenter: CallersBaseDetailsPresenter

    private val binding by viewBinding(FragmentCallersBaseDetailsBinding::bind)

    private val variablesAdapter by lazy { BasesVariablesListAdapter() }

    private val dialingsAdapter by lazy { MiniDialingsAdapter(presenter::onDialingClick) }

    @ProvidePresenter
    fun providePresenter(): CallersBaseDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_CALLERS_BASE, scopeName)
            .module { bind(CallersBaseEntity::class.java).toInstance(model) }
            .getInstance(CallersBaseDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
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
        fun newInstance(model: CallersBaseEntity) = CallersBaseDetailsFragment().apply {
            this.model = model
        }
    }
}