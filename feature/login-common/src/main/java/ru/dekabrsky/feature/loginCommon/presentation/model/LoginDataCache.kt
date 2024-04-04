package ru.dekabrsky.feature.loginCommon.presentation.model

import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import javax.inject.Inject

class LoginDataCache @Inject constructor() {
    var currentUserData: UserInfoEntity? = null

    var medReminder: NotificationEntity? = null

    var medicinesDiff: String? = null

    var chatId: String? = null

    var patientMedicinesDiff: PatientMedicinesDiff? = null

    var registeredPatientId: String? = null
}

class PatientMedicinesDiff(
    val patientId: String,
    val diff: String
)