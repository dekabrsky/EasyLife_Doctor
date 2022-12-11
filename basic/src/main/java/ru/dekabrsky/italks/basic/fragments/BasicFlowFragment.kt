package ru.dekabrsky.italks.basic.fragments

import ru.dekabrsky.italks.basic.R
import ru.dekabrsky.italks.basic.navigation.FlowNavigator
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BasicFlowFragment: BasicFragment() {

    override val layoutRes = R.layout.basic_fragment_flow

    protected open val containerId = R.id.flowContainer

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    @Inject
    lateinit var router: AppRouter

    private val navigator: FragmentFlowNavigator by lazy { provideNavigator(router) }

    protected abstract fun provideNavigator(router: AppRouter): FragmentFlowNavigator

    override fun onResume() {
        super.onResume()
        if (this::navigatorHolder.isInitialized) {
            navigatorHolder.setNavigator(navigator)
        }
    }

    override fun onPause() {
        if (this::navigatorHolder.isInitialized) {
            navigatorHolder.removeNavigator()
        }
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment =
            childFragmentManager.fragments.firstOrNull { it.isVisible && it is BasicFragment }
        (fragment as? BasicFragment)?.onBackPressed()
    }

}