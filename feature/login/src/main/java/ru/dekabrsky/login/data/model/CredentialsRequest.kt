package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class CredentialsRequest(
    val credentials: String,
    val deviceToken: String
)