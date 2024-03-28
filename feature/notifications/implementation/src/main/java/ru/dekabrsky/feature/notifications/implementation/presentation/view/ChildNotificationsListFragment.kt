package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.ChildNotificationListPresenter
import toothpick.Toothpick

class ChildNotificationsListFragment(
    private val notificationsScope: String
): NotificationsListFragment<ChildNotificationListPresenter>(), ChildNotificationsListView {

    override val isForChild: Boolean = true

    @InjectPresenter
    override lateinit var presenter: ChildNotificationListPresenter

    @ProvidePresenter
    fun providePresenter(): ChildNotificationListPresenter {
        return Toothpick.openScopes(notificationsScope, scopeName)
            .getInstance(ChildNotificationListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    companion object {
        fun newInstance(notificationsScope: String) = ChildNotificationsListFragment(notificationsScope)
    }
}