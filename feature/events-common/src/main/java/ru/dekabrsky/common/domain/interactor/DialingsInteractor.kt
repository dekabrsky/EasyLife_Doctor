package ru.dekabrsky.common.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.dekabrsky.common.domain.model.DialingEntity
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.easylife.basic.network.utils.Direction
import ru.dekabrsky.easylife.basic.network.utils.SortVariants

interface DialingsInteractor {
    fun getDialings(
        direction: Direction,
        sortBy: SortVariants,
        status: DialingStatus?
    ): Observable<List<DialingEntity>>

    fun getDialingsByCallersBase(baseId: Int): Observable<List<DialingEntity>>

    fun getDialingById(id: Int): Observable<DialingEntity>

    fun startDialing(id: Int): Completable
}