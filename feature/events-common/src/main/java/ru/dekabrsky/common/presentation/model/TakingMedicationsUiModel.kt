package ru.dekabrsky.common.presentation.model

import java.io.Serializable

class TakingMedicationsUiModel (
    val name: String,
    val id: Int,
    val dateTime: String,
    val isMedicationTaken: Boolean? = null,
    val isGameDone: Boolean? = null
): Serializable