package ru.dekabrsky.italks.testerSettings.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.moduleFlow
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.testerSettings.R
import ru.dekabrsky.italks.testerSettings.presentation.presenter.TesterSettingsFlowPresenter
import toothpick.Toothpick
import javax.inject.Inject

class TesterSettingsFlowFragment: BasicFlowFragment(), TesterSettingsFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_TESTER_SETTINGS

    @Suppress("UseIfInsteadOfWhen")
    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.TesterSettings.SCREEN_TESTER_SETTINGS -> TesterSettingsFragment.newInstance()
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: TesterSettingsFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP, scopeName)
            .moduleFlow()
            .inject(this)
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        Toothpick.closeScope(scopeName)
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance() = TesterSettingsFlowFragment()
    }
}