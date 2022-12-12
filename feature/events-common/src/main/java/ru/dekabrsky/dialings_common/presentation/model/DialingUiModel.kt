package ru.dekabrsky.dialings_common.presentation.model

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.dialings_common.domain.model.DialingStatus
import java.io.Serializable
import java.math.BigDecimal

class DialingUiModel(
    val id: Int,
    val name: String,

    val callersBaseId: Int,
    val callersBaseName: String,
    val callersBaseCount: Int,

    val scenarioId: Int,
    val scenarioName: String,
    val scenarioStepsCount: Int,

    val created: String,
    val startDate: String,
    val endDate: String,

    val status: DialingStatus,
    val progress: Int,
    val formattedProgress: String
): Serializable