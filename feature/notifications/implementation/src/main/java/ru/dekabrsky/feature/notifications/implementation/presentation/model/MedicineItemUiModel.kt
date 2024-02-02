package ru.dekabrsky.feature.notifications.implementation.presentation.model

import ru.dekabrsky.feature.notifications.common.domain.model.DosageUnit

class MedicineItemUiModel(
    var name: String = "",
    var dosage: String = "",
    var unit: DosageUnit? = DosageUnit.PILL,
    var note: String = ""
)