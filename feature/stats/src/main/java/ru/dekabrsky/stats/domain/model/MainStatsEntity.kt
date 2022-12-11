package ru.dekabrsky.stats.domain.model

class MainStatsEntity(
    val averageCallDuration: DurationEntity,
    val averageDialingsDuration: DurationEntity,
    val averageNumberOfCallsPerDialing: Int,
    val totalDialings: Int
)

class DurationEntity(
    val hours: Int,
    val minutes: Int,
    val seconds: Int
)