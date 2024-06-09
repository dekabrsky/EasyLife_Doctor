package ru.dekabrsky.easylife.game.view.presenter

import ru.dekabrsky.easylife.basic.navigation.router.FlowRouter
import ru.dekabrsky.easylife.basic.presenter.BasicPresenter
import ru.dekabrsky.easylife.flows.Flows
import ru.dekabrsky.easylife.game.data.domain.model.GameProgressEntity
import ru.dekabrsky.easylife.game.view.GameFlowView
import ru.dekabrsky.easylife.game.view.cache.GameFlowCache
import ru.dekabrsky.feature.loginCommon.presentation.model.LoginDataCache
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

