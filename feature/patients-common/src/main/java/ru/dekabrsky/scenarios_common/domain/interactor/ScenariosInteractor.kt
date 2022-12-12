package ru.dekabrsky.scenarios_common.domain.interactor

import io.reactivex.Observable
import ru.dekabrsky.italks.basic.network.utils.Direction
import ru.dekabrsky.italks.basic.network.utils.SortVariants
import ru.dekabrsky.scenarios_common.domain.model.ScenarioEntity

interface ScenariosInteractor {
    fun getScenarios(
        direction: String = Direction.ASC.name,
        sortBy: String = SortVariants.NAME.name
    ): Observable<List<ScenarioEntity>>
}