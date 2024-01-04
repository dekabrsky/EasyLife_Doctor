package ru.dekabrsky.login.data.api.mapper

import org.junit.Assert.assertEquals
import org.junit.Test
import ru.dekabrsky.feature.loginCommon.domain.model.UserType
import ru.dekabrsky.login.data.mapper.LoginDataMapperImpl
import ru.dekabrsky.feature.loginCommon.data.model.UserInfoResponse
import ru.dekabrsky.feature.loginCommon.domain.model.UserInfoEntity

class LoginDataMapperTest {

    private val mapper = LoginDataMapperImpl()

//    @Test
//    fun mapCredentials() {
//        val login = "user"
//        val password = "1234"
//        val decoded = "dXNlcjoxMjM"
//        val mapResult = mapper.mapCredentials(login, password)
//        assertEquals(mapResult, CredentialsRequest(decoded))
//    }

    @Test
    fun mapResponseToEntity() {
        val responsesToEntities = listOf(
            UserInfoResponse(1, "Den", "DOCTOR") to UserInfoEntity(1, "Den", UserType.DOCTOR),
            UserInfoResponse(null, "Dan", "") to UserInfoEntity(0, "Dan", UserType.CHILD),
            UserInfoResponse(1, null, "PATIENT") to UserInfoEntity(1, "", UserType.PATIENT),
        )
        responsesToEntities.forEach {
            val result = mapper.mapUserInfo(it.first)
            assertEquals(it.second.id, result.id)
            assertEquals(it.second.name, result.name)
            assertEquals(it.second.role, result.role)
        }
    }

}