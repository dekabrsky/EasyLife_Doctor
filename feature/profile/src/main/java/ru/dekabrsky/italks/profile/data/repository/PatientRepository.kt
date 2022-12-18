package ru.dekabrsky.italks.profile.data.repository

import ru.dekabrsky.italks.profile.data.api.ProfileApi
import ru.dekabrsky.italks.profile.data.mapper.CodeResponseToEntityMapper
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val api: ProfileApi,
    private val mapper: CodeResponseToEntityMapper
) {
    fun generateParent() = api.createParent().map { mapper.map(it) }
}