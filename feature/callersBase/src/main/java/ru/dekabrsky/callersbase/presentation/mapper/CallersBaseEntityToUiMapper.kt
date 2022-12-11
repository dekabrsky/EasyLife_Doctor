package ru.dekabrsky.callersbase.presentation.mapper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateToUiDate
import javax.inject.Inject
import kotlin.random.Random

class CallersBaseEntityToUiMapper @Inject constructor() {
    fun map(entity: CallersBaseEntity): CallersBaseUiModel {
        return CallersBaseUiModel(
            date = formatDateToUiDate(entity.updated),
            count = entity.countCallers.toString(),
            title = entity.name,
            variables = entity.columns.map { "#${it}" },
            fullData = entity
        )
    }
}