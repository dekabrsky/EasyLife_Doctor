package ru.dekabrsky.easylife.basic.network.model

import androidx.annotation.Keep

@Keep
class ErrorResponseBody(
    val statusCode: Int,
    val error: ErrorResponseContent
)

@Keep
class ErrorResponseContent(
    val code: String,
    val message: String
)