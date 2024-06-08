package ru.dekabrsky.login.presentation.presenter

import android.content.Intent
import io.reactivex.Single
import main.utils.isBlankOrEmpty
import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.PatientMedicinesDiff
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.italks.basic.navigation.BaseScreens
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.login.R
import ru.dekabrsky.login.domain.interactor.LoginInteractorImpl
import ru.dekabrsky.login.presentation.model.Extras
import ru.dekabrsky.login.presentation.view.LoginView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import javax.inject.Inject

@Suppress("LongParameterList")
class LoginPresenter @Inject constructor(
    private val router: FlowRouter,
    private val interactor: LoginInteractorImpl,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val analyticsSender: AnalyticsSender,
    private val loginDataCache: LoginDataCache,
    private val resourceProvider: ResourceProvider,
    private val intent: Intent,
    private val errorHandler: ServerErrorHandler
): BasicPresenter<LoginView>() {

    enum class LoginMode { LOGIN, REGISTRATION }

    private var currentCode: String = ""
    private var currentLogin: String = ""
    private var currentPassword: String = ""
    private var termsIsChecked: Boolean = false
    private var policyIsChecked: Boolean = false
    private var lastLogin = sharedPreferencesProvider.lastLogin.get()

    private var mode: LoginMode = LoginMode.LOGIN

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setLogin(lastLogin)
        analyticsSender.sendLogin()

        parseIntent()

        // симуляция сохраненного пина. По факту тут нужно будет смотреть, есть ли пин
//        interactor.getSavedRefreshToken()?.let { refreshToken ->
//            makeLogin(interactor.refresh(refreshToken))
//        }



//        interactor.login("dobryden", "123")
//            .subscribeOnIo()
//            .subscribe({
//                router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
//            }, { errorHandler.onError(it, viewState) })
//            .addFullLifeCycle()
    }

    private fun makeLogin(method: Single<*>) {
        method
            .flatMap { interactor.getCurrentUser() }
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({
                router.replaceScreen(Flows.Login.SCREEN_PIN_LOGIN)
                analyticsSender.setUserId(it.id)
            }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
    }

    private fun parseIntent() {
        intent.extras?.let { extras ->

            (extras.getSerializable(Extras.NOTIFICATION) as? NotificationEntity)?.let {
                loginDataCache.medReminder = it
                viewState.showToast(resourceProvider.getString(R.string.auth_to_view))
            }

            when (extras.getString(Extras.TYPE)) {
                Extras.NOTIFICATIONS_UPDATE_BY_DOCTOR ->
                    loginDataCache.medicinesDiff = extras.getString(Extras.DIFF)
                Extras.CHAT_NEW_MESSAGE ->
                    loginDataCache.chatId = extras.getString(Extras.CHAT_ID)
                Extras.NOTIFICATIONS_UPDATE_BY_PATIENT ->
                    loginDataCache.patientMedicinesDiff = PatientMedicinesDiff(
                        patientId = extras.getString(Extras.PATIENT_ID).orEmpty(),
                        diff = extras.getString(Extras.DIFF).orEmpty()
                    )
                Extras.PATIENT_REGISTRATION ->
                    loginDataCache.registeredPatientId = extras.getString(Extras.PATIENT_ID)
            }
        }
    }

    fun onDoneButtonClick() {
        when (mode) {
            LoginMode.LOGIN -> {
                if (validateLogin().not()) return
                makeLogin(
                    interactor.getFcmToken()
                        .flatMap { token -> interactor.login(currentLogin, currentPassword, token) }
                )
                if (lastLogin != currentLogin) sharedPreferencesProvider.lastLogin.set(currentLogin)
            }

            LoginMode.REGISTRATION -> {
                if (validateRegistration().not()) return
                makeLogin(
                    interactor.getFcmToken().flatMap { token ->
                        interactor.registration(currentCode, currentLogin, currentPassword, token)
                    }
                )
            }
        }
    }

    private fun validateLogin(): Boolean {
        val errors = mutableListOf<String>().apply {
            if (currentLogin.isBlankOrEmpty()) add("Заполните логин")
            if (currentPassword.isBlankOrEmpty()) add("Заполните пароль")
        }

        return errors.isEmpty().also { errorsIsEmpty ->
            if (errorsIsEmpty.not()) viewState.showToast(errors.joinToString("\n"))
        }
    }

    private fun validateRegistration(): Boolean {
        val errors = mutableListOf<String>().apply {
            if (currentCode.isBlankOrEmpty()) add("Заполните код приглашения")
            if (currentLogin.isBlankOrEmpty()) add("Заполните логин")
            if (currentPassword.isBlankOrEmpty()) add("Заполните пароль")
            if (!termsIsChecked || !policyIsChecked) add("Требуется согласие со всеми документами")
        }

        return errors.isEmpty().also { errorsIsEmpty ->
            if (errorsIsEmpty.not()) viewState.showToast(errors.joinToString("\n"))
        }
    }

    fun onChangeModeClick() {
        when (mode) {
            LoginMode.LOGIN -> {
                mode = LoginMode.REGISTRATION
                viewState.setupForRegistration()
            }

            LoginMode.REGISTRATION -> {
                mode = LoginMode.LOGIN
                viewState.setupForLogin()
            }
        }
    }

    fun onLoginChanged(text: CharSequence) {
        currentLogin = text.toString()
    }

    fun onPasswordChanged(text: CharSequence) {
        currentPassword = text.toString()
    }

    fun onCodeTextChanged(text: String) {
        currentCode = text
    }

    fun onGrantPermissionBySettingsClicked() {
        router.startFlow(BaseScreens.SCREEN_OPEN_SETTINGS)
    }

    fun onTermsTextClick() {
        router.navigateTo(
            Flows.Common.SCREEN_WEB_VIEW,
            WebViewArgs(Scopes.SCOPE_FLOW_LOGIN, TERMS_URL)
        )
    }

    fun onPolicyTextClick() {
        router.navigateTo(
            Flows.Common.SCREEN_WEB_VIEW,
            WebViewArgs(Scopes.SCOPE_FLOW_LOGIN, POLICY_URL)
        )
    }

    fun onPolicyCheckedChanged(isChecked: Boolean) {
        this.policyIsChecked = isChecked
    }

    fun onTermsCheckedChanged(isChecked: Boolean) {
        this.termsIsChecked = isChecked
    }

    companion object {
        private const val TERMS_URL = "https://easylife-project.ru/terms_of_use"
        private const val POLICY_URL = "https://easylife-project.ru/privacy_policy"
    }
}