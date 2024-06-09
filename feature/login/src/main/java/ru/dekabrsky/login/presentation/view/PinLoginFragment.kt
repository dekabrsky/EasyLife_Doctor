package ru.dekabrsky.login.presentation.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import main.utils.onTextChange
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.databinding.FmtPinLoginBinding
import ru.dekabrsky.login.presentation.presenter.PinLoginPresenter
import toothpick.Toothpick

class PinLoginFragment: BasicFragment(), PinLoginView {
    private val binding by viewBinding(FmtPinLoginBinding::bind)

    override val layoutRes = R.layout.fmt_pin_login

    @InjectPresenter
    lateinit var presenter: PinLoginPresenter

    @ProvidePresenter
    fun providePresenter(): PinLoginPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_LOGIN, scopeName)
            .module {
                bind(Intent::class.java).toInstance(requireActivity().intent)
            }
            .getInstance(PinLoginPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.pinLoginCardBtn.setOnClickListener {
            presenter.onLoginClicked()
        }

        binding.editTextLogin.setOnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE) presenter.onLoginClicked()
            true
        }

        binding.additionalButton.setOnClickListener {
            presenter.onAdditionalButtonClicked()
        }

        binding.editTextLogin.onTextChange { presenter.onPinTextChanged(it) }
    }

    override fun onPause() {
        super.onPause()
        hideKeyboard()
    }

    override fun setAdditionalButtonText(text: String) {
        binding.additionalButton.text = text
    }

    override fun setTitle(title: String) {
        binding.title.text = title
    }

    override fun setPinError(error: String) {
        binding.loginLayout.error = error
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = PinLoginFragment()
    }
}