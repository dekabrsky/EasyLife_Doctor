package ru.dekabrsky.easylife.di.module

import ru.dekabrsky.easylife.BuildConfig
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject
import javax.inject.Provider

class EndPointProvider @Inject constructor(sharedPreferencesProvider: SharedPreferencesProvider): Provider<String> {
    private val testUrl = sharedPreferencesProvider.testUrl.get()

    override fun get() = testUrl.ifEmpty { BuildConfig.ENDPOINT }
}