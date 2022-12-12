package ru.dekabrsky.callersbase.data.mapper

import org.threeten.bp.Instant
import org.threeten.bp.ZoneId
import ru.dekabrsky.callersbase.data.model.CallersBaseResponse
import ru.dekabrsky.callersbase.data.model.ContentResponse
import ru.dekabrsky.callersbase_common.domain.model.CallersBaseEntity
import javax.inject.Inject

class CallersBaseResponseToEntityMapper @Inject constructor() {
    fun mapList(response: CallersBaseResponse): List<CallersBaseEntity> {
        return response.content.map { base -> mapBase(base) }
    }

    fun mapBase(base: ContentResponse) =
        CallersBaseEntity(
            id = base.id,
            columns = base.columns.map { it.currentName },
            confirmed = base.confirmed,
            countCallers = base.countCallers,
            countInvalidCallers = base.countInvalidCallers,
            name = base.name,
            updated = Instant.ofEpochMilli(base.updated)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        )
}
