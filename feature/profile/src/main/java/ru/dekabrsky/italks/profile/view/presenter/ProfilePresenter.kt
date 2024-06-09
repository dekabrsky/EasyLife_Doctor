package ru.dekabrsky.easylife.profile.view.presenter

import ru.dekabrsky.feature.loginCommon.domain.interactor.LoginInteractor
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.TokenCache
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.basic.rx.withLoadingView
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.profile.R
import ru.dekabrsky.easylife.profile.domain.interactor.ProfileInteractor
import ru.dekabrsky.easylife.profile.domain.model.CodeEntity
import ru.dekabrsky.easylife.profile.view.ProfileView
import ru.dekabrsky.easylife.scopes.Scopes
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetMode
import ru.dekabrsky.simpleBottomsheet.view.model.BottomSheetScreenArgs
import ru.dekabrsky.simpleBottomsheet.view.model.ButtonState
import ru.dekabrsky.webview.presentation.model.WebViewArgs
import javax.inject.Inject

class ProfilePresenter @Inject constructor(
    val router: FlowRouter,
    private val interactor: ProfileInteractor,
    private val loginInteractor: LoginInteractor,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginDataCache: LoginDataCache,
    private val tokenCache: TokenCache,
    private val errorHandler: ServerErrorHandler
) : BasicPresenter<ProfileView>(router) {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        checkAvatars()
        viewState.setupAvatars(router)
        loginDataCache.currentUserData?.let {
            viewState.setTitle(it.nameWithNickname)
        }
    }

    fun generateParent() {
        interactor.generateParentCode()
            .subscribeOnIo()
            .subscribe(
                { dispatchParentCode(it) },
                { }
            )
            .addFullLifeCycle()
    }

    private fun checkAvatars() {
        if (sharedPreferencesProvider.gameAvatar.get().isEmpty()) {
            router.startFlow(Flows.Avatar.name)
        }
    }

    private fun dispatchParentCode(entity: CodeEntity) {
        viewState.setParentCode(entity.code)
    }

    fun onLogoutClick() {
        router.navigateTo(
            Flows.Common.SCREEN_BOTTOM_INFO,
            BottomSheetScreenArgs(
                title = "Выйти из учетной записи?",
                subtitle = "Придется снова ввести пароль",
                mode = BottomSheetMode.GAME,
                icon = R.drawable.bye,
                buttonState = ButtonState(text = "Да, выйти", action = ::makeLogout)
            )
        )
    }

    private fun makeLogout() {
        //tokenCache.accessToken = null
        loginInteractor.getFcmToken()
            .flatMapCompletable { token -> loginInteractor.logout(token) }
            .subscribeOnIo()
            .withLoadingView(viewState)
            .subscribe({ router.newRootFlow(Flows.Login.name) }, { errorHandler.onError(it, viewState) })
            .addFullLifeCycle()
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

    companion object {
        private const val TERMS_URL = "https://easylife-project.ru/terms_of_use"
        private const val POLICY_URL = "https://easylife-project.ru/privacy_policy"
    }
}