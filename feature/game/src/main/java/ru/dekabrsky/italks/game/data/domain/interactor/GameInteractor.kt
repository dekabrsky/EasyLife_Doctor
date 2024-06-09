package ru.dekabrsky.easylife.game.data.domain.interactor

import ru.dekabrsky.easylife.game.data.repository.GameRepository
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val repo: GameRepository
) {
    fun getGamesConfigs() = repo.getGamesConfigs()

    fun postGameProgress(gameId: Int, score: Int, usePillMultiplier: Boolean) =
        repo.postGameProgress(gameId, score, usePillMultiplier)
}