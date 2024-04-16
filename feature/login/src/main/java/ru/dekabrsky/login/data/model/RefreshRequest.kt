package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class RefreshRequest(
    val refreshToken: String
)