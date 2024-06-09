package ru.dekabrsky.easylife.tabs.presentation.fragment

import android.os.Bundle
import android.view.View
import main.utils.setBoolVisibility
import main.utils.visible
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.easylife.basic.di.inject
import ru.dekabrsky.easylife.basic.di.module
import ru.dekabrsky.easylife.basic.fragments.BasicFlowFragment
import ru.dekabrsky.easylife.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.easylife.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.easylife.basic.navigation.di.moduleFlow
import ru.dekabrsky.easylife.basic.navigation.router.AppRouter
import ru.dekabrsky.easylife.basic.viewBinding.viewBinding
import ru.dekabrsky.easylife.feature.tabs.R
import ru.dekabrsky.easylife.feature.tabs.databinding.TabsFragmentBinding
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.easylife.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.easylife.tabs.presentation.presenter.TabsFlowPresenter
import ru.dekabrsky.easylife.tabs.presentation.view.TabsFlowView
import toothpick.Toothpick
import javax.inject.Inject

class TabsFlowFragment : BasicFlowFragment(), TabsFlowView {

    override val containerId: Int = R.id.flowContainer
    override val layoutRes = R.layout.tabs_fragment

    private var args : TabsFlowArgs? = null

    @InjectPresenter
    lateinit var presenter: TabsFlowPresenter

    @Inject
    lateinit var flowFragmentProvider: FlowFragmentProvider

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?) =
                flowFragmentProvider.provideFlowFragment(screenKey, data)
        }

    @ProvidePresenter
    fun providePresenter(): TabsFlowPresenter {
        return Toothpick.openScope(Scopes.SCOPE_FLOW_TABS)
            .module { bind(TabsFlowArgs::class.java).toInstance(args) }
            .getInstance(TabsFlowPresenter::class.java)
    }

    override fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP, Scopes.SCOPE_FLOW_TABS)
            .moduleFlow()
            .inject(this)
    }

    private val binding by viewBinding(TabsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navBar.visible()
        binding.navBar.initWidget(mvpDelegate)
        binding.navBar.setOnNavigationItemSelectedListener { menuItem ->
            presenter.onTabSelect(menuItem.itemId)
            true
        }
    }

    override fun setSelectedCallTab() {
        //viewBinding.navBar.selectedItemId = R.id.call
    }

    override fun setSelectedScenariosTab() {
        //viewBinding.navBar.selectedItemId = R.id.other
    }

    override fun setTabsByRole(menu: Int) {
        binding.navBar.menu.clear()
        binding.navBar.inflateMenu(menu)
    }

    override fun onFinallyFinished() {
        super.onFinallyFinished()
        Toothpick.closeScope(Scopes.SCOPE_FLOW_TABS)
    }

    fun setNavBarVisibility(isVisible: Boolean) {
        binding.navBar.setBoolVisibility(isVisible)
    }

    companion object {
        fun newInstance(args: TabsFlowArgs) = TabsFlowFragment().apply { this.args = args }
    }
}