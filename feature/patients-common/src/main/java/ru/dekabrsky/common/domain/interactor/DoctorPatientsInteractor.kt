package ru.dekabrsky.common.domain.interactor

import io.reactivex.Single

interface DoctorPatientsInteractor {
    fun generateCode(): Single<Int>
}