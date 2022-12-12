package ru.dekabrsky.scenarios.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import ru.dekabrsky.scenarios.data.model.ScenarioPageableResponse
import ru.dekabrsky.scenarios_common.domain.model.ScenarioEntity
import javax.inject.Inject

class ScenariosResponseToEntityMapper @Inject constructor() {
    fun map(response: ScenarioPageableResponse): List<ScenarioEntity> {
        return response.content?.map {
            ScenarioEntity(
                countSteps = it.countSteps.orEmpty(),
                created = Instant.ofEpochMilli(it.created ?: 0)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime(),
                id = it.id ?: 0,
                name = it.name.orEmpty()
            )
        } ?: listOf()
    }
}