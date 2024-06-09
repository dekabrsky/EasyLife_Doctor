package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity

@AddToEndSingle
interface NotificationsListView: BasicView {
    fun setEmptyLayoutVisibility(isVisible: Boolean)
    fun setChatsList(items: List<NotificationEntity>)
    fun setListLoadingVisibility(isVisible: Boolean)
    fun setToolbarBackButton()
}

@AddToEndSingle
interface DoctorNotificationsListView: NotificationsListView {
    fun setTitle(title: String)
}

interface ChildNotificationsListView: NotificationsListView