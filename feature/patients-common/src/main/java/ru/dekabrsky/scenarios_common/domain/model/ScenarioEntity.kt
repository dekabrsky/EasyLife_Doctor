package ru.dekabrsky.scenarios_common.domain.model

import org.threeten.bp.LocalDateTime

class ScenarioEntity(
    val countSteps: String,
    val created: LocalDateTime,
    val id: Int,
    val name: String
)