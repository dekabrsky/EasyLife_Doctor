package ru.dekabrsky.common.domain.model

import java.io.Serializable

class ContactEntity(
    val id: Long,
    val displayName: String,
    val nickName: String
): Serializable