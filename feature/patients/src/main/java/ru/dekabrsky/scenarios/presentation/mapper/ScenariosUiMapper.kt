package ru.dekabrsky.scenarios.presentation.mapper

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import ru.dekabrsky.italks.basic.dateTime.formatDateToUiDate
import ru.dekabrsky.scenarios_common.domain.model.ScenarioEntity
import ru.dekabrsky.scenarios_common.presentation.model.ScenarioItemUiModel
import javax.inject.Inject
import kotlin.random.Random

class ScenariosUiMapper @Inject constructor() {
    fun map(): ScenarioItemUiModel {

        return ScenarioItemUiModel(
            id = Random.nextInt(10000),
            name = "#${Random.nextInt(10000)}",
            date = formatDateToUiDate(LocalDateTime.now()),
            hasWarning = Random.nextBoolean(), // пока некорректных сценариев не бывает
            stepsCount = "Статус: на наблюдении"
        )
    }
}