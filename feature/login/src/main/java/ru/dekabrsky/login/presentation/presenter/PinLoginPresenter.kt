package ru.dekabrsky.login.presentation.presenter

import android.content.Context
import android.content.Intent
import android.util.Base64
import com.google.crypto.tink.Aead
import com.google.crypto.tink.aead.AeadKeyTemplates
import com.google.crypto.tink.integration.android.AndroidKeysetManager
import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.feature.loginCommon.presentation.model.TokenCache
import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.network.utils.ServerErrorHandler
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.basic.resources.ResourceProvider
import ru.dekabrsky.easylife.basic.rx.withLoadingView
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.tabs.presentation.model.TabsFlowArgs
import ru.dekabrsky.login.R
import ru.dekabrsky.login.domain.interactor.LoginInteractorImpl
import ru.dekabrsky.login.presentation.util.Pbkdf2Factory
import ru.dekabrsky.login.presentation.util.Salt
import ru.dekabrsky.login.presentation.util.parseLoginIntent
import ru.dekabrsky.login.presentation.view.PinLoginView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import java.security.GeneralSecurityException
import javax.inject.Inject

class PinLoginPresenter @Inject constructor(
    private val context: Context,
    private val router: FlowRouter,
    private val preferencesProvider: SharedPreferencesProvider,
    private val cache: LoginDataCache,
    private val tokenCache: TokenCache,
    private val loginInteractor: LoginInteractorImpl,
    private val analyticsSender: AnalyticsSender,
    private val errorHandler: ServerErrorHandler,
    private val resourceProvider: ResourceProvider,
    private val loginDataCache: LoginDataCache,
    private val intent: Intent
) : BasicPresenter<PinLoginView>(router) {

    private var pin: String = ""

    private val pinSecuredAead by lazy {
        val keysetName = "pin_secured_keyset"
        val prefFileName = "pin_secured_key_preference"
        val masterKeyUri = "android-keystore://pin_secured_key"

        val keysetHandle = AndroidKeysetManager.Builder()
            .withSharedPref(
                context,
                keysetName,
                prefFileName
            )
            .withKeyTemplate(AeadKeyTemplates.AES256_GCM)
            .withMasterKeyUri(masterKeyUri)
            .build()
            .keysetHandle

        keysetHandle.getPrimitive(Aead::class.java)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val additionalButtonText = if (preferencesProvider.hasPin.get()) {
            resourceProvider.getString(R.string.pin_reset)
        } else {
            resourceProvider.getString(R.string.pin_login_skip)
        }

        val headerText = if (preferencesProvider.hasPin.get()) {
            resourceProvider.getString(R.string.pin_login_title)
        } else {
            resourceProvider.getString(R.string.pin_login_create_title)
        }

        viewState.setAdditionalButtonText(additionalButtonText)
        viewState.setTitle(headerText)
        parseLoginIntent(intent, loginDataCache, viewState, resourceProvider)
    }

    fun onLoginClicked() {
        if (preferencesProvider.hasPin.get()) {
            validateInputPin()
        } else {
            savePin()
        }
    }

    private fun validate(): Boolean {
        val errors = mutableListOf<String>().apply {
            if (pin.length != PIN_LENGTH) add(resourceProvider.getString(R.string.pin_length_error))
        }

        if (errors.isNotEmpty()) {
            viewState.setPinError(errors.joinToString("\n"))
            return false
        }
        return true
    }

    private fun savePin() {
        if (validate()) {
            encryptToken(pin)
            onPinSuccess()
        }
    }

    private fun encryptToken(pin: String) {
        val salt = Salt.generate()
        val secretKey = Pbkdf2Factory.createKey(pin.toCharArray(), salt)

        val encryptedToken = pinSecuredAead.encrypt(
            tokenCache.refreshToken?.toByteArray(),
            secretKey.encoded
        )

        preferencesProvider.refreshToken.set(
            Base64.encodeToString(
                encryptedToken,
                Base64.DEFAULT
            )
        )
        preferencesProvider.salt.set(Base64.encodeToString(salt, Base64.DEFAULT))
        preferencesProvider.hasPin.set(true)
    }

    private fun onPinSuccess() {
        val currentUserData = cache.currentUserData

        if (currentUserData == null) {
            loginInteractor.getCurrentUser()
                .subscribeOnIo()
                .withLoadingView(viewState)
                .subscribe({

                    router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
                    analyticsSender.setUserId(it.id)
                }, { errorHandler.onError(it, viewState) })
                .addFullLifeCycle()
        } else {
            router.replaceFlow(Flows.Main.name, TabsFlowArgs(currentUserData.role))
        }
    }

    private fun validateInputPin() {
        if (validate().not()) return

        val salt = Base64.decode(preferencesProvider.salt.get(), Base64.DEFAULT)
        val secretKey = Pbkdf2Factory.createKey(pin.toCharArray(), salt)

        val token = try {
            val encryptedToken =
                Base64.decode(preferencesProvider.refreshToken.get(), Base64.DEFAULT)
            pinSecuredAead.decrypt(encryptedToken, secretKey.encoded)
        } catch (e: GeneralSecurityException) {
            viewState.setPinError(resourceProvider.getString(R.string.pin_incorrect))
            null
        }

        token?.toString(Charsets.UTF_8)?.let {
            tokenCache.refreshToken = it
            loginInteractor.refresh(it)
                .subscribeOnIo()
                .withLoadingView(viewState)
                .subscribe({
                    encryptToken(pin)
                    onPinSuccess()
                }) {
                    errorHandler.onError(it, viewState)
                }
        }
    }

    private fun resetPin() {
        preferencesProvider.hasPin.set(false)
        router.replaceScreen(Flows.Login.SCREEN_LOGIN)
    }

    fun onAdditionalButtonClicked() {
        if (preferencesProvider.hasPin.get()) {
            resetPin()
        } else {
            cache.currentUserData?.let {
                router.replaceFlow(Flows.Main.name, TabsFlowArgs(it.role))
            }
        }
    }

    fun onPinTextChanged(pin: String) {
        this.pin = pin
        viewState.setPinError("")
    }

    companion object {
        private const val PIN_LENGTH = 4
    }
}