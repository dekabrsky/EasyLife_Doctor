package ru.dekabrsky.common.presentation.model

import ru.dekabrsky.common.domain.model.CallersBaseEntity
import java.io.Serializable

class ChatsFlowScreenArgs (
    val parentScope: String,
    val screenKey: String,
    val callersBaseEntity: CallersBaseEntity? = null
): Serializable