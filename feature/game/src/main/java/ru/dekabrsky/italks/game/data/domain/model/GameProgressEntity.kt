package ru.dekabrsky.easylife.game.data.domain.model

@Suppress ("LongParameterList")
class GameProgressEntity (
    val score: Int,
    val experience: Int,
    val currentLevel: GameLevelResponseEntity = GameLevelResponseEntity(),
    val nextLevel: GameLevelResponseEntity = GameLevelResponseEntity(),
    val levelUpgraded: Boolean = false,
    val maxLevelReached: Boolean = false
)

class GameLevelResponseEntity (
    val id: Int = 0,
    val nextLevelExperience: Int = 0
)

