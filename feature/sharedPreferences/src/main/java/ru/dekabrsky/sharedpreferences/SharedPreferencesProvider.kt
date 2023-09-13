package ru.dekabrsky.sharedpreferences

import android.content.Context
import javax.inject.Inject
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class SharedPreferencesProvider @Inject constructor(
    private val context: Context
) {
    private val preferences = context.getSharedPreferences(PREFERENCES_STORE_NAME, Context.MODE_PRIVATE)

    val lastLogin = StringPreference("LAST_LOGIN")

    inner class StringPreference(private val prefName: String) {
        fun get() = preferences.getString(prefName, "").orEmpty()

        fun set(value: String) =
            preferences
                .edit()
                .putString(prefName, value)
                .apply()
    }


    companion object {
        private const val PREFERENCES_STORE_NAME = "APP_PREFERENCES"
    }
}

