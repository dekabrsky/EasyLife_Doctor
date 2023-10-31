package ru.dekabrsky.scenarios.data.repository

import io.reactivex.Single
import ru.dekabrsky.common.domain.model.PatientCodeEntity
import ru.dekabrsky.scenarios.data.api.DoctorPatientsApi
import ru.dekabrsky.scenarios.data.mapper.PatientsResponseToEntityMapper
import javax.inject.Inject

class ScenariosRepository @Inject constructor(
    private val api: DoctorPatientsApi,
    private val mapper: PatientsResponseToEntityMapper
) {
    fun generateCode(isChild: Boolean): Single<PatientCodeEntity> = if (isChild) {
        api.generateChild()
    } else {
        api.generatePatient()
    }.map { mapper.map(it) }

}