package ru.dekabrsky.scenarios.presentation.view

import android.os.Bundle
import android.view.View
import main.utils.onTextChange
import main.utils.setBoolVisibility
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.scenarios.R
import ru.dekabrsky.scenarios.databinding.FragmentInvitePatientBinding
import ru.dekabrsky.scenarios.presentation.presenter.InvitePatientPresenter
import toothpick.Toothpick

class InvitePatientFragment: BasicFragment(), InvitePatientView {

    private val binding by viewBinding(FragmentInvitePatientBinding::bind)

    override val layoutRes = R.layout.fragment_invite_patient

    @InjectPresenter
    lateinit var presenter: InvitePatientPresenter

    @ProvidePresenter
    fun providePresenter(): InvitePatientPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_PATIENTS, scopeName)
            .getInstance(InvitePatientPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        (parentFragment as PatientsFlowFragment).setNavBarVisibility(false)

        binding.patientName.onTextChange { presenter.onPatientNameChanged(it) }
        binding.parentName.onTextChange { presenter.onParentNameChanged(it) }
        binding.patientAgeSwitch.setOnCheckedChangeListener { _, isChecked ->
            presenter.isPatientOlder15CheckedChanged(isChecked)
        }
        binding.doneBtn.setOnClickListener { presenter.onDoneClick() }
        binding.existingParent.setOnClickListener { presenter.onParentsListClick() }
        binding.existingParentCross.setOnClickListener { presenter.onParentCrossClick() }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun onBackPressed() { presenter.onBackPressed() }

    override fun setParentState(selected: Boolean, parentName: String, link: String) {
        binding.parentName.isEnabled = selected.not()
        binding.parentName.setText(parentName)
        binding.existingParent.text = link
        binding.existingParentCross.setBoolVisibility(selected)
    }

    companion object {
        fun newInstance() = InvitePatientFragment()
    }
}