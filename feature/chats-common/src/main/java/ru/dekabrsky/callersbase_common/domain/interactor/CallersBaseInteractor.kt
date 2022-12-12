package ru.dekabrsky.callersbase_common.domain.interactor

import io.reactivex.Observable
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants

interface CallersBaseInteractor {
    fun getCallersBases(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ): Observable<List<CallersBaseEntity>>

    fun getCallersBase(id: Int): Observable<CallersBaseEntity>
}