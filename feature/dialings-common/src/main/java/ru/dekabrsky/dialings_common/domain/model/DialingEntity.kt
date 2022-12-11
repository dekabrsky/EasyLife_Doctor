package ru.dekabrsky.dialings_common.domain.model

import org.threeten.bp.LocalDateTime


class DialingEntity (
    val id: Int,
    val name: String,

    val callersBaseId: Int,
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