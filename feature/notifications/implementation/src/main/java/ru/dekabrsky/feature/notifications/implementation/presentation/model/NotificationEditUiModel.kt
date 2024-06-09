package ru.dekabrsky.feature.notifications.implementation.presentation.model

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import ru.dekabrsky.easylife.basic.dateTime.formatDateToUiDateShort
import ru.dekabrsky.easylife.basic.dateTime.formatHourAndMinute

@Suppress("LongParameterList")
class NotificationEditUiModel(
//    var tabletName: String = "",
//    var dosage: String = "",
//    var note: String = "",
    var medicines: MutableList<MedicineItemUiModel> = mutableListOf(MedicineItemUiModel()),
    var hour: Int? = null,
    var minute: Int? = null,
    var selectedDays: List<DayOfWeek> = DayOfWeek.values().toList(),
    var withDuration: Boolean = false,
    var startDate: LocalDate? = null,
    var endDate: LocalDate? = null,
    var enabled: Boolean = true
) {
    val time: String get() = formatHourAndMinute(hour, minute).ifEmpty { "Выберите время" }

    val startDateString get() = startDate?.let { formatDateToUiDateShort(it) } ?: "Начало курса"

    val endDateString get() = endDate?.let { formatDateToUiDateShort(it) } ?: "Конец курса"
}