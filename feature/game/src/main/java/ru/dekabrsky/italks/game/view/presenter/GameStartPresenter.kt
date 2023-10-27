package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.italks.game.view.GameView
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.scopes.Scopes
import ru.dekabrsky.sharedpreferences.SharedPreferencesProvider
import javax.inject.Inject

class GameStartPresenter @Inject constructor(
    val router: FlowRouter,
    private val sharedPreferencesProvider: SharedPreferencesProvider
) : BasicPresenter<GameView>(router) {

    override fun onFirstViewAttach() {
        viewState.setupAvatar(router)
    }

    fun onGameStartClicked() {
        if (sharedPreferencesProvider.gameAvatar.get().isEmpty()) {
            router.startFlow(Flows.Avatar.name)
        } else {
            router.navigateTo(Flows.Game.SCREEN_MAIN_ROOM)
        }
    }
}