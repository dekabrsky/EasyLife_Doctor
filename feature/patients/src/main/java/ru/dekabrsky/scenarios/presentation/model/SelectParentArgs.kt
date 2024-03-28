package ru.dekabrsky.scenarios.presentation.model

import ru.dekabrsky.common.domain.model.ContactEntity
import java.io.Serializable

class SelectParentArgs(
    val selectedParentId: Long? = null,
    val variants: List<ContactEntity>
): Serializable