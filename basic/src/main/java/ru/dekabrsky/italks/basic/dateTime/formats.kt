package ru.dekabrsky.italks.basic.dateTime

import android.os.Build
import android.text.format.DateFormat
import androidx.annotation.RequiresApi
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.ZoneOffset
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import java.util.*

fun formatDateTimeToUiDateTime(dateTime: LocalDateTime): String = formatDateTime(dateTime, "d MMM yyyy, hh:mm")

fun formatDateToUiDate(dateTime: LocalDateTime): String = formatDateTime(dateTime, "d MMM yyyy")

fun formatDateTimeToUiTime(dateTime: LocalDateTime): String = formatDateTime(dateTime, "hh:mm")

private fun formatDateTime(dateTime: LocalDateTime, format: String): String =
    DateTimeFormatter.ofPattern(format)
        .withLocale(Locale("ru"))
        .format(dateTime)

fun LocalDate.toDate(): Date = Date(atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())

fun LocalDateTime.toDate(zoneOffset: ZoneOffset = ZoneOffset.UTC): Date =
    Date(toInstant(zoneOffset).toEpochMilli())
