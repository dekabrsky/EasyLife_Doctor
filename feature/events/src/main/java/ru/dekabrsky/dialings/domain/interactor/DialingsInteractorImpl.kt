package ru.dekabrsky.dialings.domain.interactor

import io.reactivex.Completable
import io.reactivex.Observable
import ru.dekabrsky.common.domain.interactor.DialingsInteractor
import ru.dekabrsky.common.domain.model.DialingEntity
import ru.dekabrsky.common.domain.model.DialingStatus
import ru.dekabrsky.dialings.data.repository.DialingsRepository
import ru.dekabrsky.easylife.basic.network.utils.Direction
import ru.dekabrsky.easylife.basic.network.utils.SortVariants
import javax.inject.Inject

class DialingsInteractorImpl @Inject constructor(
    private val repository: DialingsRepository
): DialingsInteractor {
    override fun getDialings(
        direction: Direction,
        sortBy: SortVariants,
        status: DialingStatus?
    ): Observable<List<DialingEntity>> =
        repository.getDialings(direction.name, sortBy.name, status?.name)

    override fun getDialingsByCallersBase(baseId: Int): Observable<List<DialingEntity>> =
        repository.getDialingsByCallersBase(baseId)

    override fun getDialingById(id: Int): Observable<DialingEntity> = repository.getDialingById(id)

    override fun startDialing(id: Int): Completable = repository.startDialingById(id)
}