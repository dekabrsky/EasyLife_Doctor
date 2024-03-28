package ru.dekabrsky.feature.notifications.implementation.presentation.view

import androidx.fragment.app.Fragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.common.presentation.model.NotificationsFlowArgs
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.NotificationFlowPresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.basic.navigation.FragmentFlowNavigator
import ru.dekabrsky.italks.basic.navigation.di.moduleFlow
import ru.dekabrsky.italks.basic.navigation.router.AppRouter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.tabs.presentation.fragment.TabsFlowFragment
import toothpick.Toothpick
import javax.inject.Inject

class NotificationFlowFragment : BasicFlowFragment(), NotificationFlowView {


    override val layoutRes = R.layout.basic_fragment_flow

    override val containerId = R.id.flowContainer

    override val scopeName by lazy { args.parentScopeName + Scopes.SCOPE_FLOW_NOTIFICATION }

    private lateinit var args: NotificationsFlowArgs

    override fun provideNavigator(router: AppRouter): FragmentFlowNavigator =
        object : FragmentFlowNavigator(this, router, containerId) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? =
                when (screenKey) {
                    Flows.Notifications.SCREEN_CHILD_NOTIFICATIONS_LIST ->
                        ChildNotificationsListFragment.newInstance(scopeName)

                    Flows.Notifications.SCREEN_DOCTOR_NOTIFICATIONS_LIST ->
                        DoctorNotificationsListFragment.newInstance(scopeName)

                    Flows.Notifications.SCREEN_EDIT_NOTIFICATION ->
                        NotificationEditFragment.newInstance(
                            notification = data as? NotificationEntity ?: NotificationEntity(),
                            notificationsScope = scopeName
                        )

                    else -> super.createFragment(screenKey, data)
                }
        }

    @Inject
    @InjectPresenter
    lateinit var presenter: NotificationFlowPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun injectDependencies() {
        Toothpick.openScopes(args.parentScopeName, scopeName)
            .module { bind(NotificationsFlowArgs::class.java).toInstance(args) }
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

    fun setNavBarVisibility(isVisible: Boolean) {
        (parentFragment as? TabsFlowFragment)?.setNavBarVisibility(isVisible)
    }

    companion object {
        fun newInstance(args: NotificationsFlowArgs) = NotificationFlowFragment().apply { this.args = args }
    }

}