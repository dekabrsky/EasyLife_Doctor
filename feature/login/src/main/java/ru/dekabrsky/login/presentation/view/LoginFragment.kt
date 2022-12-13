package ru.dekabrsky.login.presentation.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.widget.EditText
import main.utils.onTextChange
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.fragments.BasicFragment
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.databinding.FmtLoginBinding
import ru.dekabrsky.login.presentation.presenter.LoginPresenter
import toothpick.Toothpick

class LoginFragment: BasicFragment(), LoginView {

    override val layoutRes: Int
    get() = R.layout.fmt_login

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    private val binding by viewBinding(FmtLoginBinding::bind)

    @ProvidePresenter
    fun providePresenter(): LoginPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_FLOW_LOGIN, scopeName)
            .getInstance(LoginPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextLogin.onTextChange { presenter.onLoginChanged(binding.editTextLogin.text.toString()) }
        binding.editTextPassword.onTextChange { presenter.onPasswordChanged(binding.editTextPassword.text.toString()) }

        binding.loginCardBtn.setOnClickListener { presenter.onLoginClick() }
    }

    companion object{
        fun newInstance() = LoginFragment()
    }
}