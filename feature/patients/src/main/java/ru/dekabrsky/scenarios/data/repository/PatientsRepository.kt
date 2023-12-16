package ru.dekabrsky.scenarios.data.repository

import io.reactivex.Single
import ru.dekabrsky.common.domain.model.InvitePatientRequest
import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.scenarios.data.api.DoctorPatientsApi
import ru.dekabrsky.scenarios.data.mapper.PatientsResponseToEntityMapper
import javax.inject.Inject

class PatientsRepository @Inject constructor(
    private val api: DoctorPatientsApi,
    private val mapper: PatientsResponseToEntityMapper
) {
    fun generateCode(
        patientName: String,
        isChild: Boolean,
        parentName: String?,
        parentId: Int?
    ): Single<PatientCodeEntity> =
        api.invite(
            InvitePatientRequest(
                fullName = patientName,
                isForChild = isChild,
                relatedFullName = parentName,
                relatedUserId = parentId
            )
        ).map { mapper.map(it) }

}