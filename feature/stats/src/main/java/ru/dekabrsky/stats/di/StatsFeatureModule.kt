package ru.dekabrsky.stats.di

import ru.dekabrsky.stats.data.api.StatsApi
import toothpick.config.Module

class StatsFeatureModule: Module() {
    init {
        bind(StatsApi::class.java).toProvider(StatsApiProvider::class.java)
    }
}