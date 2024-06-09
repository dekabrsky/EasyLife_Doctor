package ru.dekabrsky.easylife.game.data.model

import androidx.annotation.Keep

@Suppress ("LongParameterList")
@Keep
class GameProgressResponse (
    val score: Int?,
    val experience: Int?,
    val currentLevel: GameLevelResponse?,
    val nextLevel: GameLevelResponse?,
    val levelUpgraded: Boolean?,
    val maxLevelReached: Boolean?
)

@Keep
class GameLevelResponse (
    val id: Int,
    val nextLevelExperience: Int?
)