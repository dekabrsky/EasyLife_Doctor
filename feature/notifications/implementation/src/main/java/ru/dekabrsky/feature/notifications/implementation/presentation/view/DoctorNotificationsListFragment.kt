package ru.dekabrsky.feature.notifications.implementation.presentation.view

import android.os.Bundle
import android.view.View
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.feature.notifications.implementation.R
import ru.dekabrsky.feature.notifications.implementation.presentation.presenter.DoctorNotificationListPresenter
import toothpick.Toothpick

class DoctorNotificationsListFragment(
    private val notificationsScope: String
): NotificationsListFragment<DoctorNotificationListPresenter>(), DoctorNotificationsListView {

    override val isForChild: Boolean = false

    @InjectPresenter
    override lateinit var presenter: DoctorNotificationListPresenter

    @ProvidePresenter
    fun providePresenter(): DoctorNotificationListPresenter {
        return Toothpick.openScopes(notificationsScope, scopeName)
            .getInstance(DoctorNotificationListPresenter::class.java)
            .also { Toothpick.closeScope(scopeName) }
    }

    override fun setTitle(title: String) {
        binding.toolbar.title = title
    }

    companion object {
        fun newInstance(notificationsScope: String) = DoctorNotificationsListFragment(notificationsScope)
    }
}