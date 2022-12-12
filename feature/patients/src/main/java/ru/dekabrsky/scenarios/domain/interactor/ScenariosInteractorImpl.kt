package ru.dekabrsky.scenarios.domain.interactor

import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.scenarios.data.repository.ScenariosRepository
import ru.dekabrsky.scenarios_common.domain.interactor.ScenariosInteractor
import javax.inject.Inject

class ScenariosInteractorImpl @Inject constructor(
    private val repository: ScenariosRepository
): ScenariosInteractor {
    override fun getScenarios(direction: String, sortBy: String) =
        repository.getCallersBases(direction, sortBy)
}