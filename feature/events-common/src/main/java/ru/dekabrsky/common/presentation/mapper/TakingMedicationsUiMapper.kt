package ru.dekabrsky.common.presentation.mapper

import org.threeten.bp.LocalDateTime
import ru.dekabrsky.common.domain.model.DialingEntity
import ru.dekabrsky.common.presentation.model.TakingMedicationsUiModel
import ru.dekabrsky.italks.basic.dateTime.formatDateTimeToUiDateTime
import javax.inject.Inject
import kotlin.random.Random

class TakingMedicationsUiMapper @Inject constructor(){
    fun map(entity: DialingEntity): TakingMedicationsUiModel {
        return TakingMedicationsUiModel(
            name = entity.name,
            id = entity.id,
            dateTime = formatDateTimeToUiDateTime(entity.startDate)
        )
    }

    @Suppress("MagicNumber")
    fun mapMock(): List<TakingMedicationsUiModel> {
        return listOf(
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
            TakingMedicationsUiModel(
                name = "Препарат #${Random.nextInt(5)}",
                id = 123,
                dateTime = formatDateTimeToUiDateTime(LocalDateTime.now().minusMinutes(Random.nextInt(60).toLong())),
                isMedicationTaken = Random.nextBoolean(),
                isGameDone = Random.nextBoolean(),
            ),
        )
    }
}