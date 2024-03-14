package ru.dekabrsky.italks.game.avatarCustomization.utils

import ru.dekabrsky.italks.basic.utils.asEnumOrDefaultNonNull
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Glasses
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Hat
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Tie
import ru.dekabrsky.italks.game.avatarCustomization.view.model.CatWidgetSettings
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider

fun getCatSettingsFromPrefs(prefs: SharedPreferencesProvider): CatWidgetSettings {
    return CatWidgetSettings(
        prefs.avatarHat.get().asEnumOrDefaultNonNull(Hat.NONE),
        prefs.avatarGlasses.get().asEnumOrDefaultNonNull(Glasses.NONE),
        prefs.avatarTie.get().asEnumOrDefaultNonNull(Tie.NONE)
    )
}