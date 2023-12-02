package ru.dekabrsky.italks.game.data.cache

import ru.dekabrsky.italks.game.domain.model.GameConfigEntity
import javax.inject.Inject

class GameConfigsCache @Inject constructor() {
    var configs: List<GameConfigEntity> = listOf()
}