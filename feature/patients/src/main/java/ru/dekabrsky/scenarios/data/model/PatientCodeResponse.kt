package ru.dekabrsky.scenarios.data.model

import androidx.annotation.Keep

@Keep
class PatientCodeResponse(
    val code: String?,
    val relatedCode: String? = null
)