package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.dialings_common.presentation.adapter.MiniDialingsAdapter
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentScenarioDetailsBinding
import ru.dekabrsky.scenarios.databinding.FragmentScenariosBinding
import ru.dekabrsky.scenarios.presentation.presenter.ScenarioDetailsPresenter
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel
import toothpick.Toothpick

class ScenarioDetailsFragment: BasicFragment(), ScenarioDetailsView {

    private lateinit var model: ScenarioItemUiModel

    override val layoutRes: Int
        get() = R.layout.fragment_scenario_details

    @InjectPresenter
    lateinit var presenter: ScenarioDetailsPresenter

    private val binding by viewBinding(FragmentScenarioDetailsBinding::bind)

    private val dialingsAdapter by lazy { MiniDialingsAdapter(presenter::onDialingClick) }

    @ProvidePresenter
    fun providePresenter(): ScenarioDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_SCENARIOS, scopeName)
            .module { bind(ScenarioItemUiModel::class.java).toInstance(model) }
            .getInstance(ScenarioDetailsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.linkedDialings.adapter = dialingsAdapter
        (parentFragment as ScenariosFlowFragment).setNavBarVisibility(false)
    }


    override fun setMainData(model: ScenarioItemUiModel) {
        binding.toolbar.title = model.name
    }

    override fun setupDialings(dialings: List<MiniDialingUiModel>) {
        binding.dialingsListTitle.visibility = View.VISIBLE
        binding.linkedDialings.visibility = View.VISIBLE
        dialingsAdapter.updateItems(dialings)
    }

    companion object {
        fun newInstance(model: ScenarioItemUiModel) = ScenarioDetailsFragment().apply {
            this.model = model
        }
    }
}