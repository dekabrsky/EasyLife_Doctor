package ru.dekabrsky.dialings_common.presentation.mapper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import ru.dekabrsky.dialings_common.domain.model.DialingEntity
import ru.dekabrsky.dialings_common.presentation.model.MiniDialingUiModel
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