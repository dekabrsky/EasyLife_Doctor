package ru.dekabrsky.login.data.mapper

import android.util.Base64
import ru.dekabrsky.italks.tabs.domain.UserType
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.login.data.model.UserInfoResponse
import ru.dekabrsky.login.domain.model.UserInfoEntity
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LoginDataMapper @Inject constructor() {
    fun mapCredentials(login: String, password: String): CredentialsRequest {
        return CredentialsRequest(
            credentials = String(
                Base64.encode("$login:$password".toByteArray(), Base64.DEFAULT),
                StandardCharsets.UTF_8
            )
        )
    }

    fun mapRegistration(code: String, login: String, password: String): RegistrationRequest {
        return RegistrationRequest(
            credentials = String(
                Base64.encode("$login:$password".toByteArray(), Base64.DEFAULT),
                StandardCharsets.UTF_8
            ),
            code = code
        )
    }

    fun mapUserInfo(response: UserInfoResponse): UserInfoEntity {
        return UserInfoEntity(
            id = response.id ?: 0,
            name = response.name ?: "",
            role = UserType.valueByName(response.roleName?.uppercase())
        )
    }
}