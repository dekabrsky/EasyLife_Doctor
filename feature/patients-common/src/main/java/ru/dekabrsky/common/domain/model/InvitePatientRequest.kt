package ru.dekabrsky.common.domain.model

import androidx.annotation.Keep

@Keep
class InvitePatientRequest(
    val fullName: String,
    val isForChild: Boolean,
    val relatedUserId: Int?,
    val relatedFullName: String?
)