package ru.dekabrsky.callersbase.data.repository

import io.reactivex.Observable
import ru.dekabrsky.callersbase.data.api.CallersBaseApi
import ru.dekabrsky.callersbase.data.mapper.CallersBaseResponseToEntityMapper
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import javax.inject.Inject

class CallersBaseRepository @Inject constructor(
    private val api: CallersBaseApi,
    private val mapper: CallersBaseResponseToEntityMapper
){
    fun getCallersBases(direction: String, sortBy: String): Observable<List<CallersBaseEntity>> =
        api.getCallersBases(
            direction = direction,
            sortBy = sortBy
        ).map { mapper.mapList(it) }

    fun getCallersBase(id: Int): Observable<CallersBaseEntity> =
        api.getCallersBase(id).map { mapper.mapBase(it) }
}