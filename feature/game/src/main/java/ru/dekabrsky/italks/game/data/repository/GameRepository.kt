package ru.dekabrsky.italks.game.data.repository

import io.reactivex.Single
import ru.dekabrsky.italks.game.data.api.GameApi
import ru.dekabrsky.italks.game.data.cache.GameConfigsCache
import ru.dekabrsky.italks.game.data.mapper.GameResponseToEntityMapper
import javax.inject.Inject

class GameRepository @Inject constructor(
    private val api: GameApi,
    private val mapper: GameResponseToEntityMapper,
    private val cache: GameConfigsCache
) {
    fun getGamesConfigs() = if (cache.configs.isEmpty()) {
        api.getGamesConfigs().map { mapper.mapGameConfigs(it).also { entities -> cache.configs = entities } }
    } else {
        Single.just(cache.configs)
    }
}