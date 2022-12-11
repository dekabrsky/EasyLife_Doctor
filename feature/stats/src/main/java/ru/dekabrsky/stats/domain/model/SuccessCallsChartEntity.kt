package ru.dekabrsky.stats.domain.model

import org.threeten.bp.LocalDateTime

class SuccessCallsChartEntity(
    val date: LocalDateTime,
    val successCalls: Int,
    val time: String
)