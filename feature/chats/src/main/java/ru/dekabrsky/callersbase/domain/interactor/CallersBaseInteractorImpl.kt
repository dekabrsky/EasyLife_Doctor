package ru.dekabrsky.callersbase.domain.interactor

import ru.dekabrsky.callersbase.data.repository.CallersBaseRepository
import ru.dekabrsky.callersbase_common.domain.interactor.CallersBaseInteractor
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.italks.basic.network.utils.Direction
import javax.inject.Inject

class CallersBaseInteractorImpl @Inject constructor(
    private val repository: CallersBaseRepository
): CallersBaseInteractor {
    override fun getCallersBases(direction: String, sortBy: String) =
        repository.getCallersBases(direction, sortBy)

    override fun getCallersBase(id: Int) = repository.getCallersBase(id)
}