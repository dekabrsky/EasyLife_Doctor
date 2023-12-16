package ru.dekabrsky.scenarios.presentation.model

class PatientInvitationUiModel(
    var patientName: String = "",
    var parentName: String = "",
    var parentId: Int? = null,
    var older15: Boolean = false
)