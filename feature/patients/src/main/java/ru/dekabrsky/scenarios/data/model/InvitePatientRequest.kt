package ru.dekabrsky.scenarios.data.model

import androidx.annotation.Keep

@Keep
class InvitePatientRequest(
    val fullName: String,
    val isForChild: Boolean,
    val relatedUserId: Long?,
    val relatedFullName: String?
)