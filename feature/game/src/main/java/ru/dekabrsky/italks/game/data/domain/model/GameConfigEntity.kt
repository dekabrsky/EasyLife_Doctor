package ru.dekabrsky.italks.game.data.domain.model

class GameConfigEntity (
    val gameId: Int,
    val type: GameType,
    val displayName: String,
    val description: String,
    val enabled: Boolean
)