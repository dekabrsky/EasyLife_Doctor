package ru.dekabrsky.login.data.mapper

import android.util.Base64
import ru.dekabrsky.login.data.model.CredentialsRequest
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LoginCredentialsToRequestMapper @Inject constructor() {
    fun mapCredentials(login: String, password: String): CredentialsRequest {
        return CredentialsRequest(
            credentials = String(
                Base64.encode("$login:$password".toByteArray(), Base64.DEFAULT),
                StandardCharsets.UTF_8
            )
        )
    }
}