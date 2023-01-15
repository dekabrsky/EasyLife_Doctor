package ru.dekabrsky.dialings.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.dialings.R
import ru.dekabrsky.dialings.presentation.presenter.DialingsFlowPresenter
import ru.dekabrsky.common.presentation.model.EventsFlowScreenArgs
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import toothpick.Toothpick
import javax.inject.Inject

class DialingsFlowFragment : BasicFlowFragment(), DialingsFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_DIALINGS

    private lateinit var args: EventsFlowScreenArgs

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Events.SCREEN_DIALINGS_LIST -> DialingsListFragment.newInstance()
                    Flows.Events.SCREEN_DIALING_DETAILS ->
                        DialingDetailsFragment.newInstance(data as Int)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: DialingsFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(args.parentScope, scopeName)
            .module { bind(EventsFlowScreenArgs::class.java).toInstance(args) }
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

    fun setNavBarVisibility(isVisible: Boolean) {
        (parentFragment as TabsFlowFragment).setNavBarVisibility(isVisible)
    }

    companion object {
        fun newInstance(args: EventsFlowScreenArgs) = DialingsFlowFragment().apply {
            this.args = args
        }
    }
}