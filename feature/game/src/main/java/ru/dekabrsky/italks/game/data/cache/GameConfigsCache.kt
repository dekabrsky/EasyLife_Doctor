package ru.dekabrsky.easylife.game.data.cache

import ru.dekabrsky.easylife.game.data.domain.model.GameConfigEntity
import javax.inject.Inject

class GameConfigsCache @Inject constructor() {
    var configs: List<GameConfigEntity> = listOf()
}