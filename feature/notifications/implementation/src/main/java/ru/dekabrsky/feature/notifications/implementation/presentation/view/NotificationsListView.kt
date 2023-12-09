package ru.dekabrsky.feature.notifications.implementation.presentation.view

import moxy.viewstate.strategy.alias.AddToEndSingle
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import ru.dekabrsky.italks.basic.fragments.BasicView

@AddToEndSingle
interface NotificationsListView: BasicView {
    fun setEmptyLayoutVisibility(isVisible: Boolean)
    fun setChatsList(items: List<NotificationEntity>)
    fun setListLoadingVisibility(isVisible: Boolean)
}