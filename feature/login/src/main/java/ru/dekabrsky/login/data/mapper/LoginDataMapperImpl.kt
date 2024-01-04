package ru.dekabrsky.login.data.mapper

import android.util.Base64
import main.utils.orZero
import ru.dekabrsky.feature.loginCommon.data.mapper.LoginDataMapper
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.feature.loginCommon.data.model.LoginLevelResponse
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity
import ru.dekabrsky.feature.loginCommon.domain.model.UserLoginLevelEntity
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class LoginDataMapperImpl @Inject constructor(): LoginDataMapper {
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

    override fun mapUserInfo(response: UserInfoResponse): UserInfoEntity {
        return UserInfoEntity(
            id = response.id.orZero(),
            name = response.name.orEmpty(),
            role = UserType.valueByServerId(response.roleId),
            currentLevel = mapUserLevelInfo(response.currentLevel)
        )
    }

    private fun mapUserLevelInfo(response: LoginLevelResponse?) =
      UserLoginLevelEntity(
            score = response?.score.orZero(),
            experience = response?.experience.orZero()
        )
}