package ru.dekabrsky.callersbase.data.model

import androidx.annotation.Keep
import ru.dekabrsky.easylife.basic.paging.data.response.Pageable
import ru.dekabrsky.easylife.basic.paging.data.response.Sort

@Suppress("LongParameterList")
@Keep
class CallersBaseResponse(
    val content: List<ContentResponse>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: Sort,
    val totalElements: Int,
    val totalPages: Int
)

@Keep
class ColumnResponse(
    val created: Long,
    val currentName: String,
    val id: Int,
    val nameInTable: String,
    val type: String
)

@Suppress("LongParameterList")
@Keep
class ContentResponse(
    val columns: List<ColumnResponse>,
    val confirmed: Boolean,
    val countCallers: Int,
    val countInvalidCallers: Int,
    val created: Long,
    val id: Int,
    val name: String,
    val updated: Long
)
