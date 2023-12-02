package ru.dekabrsky.italks.game.domain.interactor

import ru.dekabrsky.italks.game.data.repository.GameRepository
import javax.inject.Inject

class GameInteractor @Inject constructor(
    private val repo: GameRepository
) {
    fun getGamesConfigs() = repo.getGamesConfigs()
}