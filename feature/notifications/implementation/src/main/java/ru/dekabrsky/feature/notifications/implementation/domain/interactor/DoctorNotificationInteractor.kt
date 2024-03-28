package ru.dekabrsky.feature.notifications.implementation.domain.interactor

import io.reactivex.Completable
import io.reactivex.Single
import main.utils.orZero
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.feature.notifications.implementation.data.repository.DoctorNotificationRepository
import javax.inject.Inject

/** не забывайте ставить userId */
class DoctorNotificationInteractor @Inject constructor(
    private val repository: DoctorNotificationRepository
): INotificationInteractor {

    private var userId = Long.MIN_VALUE

    fun setUserId(id: Long) {
        userId = id
    }

    override fun getAll(): Single<List<NotificationEntity>> = repository.getAll(userId)

    override fun add(notification: NotificationEntity): Completable = repository.add(userId, notification)

    override fun delete(notification: NotificationEntity): Completable = repository.delete(userId, notification.uid.orZero())

    override fun update(notification: NotificationEntity): Completable = repository.update(userId, notification)
}