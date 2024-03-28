package ru.dekabrsky.scenarios.presentation.model

class PatientInvitationUiModel(
    var patientName: String = "",
    var parentName: String = "",
    var parentId: Long? = null,
    var older15: Boolean = false
)