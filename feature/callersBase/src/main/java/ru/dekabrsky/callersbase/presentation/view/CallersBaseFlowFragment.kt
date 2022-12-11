package ru.dekabrsky.callersbase.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.R
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase_common.presentation.model.CallersBasesFlowScreenArgs
import ru.dekabrsky.callersbase.presentation.presenter.CallersBaseFlowPresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes.SCOPE_FLOW_CALLERS_BASE
import toothpick.Toothpick
import javax.inject.Inject

class CallersBaseFlowFragment : BasicFlowFragment(), CallersBaseFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName = SCOPE_FLOW_CALLERS_BASE

    private lateinit var args: CallersBasesFlowScreenArgs

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.CallersBase.SCREEN_BASES_LIST -> CallersBasesListFragment.newInstance()
                    Flows.CallersBase.SCREEN_BASES_DETAILS ->
                        CallersBaseDetailsFragment.newInstance(data as CallersBaseEntity)
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: CallersBaseFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(args.parentScope, scopeName)
            .module { bind(CallersBasesFlowScreenArgs::class.java).toInstance(args) }
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
        fun newInstance(args: CallersBasesFlowScreenArgs) =
            CallersBaseFlowFragment().apply { this.args = args }
    }
}