package ru.dekabrsky.easylife.profile.domain.interactor

import ru.dekabrsky.easylife.profile.data.repository.PatientRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val repository: PatientRepository
) {
    fun generateParentCode() = repository.generateParent()
}