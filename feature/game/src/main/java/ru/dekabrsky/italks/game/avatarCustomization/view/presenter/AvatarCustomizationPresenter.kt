package ru.dekabrsky.italks.game.avatarCustomization.view.presenter

import ru.dekabrsky.analytics.AnalyticsSender
import ru.dekabrsky.analytics.AnalyticsUtils
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.game.R
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Glasses
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Hat
import ru.dekabrsky.italks.game.avatarCustomization.domain.model.Tie
import ru.dekabrsky.italks.game.avatarCustomization.utils.getCatSettingsFromPrefs
import ru.dekabrsky.italks.game.avatarCustomization.view.fragment.AvatarCustomizationView
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class AvatarCustomizationPresenter @Inject constructor(
    private val router: FlowRouter,
    private val prefs: SharedPreferencesProvider,
    private val analyticsSender: AnalyticsSender
): BasicPresenter<AvatarCustomizationView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        updateView()
        AnalyticsUtils.sendScreenOpen(this, analyticsSender)
    }

    private fun updateView() {
        viewState.updateSettings(getCatSettingsFromPrefs(prefs))
    }

    fun onHatClicked() {
        viewState.showChoiceDialog(
            R.string.hat_title,
            getCatSettingsFromPrefs(prefs).hat.ordinal,
            Hat.values().map { it.rusName }.toTypedArray()
        ) { pos ->
            prefs.avatarHat.set(Hat.values()[pos].name)
            updateView()
        }
    }

    fun onGlassesClicked() {
        viewState.showChoiceDialog(
            R.string.glasses_title,
            getCatSettingsFromPrefs(prefs).glasses.ordinal,
            Glasses.values().map { it.rusName }.toTypedArray()
        ) { pos ->
            prefs.avatarGlasses.set(Glasses.values()[pos].name)
            updateView()
        }
    }

    fun onTieClicked() {
        viewState.showChoiceDialog(
            R.string.tie_title,
            getCatSettingsFromPrefs(prefs).tie.ordinal,
            Tie.values().map { it.rusName }.toTypedArray()
        ) { pos ->
            prefs.avatarTie.set(Tie.values()[pos].name)
            updateView()
        }
    }
}