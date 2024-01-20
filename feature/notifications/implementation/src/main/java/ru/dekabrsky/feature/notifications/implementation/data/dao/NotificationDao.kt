package ru.dekabrsky.feature.notifications.implementation.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single
import ru.dekabrsky.feature.notifications.implementation.data.model.NotificationDbEntity

@Dao
interface NotificationDao {
    @Query("SELECT * FROM ${NotificationDbEntity.TABLE_NOTIFICATIONS}")
    fun getAll(): Single<List<NotificationDbEntity>>

    @Query("SELECT * FROM ${NotificationDbEntity.TABLE_NOTIFICATIONS} WHERE uid IN (:ids)")
    fun loadAllByIds(ids: IntArray): Single<List<NotificationDbEntity>>

    @Query("SELECT * FROM ${NotificationDbEntity.TABLE_NOTIFICATIONS} WHERE uid = :id")
    fun getById(id: IntArray): Single<List<NotificationDbEntity>>

    @Insert
    fun insert(notificationDbEntity: NotificationDbEntity): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(entities: List<NotificationDbEntity>): Completable

    @Update
    fun update(notificationDbEntity: NotificationDbEntity): Completable

    @Delete
    fun delete(notificationDbEntity: NotificationDbEntity): Completable

    @Query("DELETE FROM ${NotificationDbEntity.TABLE_NOTIFICATIONS}")
    fun deleteAll(): Completable
}