package ru.dekabrsky.easylife.testerSettings.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.fragments.BasicFragment
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.easylife.testerSettings.R
import ru.dekabrsky.easylife.testerSettings.databinding.FmtTesterSettingsBinding
import ru.dekabrsky.easylife.testerSettings.presentation.presenter.TesterSettingsPresenter
import toothpick.Toothpick
import javax.inject.Inject


class TesterSettingsFragment: BasicFragment(), TesterSettingsView {

    override val layoutRes = R.layout.fmt_tester_settings

    @Inject
    @InjectPresenter
    lateinit var presenter: TesterSettingsPresenter

    private val binding by viewBinding(FmtTesterSettingsBinding::bind)

    @ProvidePresenter
    fun providePresenter(): TesterSettingsPresenter {
        return Toothpick.openScopes(Scopes.SCOPE_TESTER_SETTINGS, scopeName)
            .getInstance(TesterSettingsPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationIcon(R.drawable.ic_round_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener { presenter.onBackPressed() }
        binding.saveBtn.setOnClickListener {
            presenter.onSaveBtnClicked(binding.serverAddressInput.text.toString())
        }
        binding.server83.setOnClickListener { presenter.setSavedUrl(binding.server83.text) }
        binding.serverEasyLife.setOnClickListener { presenter.setSavedUrl(binding.serverEasyLife.text) }
    }

    override fun onBackPressed() = presenter.onBackPressed()

    companion object {
        fun newInstance() = TesterSettingsFragment()
    }

    override fun setupServerAddress(baseEndpoint: String) {
        binding.serverAddressInput.setText(baseEndpoint)
    }

    override fun restartApp() {
        requireActivity().finish()
        startActivity(requireActivity().intent)
    }
}