package ru.dekabrsky.italks.activity.view

import android.app.NotificationManager
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import moxy.MvpDelegate
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import ru.dekabrsky.callersbase.di.module.CallersBasesFeatureModule
import ru.dekabrsky.dialings.di.DialingsFeatureModule
import ru.dekabrsky.feature.notifications.common.NotificationChannelManager
import ru.dekabrsky.feature.notifications.implementation.data.module.NotificationFeatureModule
import ru.dekabrsky.italks.R
import ru.dekabrsky.italks.activity.presenter.MainPresenter
import ru.dekabrsky.italks.basic.di.inject
import ru.dekabrsky.italks.basic.fragments.BasicFlowFragment
import ru.dekabrsky.italks.di.module.AppModule
import ru.dekabrsky.italks.di.module.FlowModule
import ru.dekabrsky.italks.di.module.NetworkModule
import ru.dekabrsky.italks.di.module.PushModule
import ru.dekabrsky.italks.navigation.AppFlowFragmentProvider
import ru.dekabrsky.italks.navigation.AppFlowNavigator
import ru.dekabrsky.italks.profile.di.ProfileFeatureModule
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.italks.testerSettings.presentation.view.TesterSettingsFragment
import ru.dekabrsky.login.di.module.LoginFeatureModule
import ru.dekabrsky.scenarios.di.module.DoctorsPatientsModule
import ru.dekabrsky.stats.di.StatsFeatureModule
import ru.terrakok.cicerone.Navigator
import ru.terrakok.cicerone.NavigatorHolder
import toothpick.Toothpick
import toothpick.configuration.MultipleRootException
import toothpick.locators.NoFactoryFoundException
import javax.inject.Inject


open class MainActivity : AppCompatActivity(), MainView {

    private val mvpDelegate: MvpDelegate<out MainActivity> by lazy { MvpDelegate(this) }
    private val compositeDisposable by lazy { CompositeDisposable() }

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val scopeName: String = this.javaClass.name

    private val toolbar: Toolbar? by lazy { findViewById<Toolbar>(R.id.toolbar) }

    private val channelManager: NotificationChannelManager by lazy {
        Toothpick.openScopes(Scopes.SCOPE_APP).getInstance(NotificationChannelManager::class.java)
    }

    @Inject
    lateinit var flowFragmentProvider: AppFlowFragmentProvider

    private val navigator: Navigator =
        object : AppFlowNavigator(this, R.id.flowContainer) {
            override fun createFragment(screenKey: String?, data: Any?): Fragment? {
                return flowFragmentProvider.provideFlowFragment(screenKey, data)
            }
        }

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter(): MainPresenter =
        Toothpick.openScopes(Scopes.SCOPE_APP, scopeName)
            .getInstance(MainPresenter::class.java).also {
                Toothpick.closeScope(scopeName)
            }

    private fun injectDependencies() {
        Toothpick.openScopes(Scopes.SCOPE_APP_ROOT, Scopes.SCOPE_APP).apply {
            installModules(
                AppModule(),
                FlowModule(),
                NetworkModule(),
                PushModule(),
                // features
                LoginFeatureModule(),
                CallersBasesFeatureModule(),
                DoctorsPatientsModule(),
                DialingsFeatureModule(),
                StatsFeatureModule(),
                ProfileFeatureModule(),
                NotificationFeatureModule()
            )
        }.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.ThemeITalks)
        super.onCreate(savedInstanceState)
        tryInject(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
    }

    private fun tryInject(savedInstanceState: Bundle?) {
        try {
            injectDependencies()
            mvpDelegate.onCreate(savedInstanceState)
            onSecureCreate(savedInstanceState)
        } catch (e: MultipleRootException) {
            dispatchInjectException(e)
        } catch (t: NoFactoryFoundException) {
            dispatchInjectException(t)
        }
    }

    private fun dispatchInjectException(t: Exception) {
        Log.d(t.message, "Что-то пошло не так при попытке инъекции")
    }

    @CallSuper
    protected open fun onSecureCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        presenter.onAttach()

        channelManager.createNotificationChannels()

        val notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()
    }

    override fun onStart() {
        super.onStart()
        mvpDelegate.onAttach()
        toolbar?.let { toolbar -> setSupportActionBar(toolbar) }
    }

    override fun onResume() {
        super.onResume()
        mvpDelegate.onAttach()
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mvpDelegate.onSaveInstanceState(outState)
        mvpDelegate.onDetach()
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
        mvpDelegate.onDetach()
    }

    override fun onBackPressed() {
        val fragment =
            supportFragmentManager.fragments.lastOrNull { it.isHidden.not() && it is BasicFlowFragment }
        (fragment as? BasicFlowFragment)?.onBackPressed()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode in setOf(KeyEvent.KEYCODE_VOLUME_DOWN, KeyEvent.KEYCODE_VOLUME_UP)
            && supportFragmentManager.fragments.firstOrNull { it is TesterSettingsFragment } == null
        ) {
            presenter.toTesterSettings()
            return true
        }

        return super.onKeyDown(keyCode, event)
    }

    @Suppress("TooGenericExceptionCaught")
    override fun onDestroy() {
        super.onDestroy()
        try {
            mvpDelegate.onDestroyView()
            if (isFinishing) {
                mvpDelegate.onDestroy()
                compositeDisposable.dispose()
            }
        } catch (e: Exception) {
            throw e
        }
    }
}
