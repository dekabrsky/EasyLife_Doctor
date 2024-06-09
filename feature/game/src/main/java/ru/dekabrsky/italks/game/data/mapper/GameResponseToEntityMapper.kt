package ru.dekabrsky.easylife.game.data.mapper

import ru.dekabrsky.easylife.game.data.domain.model.GameConfigEntity
import ru.dekabrsky.easylife.game.data.domain.model.GameLevelResponseEntity
import ru.dekabrsky.easylife.game.data.domain.model.GameProgressEntity
import ru.dekabrsky.easylife.game.data.domain.model.GameType
import ru.dekabrsky.easylife.game.data.model.GameConfigResponse
import ru.dekabrsky.easylife.game.data.model.GameLevelResponse
import ru.dekabrsky.easylife.game.data.model.GameProgressResponse
import javax.inject.Inject

class GameResponseToEntityMapper @Inject constructor() {
    fun mapGameConfigs(responses: List<GameConfigResponse>) =
        responses.map { response ->
            GameConfigEntity(
                gameId = response.gameId,
                type = GameType.getByName(response.name.orEmpty()),
                displayName = response.displayName.orEmpty(),
                description = response.description.orEmpty(),
                enabled = response.enabled ?: false
            )
        }

    fun mapProgress(response: GameProgressResponse) =
            GameProgressEntity(
                score = response.score ?: 0,
                experience = response.experience ?: 0,
                currentLevel = mapProgressLevel(response.currentLevel),
                nextLevel = mapProgressLevel(response.nextLevel),
                levelUpgraded = response.levelUpgraded ?: false,
                maxLevelReached = response.maxLevelReached ?: false
            )

    private fun mapProgressLevel(response: GameLevelResponse?) =
            GameLevelResponseEntity(
                id = response?.id ?: 0,
                nextLevelExperience = response?.nextLevelExperience ?: 0
            )
}