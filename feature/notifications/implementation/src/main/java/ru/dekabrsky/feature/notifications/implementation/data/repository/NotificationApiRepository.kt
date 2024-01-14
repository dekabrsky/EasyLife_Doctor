package ru.dekabrsky.feature.notifications.implementation.data.repository

import main.utils.orZero
import ru.dekabrsky.feature.notifications.implementation.data.api.NotificationApi
import ru.dekabrsky.feature.notifications.implementation.data.mapper.NotificationResponseToEntityMapper
import ru.dekabrsky.feature.notifications.implementation.domain.entity.NotificationEntity
import javax.inject.Inject

class NotificationApiRepository @Inject constructor(
    private val api: NotificationApi,
//    private val dao: NotificationDao,
    private val mapper: NotificationResponseToEntityMapper,
//    private val dbMapper: NotificationDbToEntityMapper,
//    private val dbRepository: NotificationRepository
) {

    fun getAll() = api.getNotifications().map { list -> list.sortedBy { it.time }.map { mapper.mapResponseToEntity(it) } }
//    На случай, если нужно будет сохранять в бд
//    fun getAll() =
//        api
//            .getNotifications()
//            .map { list -> list.map { mapper.mapResponseToEntity(it) } }
//            .flatMapCompletable { entities ->
//                dao.deleteAll()
//                    .andThen(dao.insertAll(entities.map { dbMapper.entityToDb(it) }) )
//            }
//            .andThen(dbRepository.getAll())

    fun add(notification: NotificationEntity) =
        api
            .postNotification(mapper.mapEntityToRequest(notification))
            .map { mapper.mapResponseToEntity(it) }

    fun delete(notificationId: Int) = api.deleteNotification(notificationId)

    fun update(notification: NotificationEntity) =
        api.putNotification(notification.uid?.toInt().orZero(), mapper.mapEntityToRequest(notification))

}