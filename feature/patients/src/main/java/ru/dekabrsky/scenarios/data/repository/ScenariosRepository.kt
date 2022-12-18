package ru.dekabrsky.scenarios.data.repository

import io.reactivex.Single
import ru.dekabrsky.scenarios.data.api.DoctorPatientsApi
import ru.dekabrsky.scenarios.data.mapper.PatientsResponseToEntityMapper
import javax.inject.Inject

class ScenariosRepository @Inject constructor(
    private val api: DoctorPatientsApi,
    private val mapper: PatientsResponseToEntityMapper
) {
    fun generateCode(): Single<Int> = api.generatePatient().map { it.code }
}