package ru.dekabrsky.common.domain.interactor

import io.reactivex.Single
import ru.dekabrsky.common.domain.model.PatientCodeEntity

interface DoctorPatientsInteractor {
    fun generateCode(isChild: Boolean): Single<PatientCodeEntity>
}