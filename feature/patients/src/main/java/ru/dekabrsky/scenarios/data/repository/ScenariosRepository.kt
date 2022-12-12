package ru.dekabrsky.scenarios.data.repository

import io.reactivex.Observable
import ru.dekabrsky.scenarios.data.api.ScenariosApi
import ru.dekabrsky.scenarios.data.mapper.ScenariosResponseToEntityMapper
import ru.dekabrsky.scenarios_common.domain.model.ScenarioEntity
import javax.inject.Inject

class ScenariosRepository @Inject constructor(
    private val api: ScenariosApi,
    private val mapper: ScenariosResponseToEntityMapper
) {
    fun getCallersBases(direction: String, sortBy: String): Observable<List<ScenarioEntity>> =
        api.getScenarios(
            direction = direction,
            sortBy = sortBy
        ).map { mapper.map(it) }
}