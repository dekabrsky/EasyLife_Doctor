package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class LoginTokenResponse(
    val tokenType: String?,
    val accessToken: String?,
    val expiresIn: Int?,
    val refreshToken: String?
)