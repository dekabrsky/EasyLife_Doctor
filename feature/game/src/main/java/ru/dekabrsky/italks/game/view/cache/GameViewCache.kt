package ru.dekabrsky.easylife.game.view.cache

import ru.dekabrsky.easylife.game.data.domain.model.GameConfigEntity
import javax.inject.Inject

class GameViewCache @Inject constructor(){
    var configs: List<GameConfigEntity> = listOf()
}