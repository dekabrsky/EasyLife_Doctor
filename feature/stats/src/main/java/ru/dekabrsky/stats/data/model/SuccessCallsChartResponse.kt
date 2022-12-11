package ru.dekabrsky.stats.data.model

import androidx.annotation.Keep

@Keep
class SuccessCallsChartResponse(
    val date: Long,
    val successCalls: Int,
    val time: String
)
