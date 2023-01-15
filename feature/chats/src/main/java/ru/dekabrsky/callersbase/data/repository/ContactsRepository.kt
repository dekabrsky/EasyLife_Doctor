package ru.dekabrsky.callersbase.data.repository

import io.reactivex.Observable
import ru.dekabrsky.callersbase.data.api.ContactsApi
import ru.dekabrsky.callersbase.data.mapper.CallersBaseResponseToEntityMapper
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val api: ContactsApi,
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