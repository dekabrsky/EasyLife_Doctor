package ru.dekabrsky.italks.tabs.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FlowFragmentProvider
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.di.localNavigationModules
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.basic.viewBinding.viewBinding
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.feature.tabs.R
import ru.dekabrsky.italks.feature.tabs.databinding.TabsFragmentBinding
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.italks.tabs.presentation.presenter.TabsFlowPresenter
import ru.dekabrsky.italks.tabs.presentation.view.TabsFlowView
import ru.terrakok.cicerone.Navigator
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
            .installNavigation()
            .inject(this)
    }

    private val binding by viewBinding(TabsFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.navBar.visibility = View.VISIBLE
        binding.navBar.initWidget(mvpDelegate)
        binding.navBar.maxItemCount
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
        binding.navBar.visibility = if (isVisible) View.VISIBLE else View.GONE
    }

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    companion object {
        fun newInstance(args: TabsFlowArgs) = TabsFlowFragment().apply { this.args = args }
    }
}