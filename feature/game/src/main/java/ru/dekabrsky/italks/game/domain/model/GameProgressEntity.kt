package ru.dekabrsky.italks.game.domain.model

@Suppress ("LongParameterList")
class GameProgressEntity (
    val score: Int,
    val experience: Int,
    val currentLevel: GameLevelResponseEntity,
    val nextLevel: GameLevelResponseEntity,
    val levelUpgraded: Boolean,
    val maxLevelReached: Boolean
)

class GameLevelResponseEntity (
    val id: Int,
    val nextLevelExperience: Int
)

