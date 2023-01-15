package ru.dekabrsky.login.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.presentation.presenter.LoginFlowPresenter
import toothpick.Toothpick
import javax.inject.Inject

class LoginFlowFragment : BasicFlowFragment(), LoginFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_LOGIN

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                if (screenKey == Flows.Login.SCREEN_LOGIN) {
                    LoginFragment.newInstance()
                } else {
                    super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP, scopeName)
            .installNavigation()
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
        fun newInstance() = LoginFlowFragment()
    }

}