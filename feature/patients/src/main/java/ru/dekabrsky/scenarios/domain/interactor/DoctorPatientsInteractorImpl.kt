package ru.dekabrsky.scenarios.domain.interactor

import ru.dekabrsky.common.domain.interactor.DoctorPatientsInteractor
import ru.dekabrsky.scenarios.data.repository.ScenariosRepository
import javax.inject.Inject

class DoctorPatientsInteractorImpl @Inject constructor(
    private val repository: ScenariosRepository
): DoctorPatientsInteractor {
    override fun generateCode(isChild: Boolean) = repository.generateCode(isChild)
}