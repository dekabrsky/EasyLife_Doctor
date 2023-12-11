package ru.dekabrsky.italks.game.data.model

import androidx.annotation.Keep

@Keep
class GameConfigResponse (
    val gameId: Int,
    val name: String?,
    val displayName: String?,
    val description: String?,
    val enabled: Boolean?
)