package ru.dekabrsky.login.presentation.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
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
        binding.editTextCode.onTextChange { presenter.onCodeTextChanged(binding.editTextCode.text.toString()) }

        binding.loginCardBtn.setOnClickListener { presenter.onDoneButtonClick() }
        binding.changeMode.setOnClickListener { presenter.onChangeModeClick() }
    }

    override fun setupForRegistration() {
        binding.title.text = getString(R.string.registration)
        binding.codeLayout.visibility = View.VISIBLE
        binding.changeMode.text = getString(R.string.change_mode_to_login)
        binding.bgImage.setImageResource(R.drawable.ic_divan)
    }

    override fun setupForLogin() {
        binding.title.text = getString(R.string.login_title)
        binding.codeLayout.visibility = View.GONE
        binding.changeMode.text = getString(R.string.registration)
        binding.bgImage.setImageResource(R.drawable.drawable_table)
    }

    companion object{
        fun newInstance() = LoginFragment()
    }
}