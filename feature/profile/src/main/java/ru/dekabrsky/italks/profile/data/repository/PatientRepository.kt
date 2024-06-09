package ru.dekabrsky.easylife.profile.data.repository

import ru.dekabrsky.easylife.profile.data.api.ProfileApi
import ru.dekabrsky.easylife.profile.data.mapper.CodeResponseToEntityMapper
import javax.inject.Inject

class PatientRepository @Inject constructor(
    private val api: ProfileApi,
    private val mapper: CodeResponseToEntityMapper
) {
    fun generateParent() = api.createParent().map { mapper.map(it) }
}