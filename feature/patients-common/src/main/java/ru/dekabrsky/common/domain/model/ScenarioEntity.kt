package ru.dekabrsky.common.domain.model

import org.threeten.bp.LocalDateTime

class ScenarioEntity(
    val countSteps: String,
    val created: LocalDateTime,
    val id: Int,
    val name: String
)