package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class RegistrationRequest(
    val credentials: String,
    val code: String,
    val deviceToken: String
)