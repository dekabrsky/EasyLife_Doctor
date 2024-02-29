package ru.dekabrsky.sharedpreferences

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class SharedPreferencesProvider @Inject constructor(
    private val context: Context
) {
    private val preferences = context.getSharedPreferences(PREFERENCES_STORE_NAME, Context.MODE_PRIVATE)

    val lastLogin = StringPreference("LAST_LOGIN")
    val gameAvatar = StringPreference("GAME_AVATAR")
    val testUrl = StringPreference("TEST_URL")
    val wallColor = StringPreference("WALL_COLOR")
    val notificationIds = SetPreference<Long>("NOTIFICATIONS_LIST")

    inner class StringPreference(private val prefName: String) {
        fun get() = preferences.getString(prefName, "").orEmpty()

        fun set(value: String) =
            preferences
                .edit()
                .putString(prefName, value)
                .apply()
    }

    inner class SetPreference<T>(private val prefName: String) {
        fun get(): Set<T>{

            val itemType = object : TypeToken<Set<T>>() {}.type

             val json = preferences.getString(prefName, null)

            return if (json != null) {
                Gson().fromJson(json, itemType)
            } else  {
                emptySet()
            }
        }

        fun set(value: Set<T>) {
            val json = Gson().toJson(value)
            preferences.edit()
                .putString(prefName, json)
                .apply()
        }
    }

    companion object {
        private const val PREFERENCES_STORE_NAME = "APP_PREFERENCES"
    }
}

