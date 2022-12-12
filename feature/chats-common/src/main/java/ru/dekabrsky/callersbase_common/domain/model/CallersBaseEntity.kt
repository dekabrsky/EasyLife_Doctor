package ru.dekabrsky.callersbase_common.domain.model

import org.threeten.bp.LocalDateTime
import java.io.Serializable

class CallersBaseEntity(
    val id: Int,
    val columns: List<String>,
    val confirmed: Boolean,
    val countCallers: Int,
    val countInvalidCallers: Int,
    val name: String,
    val updated: LocalDateTime
): Serializable
