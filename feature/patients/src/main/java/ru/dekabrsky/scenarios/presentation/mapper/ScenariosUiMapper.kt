package ru.dekabrsky.scenarios.presentation.mapper

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.easylife.basic.dateTime.formatDateToUiDate
import ru.dekabrsky.common.presentation.model.ScenarioItemUiModel
import javax.inject.Inject
import kotlin.random.Random

class ScenariosUiMapper @Inject constructor() {
    @Suppress("MagicNumber")
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