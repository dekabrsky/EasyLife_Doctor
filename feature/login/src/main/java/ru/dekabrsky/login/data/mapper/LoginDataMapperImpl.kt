package ru.dekabrsky.login.data.mapper

import android.util.Base64
import main.utils.orZero
import ru.dekabrsky.feature.loginCommon.data.mapper.LoginDataMapper
import ru.dekabrsky.feature.loginCommon.data.model.LoginLevelResponse
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserLoginLevelEntity
import ru.dekabrsky.login.data.model.LogoutRequest
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.login.data.model.LoginTokenResponse
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.login.domain.entity.LoginTokenEntity
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LoginDataMapperImpl @Inject constructor(): LoginDataMapper {
    fun mapCredentials(login: String, password: String, token: String): CredentialsRequest {
        return CredentialsRequest(
            credentials = encodeToBase64("$login:$password"),
            deviceToken = encodeToBase64(token)
        )
    }

    fun mapRegistration(code: String, login: String, password: String): RegistrationRequest {
        return RegistrationRequest(
            credentials = encodeToBase64("$login:$password"),
            code = code
        )
    }

    fun mapLogout(token: String) = LogoutRequest(encodeToBase64(token))

    private fun encodeToBase64(token: String) =
        String(Base64.encode(token.toByteArray(), Base64.DEFAULT), StandardCharsets.UTF_8)

    override fun mapUserInfo(response: UserInfoResponse): UserInfoEntity {
        return UserInfoEntity(
            id = response.id.orZero(),
            name = response.name.orEmpty(),
            displayName = response.displayName.orEmpty(),
            role = UserType.valueByServerId(response.roleId),
            currentLevel = mapUserLevelInfo(response.currentLevel)
        )
    }

    private fun mapUserLevelInfo(response: LoginLevelResponse?) =
      UserLoginLevelEntity(
            score = response?.score.orZero(),
            experience = response?.experience.orZero()
        )


    fun mapToken(response: LoginTokenResponse) =
        LoginTokenEntity(
            accessToken = response.accessToken.orEmpty(),
            expiresIn = response.expiresIn.orZero(),
            refreshToken = response.refreshToken.orEmpty()
        )
}