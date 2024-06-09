package ru.dekabrsky.login.presentation.util

import android.content.Intent
import ru.dekabrsky.easylife.basic.fragments.BasicView
import ru.dekabrsky.easylife.basic.resources.ResourceProvider
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.PatientMedicinesDiff
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.login.R
import ru.dekabrsky.login.presentation.model.Extras

fun parseLoginIntent(
    intent: Intent,
    loginDataCache: LoginDataCache,
    viewState: BasicView,
    resourceProvider: ResourceProvider
) {
    intent.extras?.let { extras ->

        (extras.getSerializable(Extras.NOTIFICATION) as? NotificationEntity)?.let {
            loginDataCache.medReminder = it
            viewState.showToast(resourceProvider.getString(R.string.auth_to_view))
        }

        when (extras.getString(Extras.TYPE)) {
            Extras.NOTIFICATIONS_UPDATE_BY_DOCTOR ->
                loginDataCache.medicinesDiff = extras.getString(Extras.DIFF)
            Extras.CHAT_NEW_MESSAGE ->
                loginDataCache.chatId = extras.getString(Extras.CHAT_ID)
            Extras.NOTIFICATIONS_UPDATE_BY_PATIENT ->
                loginDataCache.patientMedicinesDiff = PatientMedicinesDiff(
                    patientId = extras.getString(Extras.PATIENT_ID).orEmpty(),
                    diff = extras.getString(Extras.DIFF).orEmpty()
                )
            Extras.PATIENT_REGISTRATION ->
                loginDataCache.registeredPatientId = extras.getString(Extras.PATIENT_ID)
        }
    }
}