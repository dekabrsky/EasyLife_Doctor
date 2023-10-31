package ru.dekabrsky.scenarios.data.model

import androidx.annotation.Keep

@Keep
class PatientCodeResponse(
    val code: String?,
    val parentCode: String? = null,
    val role: String?
)