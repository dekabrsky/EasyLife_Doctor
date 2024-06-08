package ru.dekabrsky.stats.presentation.view

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
import ru.dekabrsky.stats.R
import ru.dekabrsky.stats.presentation.presenter.AdultProfileFlowPresenter
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import ru.dekabrsky.webview.presentation.view.WebViewFragment
import toothpick.Toothpick
import javax.inject.Inject

class AdultProfileFlowFragment: BasicFlowFragment(), AdultProfileFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_ADULT_PROFILE

    @Suppress("UseIfInsteadOfWhen")
    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Stats.SCREEN_MAIN_STATS -> AdultProfileFragment.newInstance()
                    Flows.Common.SCREEN_WEB_VIEW -> WebViewFragment.newInstance(data as WebViewArgs)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: AdultProfileFlowPresenter

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
        fun newInstance() = AdultProfileFlowFragment()
    }
}