package ru.dekabrsky.italks.game.data.di.module

import ru.dekabrsky.italks.game.data.api.GameApi
import ru.dekabrsky.italks.game.data.cache.GameConfigsCache
import ru.dekabrsky.italks.game.data.di.provider.GameApiProvider
import toothpick.config.Module

class GameFeatureModule: Module() {
    init {
        bind(GameApi::class.java).toProvider(GameApiProvider::class.java)
        bind(GameConfigsCache::class.java).singletonInScope()
    }
}