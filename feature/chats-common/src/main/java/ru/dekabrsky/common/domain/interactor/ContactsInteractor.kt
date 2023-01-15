package ru.dekabrsky.common.domain.interactor

import io.reactivex.Observable
import ru.dekabrsky.common.domain.model.CallersBaseEntity
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants

interface ContactsInteractor {
    fun getCallersBases(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ): Observable<List<CallersBaseEntity>>

    fun getCallersBase(id: Int): Observable<CallersBaseEntity>
}