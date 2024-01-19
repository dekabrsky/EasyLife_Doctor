package ru.dekabrsky.scenarios.presentation.model

import ru.dekabrsky.common.domain.model.ContactEntity
import java.io.Serializable

class SelectParentArgs(
    val selectedParentId: Int? = null,
    val variants: List<ContactEntity>
): Serializable