package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.view.View
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentPatientsCodesBinding
import ru.dekabrsky.scenarios.presentation.model.PatientsCodesScreenArgs
import ru.dekabrsky.scenarios.presentation.presenter.PatientsCodesPresenter
import toothpick.Toothpick

class PatientsCodesFragment: BasicFragment(), PatientsCodesView {
    private var model: PatientsCodesScreenArgs? = null

    private val binding by viewBinding(FragmentPatientsCodesBinding::bind)

    override val layoutRes = R.layout.fragment_patients_codes

    @InjectPresenter
    lateinit var presenter: PatientsCodesPresenter

    @ProvidePresenter
    fun providePresenter(): PatientsCodesPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_SCENARIOS, scopeName)
            .getInstance(PatientsCodesPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        model?.let {
            binding.patientCode.text = it.patientCode
            it.parentCode?.let {
                binding.parentCode.visible()
                binding.parentCodeTitle.visible()
                binding.parentCode.text = it
            }

        }
        (parentFragment as PatientsFlowFragment).setNavBarVisibility(false)
        binding.doneBtn.setOnClickListener { onBackPressed() }
    }

    override fun onBackPressed() { presenter.onBackPressed() }

    companion object {
        fun newInstance(model: PatientsCodesScreenArgs) = PatientsCodesFragment().apply {
            this.model = model
        }
    }
}