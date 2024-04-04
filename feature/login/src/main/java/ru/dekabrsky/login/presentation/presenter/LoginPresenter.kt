package ru.dekabrsky.login.presentation.presenter

import android.content.Context
import android.content.Intent
import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.PatientMedicinesDiff
import ru.dekabrsky.feature.notifications.common.domain.model.NotificationEntity
import ru.dekabrsky.italks.basic.navigation.BaseScreens
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.basic.resources.ResourceProvider
import ru.dekabrsky.italks.basic.rx.RxSchedulers
import ru.dekabrsky.italks.basic.rx.withLoadingView
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.login.R
import ru.dekabrsky.login.domain.interactor.LoginInteractorImpl
import ru.dekabrsky.login.presentation.model.Extras
import ru.dekabrsky.login.presentation.view.LoginView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
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
    private val context: Context
): BasicPresenter<LoginView>() {

    enum class LoginMode { LOGIN, REGISTRATION }

    private var currentCode: String = ""
    private var currentLogin: String = ""
    private var currentPassword: String = ""
    private var lastLogin = sharedPreferencesProvider.lastLogin.get()

    private var mode: LoginMode = LoginMode.LOGIN

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        viewState.setLogin(lastLogin)
        analyticsSender.sendLogin()

        parseIntent()

//        interactor.login("dobryden", "123")
//            .observeOn(RxSchedulers.main())
//            .subscribe({
//                router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
//            }, viewState::showError)
//            .addFullLifeCycle()
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
                interactor.getFcmToken()
                    .flatMap { token -> interactor.login(currentLogin, currentPassword, token) }
                    .observeOn(RxSchedulers.main())
                    .withLoadingView(viewState)
                    .subscribe({
                        router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
                        analyticsSender.setUserId(it.id)
                    }, viewState::showError)
                    .addFullLifeCycle()
                if (lastLogin != currentLogin) sharedPreferencesProvider.lastLogin.set(currentLogin)
            }

            LoginMode.REGISTRATION -> {
                interactor.registration(currentCode, currentLogin, currentPassword)
                    .observeOn(RxSchedulers.main())
                    .withLoadingView(viewState)
                    .subscribe({
                        router.replaceFlow(
                            Flows.Main.name,
                            TabsFlowArgs(it.role)
                        )
                    }, viewState::showError)
                    .addFullLifeCycle()
            }
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
}