package ru.dekabrsky.easylife.game.avatarCustomization.utils

import ru.dekabrsky.easylife.basic.utils.asEnumOrDefaultNonNull
import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Glasses
import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Hat
import ru.dekabrsky.easylife.game.avatarCustomization.domain.model.Tie
import ru.dekabrsky.easylife.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider

fun getCatSettingsFromPrefs(prefs: SharedPreferencesProvider): CatWidgetSettings {
    return CatWidgetSettings(
        prefs.avatarHat.get().asEnumOrDefaultNonNull(Hat.NONE),
        prefs.avatarGlasses.get().asEnumOrDefaultNonNull(Glasses.NONE),
        prefs.avatarTie.get().asEnumOrDefaultNonNull(Tie.NONE)
    )
}