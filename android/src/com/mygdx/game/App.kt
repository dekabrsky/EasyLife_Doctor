package com.mygdx.game

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import ru.dekabrsky.italks.di.module.AppRootModule
import ru.dekabrsky.italks.scopes.Scopes
import toothpick.Toothpick
import toothpick.configuration.Configuration
import toothpick.smoothie.module.SmoothieApplicationModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setUpToothpick()

        Toothpick.openScope(Scopes.SCOPE_APP_ROOT).installModules(
            AppRootModule(this),
            SmoothieApplicationModule(this)
        )

        AndroidThreeTen.init(this)
    }

    private fun setUpToothpick() {
        val toothpickCfg = if (BuildConfig.DEBUG) {
            Configuration.forDevelopment().preventMultipleRootScopes()
        } else {
            Configuration.forProduction()
        }

        Toothpick.setConfiguration(toothpickCfg)
    }
    

}