package ru.dekabrsky.scenarios.domain.interactor

import ru.dekabrsky.scenarios.data.repository.ScenariosRepository
import ru.dekabrsky.scenarios_common.domain.interactor.DoctorPatientsInteractor
import javax.inject.Inject

class DoctorPatientsInteractorImpl @Inject constructor(
    private val repository: ScenariosRepository
): DoctorPatientsInteractor {
    override fun generateCode() = repository.generateCode()
}