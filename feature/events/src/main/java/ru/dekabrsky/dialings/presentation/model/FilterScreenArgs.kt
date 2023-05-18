package ru.dekabrsky.dialings.presentation.model

import java.io.Serializable

class FilterScreenArgs(
    val resultCode: Int,
    val name: String,
    val values: List<String>,
    val selectedValues: List<String>
): Serializable