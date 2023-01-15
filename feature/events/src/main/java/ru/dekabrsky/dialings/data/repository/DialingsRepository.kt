package ru.dekabrsky.dialings.data.repository

import io.reactivex.Completable
import io.reactivex.Observable
import ru.dekabrsky.dialings.data.api.DialingsApi
import ru.dekabrsky.dialings.data.mapper.DialingResponseToEntityMapper
import ru.dekabrsky.common.domain.model.DialingEntity
import javax.inject.Inject

class DialingsRepository @Inject constructor(
    private val api: DialingsApi,
    private val mapper: DialingResponseToEntityMapper
) {
    fun getDialings(direction: String, sortBy: String, status: String?): Observable<List<DialingEntity>> =
        api.getDialings(direction = direction, sortBy = sortBy, status = status)
            .map { mapper.map(it) }

    fun getDialingsByCallersBase(baseId: Int): Observable<List<DialingEntity>> =
        api.getDialingsByCallersBase(baseId)
            .map { it.map { mapper.mapContent(it) } }

    fun getDialingById(id: Int): Observable<DialingEntity> =
        api.getDialingsById(id).map { mapper.mapContent(it) }

    fun startDialingById(id: Int): Completable = api.startDialingById(id)
}