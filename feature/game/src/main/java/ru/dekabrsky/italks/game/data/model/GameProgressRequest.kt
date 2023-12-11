package ru.dekabrsky.italks.game.data.model

import androidx.annotation.Keep

@Keep
class GameProgressRequest(
    val gameId: Int,
    val score: Int,
    val usePillMultiplier: Boolean?
)