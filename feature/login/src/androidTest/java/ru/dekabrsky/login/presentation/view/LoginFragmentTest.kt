package ru.dekabrsky.login.presentation.view

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.reactivex.Single
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import ru.dekabrsky.italks.basic.di.module
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.data.api.LoginApi
import ru.dekabrsky.login.data.mapper.LoginDataMapper
import ru.dekabrsky.login.data.model.CredentialsRequest
import ru.dekabrsky.login.data.model.RegistrationRequest
import ru.dekabrsky.login.data.model.UserInfoResponse
import ru.dekabrsky.login.data.repository.LoginRepository
import toothpick.Toothpick
import toothpick.configuration.Configuration

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest: TestCase() {

    @Before
    fun initialize() {
        val toothpickCfg = Configuration.forDevelopment().preventMultipleRootScopes()
        Toothpick.setConfiguration(toothpickCfg)
        Toothpick.openScope(Scopes.SCOPE_FLOW_LOGIN).module {
            bind(FlowRouter::class.java).toInstance(FlowRouter(null))
            bind(LoginRepository::class.java).toInstance(LoginRepository(MockLoginApiImpl(), LoginDataMapper()))
        }
    }

    //проверяем, меняется ли режим отображения при нажатии
    @Test
    fun testChangeMode() {
        launchFragmentInContainer<LoginFragment>(null, R.style.ThemeITalks)
        onView(withId(R.id.title)).check(matches(withText("Вход")))
        onView(withId(R.id.changeMode)).perform(click())
        onView(withId(R.id.title)).check(matches(withText("Регистрация")))
        onView(withId(R.id.editTextCode)).check(matches(isDisplayed()))
    }


    class MockLoginApiImpl: LoginApi {
        override fun login(credentialsRequest: CredentialsRequest): Single<UserInfoResponse> {
            return Single.just(UserInfoResponse())
        }

        override fun registration(regRequest: RegistrationRequest): Single<UserInfoResponse> {
            return Single.just(UserInfoResponse())
        }

        override fun getCurrentUser(): Single<UserInfoResponse> {
            return Single.just(UserInfoResponse())
        }
    }
}