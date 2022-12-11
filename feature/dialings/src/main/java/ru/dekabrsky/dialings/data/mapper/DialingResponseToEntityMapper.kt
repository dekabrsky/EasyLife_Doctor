package ru.dekabrsky.dialings.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import ru.dekabrsky.dialings.data.model.DialingResultResponse
import ru.dekabrsky.dialings.data.model.DialingsResponse
import ru.dekabrsky.dialings_common.domain.model.DialingEntity
import ru.dekabrsky.dialings_common.domain.model.DialingStatus
import javax.inject.Inject

class DialingResponseToEntityMapper @Inject constructor() {
    fun map(response: DialingsResponse): List<DialingEntity> {
        return response.content?.map { mapContent(it) }.orEmpty()
    }

    fun mapContent(it: DialingResultResponse) =
        DialingEntity(
            callersBaseId = it.callersBase?.id ?: 0,
            callersBaseName = it.callersBase?.name.orEmpty(),
            callersBaseCount = it.callersBase?.countCallers ?: 0,

            created = it.created?.let {
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            } ?: LocalDateTime.now(),

            id = it.id ?: 0,
            name = it.name.orEmpty(),
            scenarioId = it.scenario?.scenarioId ?: 0,
            scenarioName = it.scenario?.name.orEmpty(),
            scenarioStepsCount = it.scenario?.countSteps ?: 0,

            startDate = it.startDate?.let {
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            } ?: LocalDateTime.now(),

            endDate = it.endDate?.let {
                LocalDateTime.ofInstant(
                    Instant.ofEpochMilli(it),
                    ZoneId.systemDefault()
                )
            } ?: LocalDateTime.now(),

            status = it.status?.let { status -> DialingStatus.valueOf(status) }
                ?: DialingStatus.SCHEDULED,
            percent = maxOf(it.progress?.percentEnd ?: 0, it.percentEnd?: 0)
        )

}