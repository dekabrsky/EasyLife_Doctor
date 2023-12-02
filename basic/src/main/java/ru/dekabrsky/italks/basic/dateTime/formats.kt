package ru.dekabrsky.italks.basic.dateTime

import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.DateTimeParseException
import java.util.*

fun formatDateTimeToUiDateTime(dateTime: LocalDateTime): String = formatDateTime(dateTime, "d MMM yyyy, hh:mm")

fun formatDateToUiDate(dateTime: LocalDateTime): String = formatDateTime(dateTime, "d MMM yyyy")

fun formatDateToUiDateShort(dateTime: LocalDate): String = formatDateTime(dateTime, "dd.MM.yyyy")

fun formatDateTimeToUiTime(dateTime: LocalDateTime): String = formatDateTime(dateTime, "hh:mm")

fun formatDateToServerString(date: LocalDate): String = formatDateTime(date, SERVER_DATE_FORMAT)

fun formatHourAndMinute(hour: Int?, minute: Int?) : String {
    if (hour == null || minute == null) return ""
    val hour = if (hour < MIN_TWO_DIGIT_MINUTE) "0$hour" else hour.toString()
    val minute = if (minute < MIN_TWO_DIGIT_MINUTE) "0$minute" else minute.toString()
    return "$hour:$minute"
}

fun hourAndMinuteFromString(time: String?): Pair<Int, Int> {
    val parts = time?.split(":")
    val hour = parts?.firstOrNull()?.toIntOrNull() ?: 0
    val minute = parts?.getOrNull(1)?.toIntOrNull() ?: 0
    return hour to minute
}

private fun formatDateTime(dateTime: LocalDateTime, format: String): String =
    DateTimeFormatter.ofPattern(format)
        .withLocale(Locale("ru"))
        .format(dateTime)

private fun formatDateTime(dateTime: LocalDate, format: String): String =
    DateTimeFormatter.ofPattern(format)
        .withLocale(Locale("ru"))
        .format(dateTime)

fun LocalDate.toDate(): Date = Date(atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())

fun LocalDateTime.toDate(zoneOffset: ZoneOffset = ZoneOffset.UTC): Date =
    Date(toInstant(zoneOffset).toEpochMilli())

fun tryParseServerDate(date: String) = try {
    LocalDate.parse(date, DateTimeFormatter.ofPattern(SERVER_DATE_FORMAT))
} catch (e: DateTimeParseException) {
    null
}

private const val MIN_TWO_DIGIT_MINUTE = 10
const val SERVER_DATE_FORMAT = "yyyy-MM-dd"