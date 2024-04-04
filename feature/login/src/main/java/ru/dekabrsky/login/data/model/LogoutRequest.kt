package ru.dekabrsky.login.data.model

import androidx.annotation.Keep

@Keep
class LogoutRequest(val deviceToken: String)