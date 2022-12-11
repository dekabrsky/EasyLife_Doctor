package ru.dekabrsky.dialings_common.presentation.model

import java.io.Serializable

class DialingsFlowScreenArgs(
    val parentScope: String,
    val screenKey: String,
    val dialingId: Int? = null
): Serializable