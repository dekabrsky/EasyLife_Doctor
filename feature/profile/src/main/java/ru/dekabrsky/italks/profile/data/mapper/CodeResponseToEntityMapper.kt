package ru.dekabrsky.easylife.profile.data.mapper

import main.utils.orZero
import ru.dekabrsky.easylife.profile.data.model.response.CodeResponse
import ru.dekabrsky.easylife.profile.domain.model.CodeEntity
import javax.inject.Inject

class CodeResponseToEntityMapper @Inject constructor() {
    fun map(response: CodeResponse): CodeEntity {
        return CodeEntity(response.code.orZero())
    }
}