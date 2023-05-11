package ru.dekabrsky.feature.notifications.implementation.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationFlowPresenter
import ru.dekabrsky.feature.notifications.implementation.presentation.view.NotificationFlowFragment.Companion.newInstance
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.installNavigation
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import javax.inject.Inject

class NotificationFlowFragment: BasicFlowFragment(), NotificationFlowView {

    override val layoutRes = R.layout.basic_fragment_flow
    override val containerId = R.id.flowContainer

    override val scopeName = Scopes.SCOPE_FLOW_NOTIFICATIONS

    @Suppress("UseIfInsteadOfWhen")
    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Notifications.SCREEN_NOTIFICATIONS_LIST ->
                        NotificationsListFragment.newInstance()
                    Flows.Notifications.SCREEN_NOTIFICATION_DETAILS ->
                        NotificationDetailFragment.newInstance()
                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: NotificationFlowPresenter

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
        fun newInstance() = NotificationFlowFragment()
    }
}