package ru.dekabrsky.easylife.profile.view.fragment

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.di.inject
import ru.dekabrsky.easylife.basic.fragments.BasicFlowFragment
import ru.dekabrsky.easylife.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.easylife.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.easylife.basic.navigation.di.moduleFlow
import ru.dekabrsky.easylife.basic.navigation.router.AppRouter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.flows.R
import ru.dekabrsky.easylife.profile.view.ProfileFlowView
import ru.dekabrsky.easylife.profile.view.presenter.ProfileFlowPresenter
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.simpleBottomsheet.view.fragment.SimpleInfoBottomSheet
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import ru.dekabrsky.webview.presentation.view.WebViewFragment
import toothpick.Toothpick
import javax.inject.Inject


class ProfileFlowFragment : BasicFlowFragment(), ProfileFlowView {

    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    @Inject
    lateinit var flowFragmentProvider: FlowFragmentProvider

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Profile.SCREEN_PROFILE -> ProfileFragment.newInstance()
                    Flows.Common.SCREEN_BOTTOM_INFO -> SimpleInfoBottomSheet.newInstance(data as BottomSheetScreenArgs)
                    Flows.Common.SCREEN_WEB_VIEW -> WebViewFragment.newInstance(data as WebViewArgs)
                    else -> super.createFragment(screenKey, data)
                }
        }

    override val scopeName = Scopes.SCOPE_FLOW_PROFILE

    @Inject
    @InjectPresenter
    lateinit var presenter: ProfileFlowPresenter

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
        fun newInstance() = ProfileFlowFragment()
    }
}