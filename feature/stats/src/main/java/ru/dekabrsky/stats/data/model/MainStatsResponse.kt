package ru.dekabrsky.stats.data.model

import androidx.annotation.Keep

@Keep
class MainStatsResponse(
    val averageCallDuration: DurationResponse,
    val averageDialingsDuration: DurationResponse,
    val averageNumberOfCallsPerDialing: Int,
    val totalDialings: Int
)

@Keep
class DurationResponse(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
)

// todo добавить nullability