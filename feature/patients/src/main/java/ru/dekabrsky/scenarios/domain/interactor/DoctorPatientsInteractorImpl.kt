package ru.dekabrsky.scenarios.domain.interactor

import ru.dekabrsky.common.domain.interactor.DoctorPatientsInteractor
import ru.dekabrsky.scenarios.data.repository.PatientsRepository
import javax.inject.Inject

class DoctorPatientsInteractorImpl @Inject constructor(
    private val repository: PatientsRepository
): DoctorPatientsInteractor {
    fun generateCode(
        patientName: String,
        isChild: Boolean,
        parentName: String?,
        parentId: Int?
    ) = repository.generateCode(patientName, isChild, parentName, parentId)
}