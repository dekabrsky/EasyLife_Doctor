package ru.dekabrsky.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import javax.inject.Inject


class SharedPreferencesProvider @Inject constructor(
    private val context: Context
) {
    private val preferences =
        context.getSharedPreferences(PREFERENCES_STORE_NAME, Context.MODE_PRIVATE)

    private val masterKey =
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val encryptedSharedPreferences = EncryptedSharedPreferences.create(
        context,
        "ENCRYPTED_DATA",
        masterKey,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    val lastLogin = StringPreference("LAST_LOGIN")
    val gameAvatar = StringPreference("GAME_AVATAR")
    val testUrl = StringPreference("TEST_URL")
    val wallColor = StringPreference("WALL_COLOR")
    val hasPin = BooleanPreference("HAS_PIN")
    val salt = StringPreference("STORAGE_KEY_SALT", encryptedSharedPreferences)
    val refreshToken = StringPreference("REFRESH_TOKEN", encryptedSharedPreferences)
    val notificationIds = SetPreference<Long>("NOTIFICATIONS_LIST")

    // avatar customization
    val avatarHat = StringPreference("AVATAR_HAT")
    val avatarGlasses = StringPreference("AVATAR_GLASSES")
    val avatarTie = StringPreference("AVATAR_TIE")

    inner class StringPreference(
        private val prefName: String,
        private val sharedPreferences: SharedPreferences = preferences
    ) {
        fun get() = sharedPreferences.getString(prefName, "").orEmpty()

        fun set(value: String) =
            sharedPreferences
                .edit()
                .putString(prefName, value)
                .apply()
    }

    inner class SetPreference<T>(
        private val prefName: String,
        private val sharedPreferences: SharedPreferences = preferences
    ) {
        fun get(): Set<T> {

            val itemType = object : TypeToken<Set<T>>() {}.type

            val json = sharedPreferences.getString(prefName, null)

            return if (json != null) {
                Gson().fromJson(json, itemType)
            } else {
                emptySet()
            }
        }

        fun set(value: Set<T>) {
            val json = Gson().toJson(value)
            sharedPreferences.edit()
                .putString(prefName, json)
                .apply()
        }
    }

    inner class BooleanPreference(
        private val prefName: String,
        private val sharedPreferences: SharedPreferences = preferences
    ) {
        fun get() = sharedPreferences.getBoolean(prefName, false)

        fun set(value: Boolean) =
            sharedPreferences
                .edit()
                .putBoolean(prefName, value)
                .apply()
    }

    companion object {
        private const val PREFERENCES_STORE_NAME = "APP_PREFERENCES"
    }
}

