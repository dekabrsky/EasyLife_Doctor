package ru.dekabrsky.scenarios.data.model

import androidx.annotation.Keep

@Keep
class ScenarioPageableResponse(
    val content: List<ScenarioResponse>?
)

@Keep
class ScenarioResponse(
    val countSteps: String?,
    val created: Long?,
    val id: Int?,
    val name: String?
)