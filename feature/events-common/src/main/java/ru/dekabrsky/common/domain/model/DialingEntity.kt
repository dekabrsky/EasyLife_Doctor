package ru.dekabrsky.common.domain.model

import org.threeten.bp.LocalDateTime

@Suppress("LongParameterList")
class DialingEntity (
    val id: Int,
    val name: String,

    val callersBaseId: Long,
    val callersBaseName: String,
    val callersBaseCount: Int,

    val scenarioId: Int,
    val scenarioName: String,
    val scenarioStepsCount: Int,

    val created: LocalDateTime,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime?,

    val status: DialingStatus,
    val percent: Int
)