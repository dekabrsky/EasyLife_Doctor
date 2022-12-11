package ru.dekabrsky.italks.di.module

import android.content.Context
import ru.dekabrsky.italks.BuildConfig
import javax.inject.Inject
import javax.inject.Provider

class EndPointProvider @Inject constructor(context: Context): Provider<String> {
    private val testUrl = context
        .getSharedPreferences("APP_PREFERENCES", Context.MODE_PRIVATE)
        .getString("TEST_URL", "")
        .orEmpty()

    override fun get() = if (testUrl.isNotEmpty()) testUrl else BuildConfig.ENDPOINT
}