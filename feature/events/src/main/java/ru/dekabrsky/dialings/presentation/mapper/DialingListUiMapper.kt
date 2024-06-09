package ru.dekabrsky.dialings.presentation.mapper

import ru.dekabrsky.common.domain.model.DialingEntity
import ru.dekabrsky.common.presentation.model.DialingUiModel
import ru.dekabrsky.easylife.basic.dateTime.formatDateTimeToUiDateTime
import ru.dekabrsky.easylife.basic.dateTime.formatDateToUiDate
import javax.inject.Inject

class DialingListUiMapper @Inject constructor() {

    fun map(entity: DialingEntity): DialingUiModel {
        return DialingUiModel(
            id = entity.id,
            name = entity.name,

            status = entity.status,
            progress = entity.percent,
            formattedProgress = "${entity.percent}%",

            scenarioId = entity.scenarioId,
            scenarioName = entity.scenarioName,
            scenarioStepsCount = entity.scenarioStepsCount,

            callersBaseName = entity.callersBaseName,
            callersBaseId = entity.callersBaseId,
            callersBaseCount = entity.callersBaseCount,

            created = formatDateToUiDate(entity.created),
            startDate = formatDateTimeToUiDateTime(entity.startDate),
            endDate = entity.endDate?.let { formatDateTimeToUiDateTime(it) }.orEmpty()
        )
    }

}