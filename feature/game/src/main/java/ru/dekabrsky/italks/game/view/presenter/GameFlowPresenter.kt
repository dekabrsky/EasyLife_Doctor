package ru.dekabrsky.italks.game.view.presenter

import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
import ru.dekabrsky.italks.basic.navigation.router.FlowRouter
import ru.dekabrsky.italks.basic.presenter.BasicPresenter
import ru.dekabrsky.italks.flows.Flows
import ru.dekabrsky.italks.game.data.domain.model.GameProgressEntity
import ru.dekabrsky.italks.game.view.GameFlowView
import ru.dekabrsky.italks.game.view.cache.GameFlowCache
import javax.inject.Inject

class GameFlowPresenter @Inject constructor(
    private val router: FlowRouter,
    private val loginDataCache: LoginDataCache,
    private val gameFlowCache: GameFlowCache,
) : BasicPresenter<GameFlowView>(router) {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        router.newRootScreen(Flows.Game.SCREEN_START_GAME)
        loginDataCache.currentUserData?.currentLevel?.let {
            gameFlowCache.experience = GameProgressEntity(it.score, it.experience)
        }
    }
}

