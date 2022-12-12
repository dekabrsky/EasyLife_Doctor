package ru.dekabrsky.dialings.data.model

import androidx.annotation.Keep
import ru.dekabrsky.italks.basic.paging.data.response.Pageable
import ru.dekabrsky.italks.basic.paging.data.response.Sort

@Keep
class DialingsResponse(
    val content: List<DialingResultResponse>?,
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
class DialingResultResponse(
    val callersBase: CallersBaseOfDialingResponse?,
    val created: Long?,
    val id: Int?,
    val name: String?,
    val scenario: ScenarioOfDialingResponse?,
    val startDate: Long?,
    val endDate: Long?,
    val status: String?,
    val progress: ProgressResponse? = null,
    val percentEnd: Int? = null
)

@Keep
class IdNameResponse(
    val id: Int?,
    val name: String?
)

@Keep
class CallersBaseOfDialingResponse(
    val id: Int?,
    val name: String?,
    val countCallers: Int?
)

@Keep
class ScenarioOfDialingResponse(
    val scenarioId: Int?,
    val name: String?,
    val countSteps: Int?
)

@Keep
class ProgressResponse(
    val countCallers: Int?,
    val countEnd: Int?,
    val percentEnd: Int?
)