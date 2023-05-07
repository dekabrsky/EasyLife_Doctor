package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.common.presentation.adapter.MiniDialingsAdapter
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.presentation.presenter.PatientDetailsPresenter
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import ru.dekabrsky.scenarios.databinding.FragmentPatientDetailsBinding
import toothpick.Toothpick

class PatientDetailsFragment: BasicFragment(), PatientDetailsView {

    private lateinit var model: ScenarioItemUiModel

    override val layoutRes: Int
        get() = R.layout.fragment_patient_details

    @InjectPresenter
    lateinit var presenter: PatientDetailsPresenter

    private val binding by viewBinding(FragmentPatientDetailsBinding::bind)

    private val dialingsAdapter by lazy { MiniDialingsAdapter(presenter::onDialingClick) }

    @ProvidePresenter
    fun providePresenter(): PatientDetailsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_SCENARIOS, scopeName)
            .module { bind(ScenarioItemUiModel::class.java).toInstance(model) }
            .getInstance(PatientDetailsPresenter::class.java)
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

    override fun setupTakingMedications(dialings: List<TakingMedicationsUiModel>) {
        binding.dialingsListTitle.visibility = View.VISIBLE
        binding.linkedDialings.visibility = View.VISIBLE
        dialingsAdapter.updateItems(dialings)
    }

    companion object {
        fun newInstance(model: ScenarioItemUiModel) = PatientDetailsFragment().apply {
            this.model = model
        }
    }
}