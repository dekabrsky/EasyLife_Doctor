package ru.dekabrsky.feature.notifications.common.domain.model

import androidx.annotation.PluralsRes
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import ru.dekabrsky.feature.notifications.api.R
import java.io.Serializable

@Suppress("LongParameterList")
data class NotificationEntity(
    val uid: Long? = null,
    val medicines: List<NotificationMedicineEntity> = listOf(),
    val hour: Int = 0,
    val minute: Int = 0,
    val enabled: Boolean = true,
    val weekDays: List<DayOfWeek> = DayOfWeek.values().toList(),
    val duration: NotificationDurationEntity? = null
) : Serializable

class NotificationMedicineEntity(
    val name: String,
    val unit: DosageUnit?,
    val dosage: String,
    val note: String
): Serializable

enum class DosageUnit(@PluralsRes val rusUnitName: Int) {
    PILL(R.plurals.plural_pill),
    ML(R.plurals.plural_ml);

    companion object {
        fun getByValue(unit: String?) = values().find { it.name == unit }
    }
}

class NotificationDurationEntity(
    val startDate: LocalDate,
    val endDate: LocalDate
): Serializable