package ru.dekabrsky.common.presentation.mapper

import ru.dekabrsky.common.domain.model.DialingEntity
import ru.dekabrsky.common.presentation.model.MiniDialingUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import javax.inject.Inject

class MiniDialingUiMapper @Inject constructor(){
    fun map(entity: DialingEntity): MiniDialingUiModel {
        return MiniDialingUiModel(
            name = entity.name,
            id = entity.id,
            dateTime = formatDateTimeToUiDateTime(entity.startDate)
        )
    }
}