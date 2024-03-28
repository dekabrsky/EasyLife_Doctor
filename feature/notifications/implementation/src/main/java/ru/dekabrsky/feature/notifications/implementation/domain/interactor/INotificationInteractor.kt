package ru.dekabrsky.feature.notifications.implementation.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single
import main.utils.orZero
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity

interface INotificationInteractor {
    fun getAll(): Single<List<NotificationEntity>>

    fun add(notification: NotificationEntity): Completable

    fun delete(notification: NotificationEntity): Completable

    fun update(notification: NotificationEntity): Completable
}