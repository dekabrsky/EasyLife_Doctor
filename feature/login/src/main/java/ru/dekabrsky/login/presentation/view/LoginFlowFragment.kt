package ru.dekabrsky.login.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.di.inject
import ru.dekabrsky.easylife.basic.fragments.BasicFlowFragment
import ru.dekabrsky.easylife.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.easylife.basic.navigation.di.moduleFlow
import ru.dekabrsky.easylife.basic.navigation.router.AppRouter
import ru.dekabrsky.easylife.basic.network.model.ReLoginType
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.presentation.presenter.LoginFlowPresenter
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import ru.dekabrsky.webview.presentation.view.WebViewFragment
import toothpick.Toothpick
import javax.inject.Inject

class LoginFlowFragment : BasicFlowFragment(), LoginFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_LOGIN

    private var reLoginType: ReLoginType = ReLoginType.DEFAULT

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Login.SCREEN_LOGIN -> LoginFragment.newInstance()
                    Flows.Login.SCREEN_PIN_LOGIN ->
                        PinLoginFragment.newInstance()
                    Flows.Common.SCREEN_WEB_VIEW ->
                        WebViewFragment.newInstance(data as WebViewArgs)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: LoginFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP, scopeName)
            .moduleFlow {
                bind(ReLoginType::class.java).toInstance(reLoginType)
            }
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
        fun newInstance(type: ReLoginType? = null) = LoginFlowFragment().apply {
            type?.let { this.reLoginType = type }
        }
    }

}