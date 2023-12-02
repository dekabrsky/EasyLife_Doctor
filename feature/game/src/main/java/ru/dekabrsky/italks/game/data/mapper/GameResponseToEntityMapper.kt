package ru.dekabrsky.italks.game.data.mapper

import ru.dekabrsky.italks.game.data.model.GameConfigResponse
import ru.dekabrsky.italks.game.domain.model.GameConfigEntity
import ru.dekabrsky.italks.game.domain.model.GameType
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
}