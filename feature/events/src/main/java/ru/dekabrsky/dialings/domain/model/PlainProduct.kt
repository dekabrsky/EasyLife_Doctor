package ru.dekabrsky.dialings.domain.model

import android.service.autofill.FillEventHistory
import androidx.annotation.Keep
import java.io.Serializable
import java.time.LocalDate

@Keep
data class PlainProduct(
    val name: String,
    val avg: Double,
    val min: Double,
    val max: Double,
    val city: String,
    val date: LocalDate,
    val history: List<Int>,
    val trend: List<Float>,
    val pred: Int,
    val positions: List<Position>,
): Serializable

@Keep
data class Position(
    val name: String,
    val price: Double,
    val firm: String
): Serializable