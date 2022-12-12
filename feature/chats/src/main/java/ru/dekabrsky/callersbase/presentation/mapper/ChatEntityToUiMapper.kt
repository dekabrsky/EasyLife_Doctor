package ru.dekabrsky.callersbase.presentation.mapper

import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import ru.dekabrsky.callersbase_common.presentation.model.CallersBaseUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateToUiDate
import javax.inject.Inject

class ChatEntityToUiMapper @Inject constructor() {
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