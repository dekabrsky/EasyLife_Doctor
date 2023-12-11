package ru.dekabrsky.italks.game.view.cache

import ru.dekabrsky.italks.game.domain.model.GameConfigEntity
import javax.inject.Inject

class GameViewCache @Inject constructor(){
    var configs: List<GameConfigEntity> = listOf()
}