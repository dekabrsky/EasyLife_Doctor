package ru.dekabrsky.italks.profile.domain.interactor

import ru.dekabrsky.italks.profile.data.repository.PatientRepository
import javax.inject.Inject

class ProfileInteractor @Inject constructor(
    private val repository: PatientRepository
) {
    fun generateParentCode() = repository.generateParent()
}