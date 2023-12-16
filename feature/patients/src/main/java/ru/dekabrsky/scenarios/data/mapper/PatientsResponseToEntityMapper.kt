package ru.dekabrsky.scenarios.data.mapper

import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.scenarios.data.model.PatientCodeResponse
import javax.inject.Inject

class PatientsResponseToEntityMapper @Inject constructor() {
    fun map(response: PatientCodeResponse) =
        PatientCodeEntity(
            patientCode = response.code.orEmpty(),
            parentCode = response.relatedCode
        )
}